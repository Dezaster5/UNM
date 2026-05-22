package kz.unm.tusupkalimiraszhaugashnurzhan.service.file;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kz.unm.tusupkalimiraszhaugashnurzhan.config.TusupkaliMirasZhaugashNurzhanFileStorageProperties;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanFileAttachmentResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanFileDownloadDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanFileAttachment;
import kz.unm.tusupkalimiraszhaugashnurzhan.exception.TusupkaliMirasZhaugashNurzhanFileStorageException;
import kz.unm.tusupkalimiraszhaugashnurzhan.mapper.TusupkaliMirasZhaugashNurzhanFileAttachmentMapper;
import kz.unm.tusupkalimiraszhaugashnurzhan.repository.TusupkaliMirasZhaugashNurzhanCourseRepository;
import kz.unm.tusupkalimiraszhaugashnurzhan.repository.TusupkaliMirasZhaugashNurzhanFileAttachmentRepository;
import kz.unm.tusupkalimiraszhaugashnurzhan.repository.TusupkaliMirasZhaugashNurzhanStudentRepository;
import kz.unm.tusupkalimiraszhaugashnurzhan.repository.TusupkaliMirasZhaugashNurzhanTeacherRepository;
import kz.unm.tusupkalimiraszhaugashnurzhan.repository.TusupkaliMirasZhaugashNurzhanUserRepository;
import kz.unm.tusupkalimiraszhaugashnurzhan.service.async.TusupkaliMirasZhaugashNurzhanAsyncFileProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TusupkaliMirasZhaugashNurzhanFileStorageService {

    private static final Logger log = LoggerFactory.getLogger(
            TusupkaliMirasZhaugashNurzhanFileStorageService.class);

    private final Path uploadPath;
    private final long maxSizeBytes;
    private final List<String> allowedContentTypes;
    private final TusupkaliMirasZhaugashNurzhanFileAttachmentRepository fileAttachmentRepository;
    private final TusupkaliMirasZhaugashNurzhanUserRepository userRepository;
    private final TusupkaliMirasZhaugashNurzhanStudentRepository studentRepository;
    private final TusupkaliMirasZhaugashNurzhanTeacherRepository teacherRepository;
    private final TusupkaliMirasZhaugashNurzhanCourseRepository courseRepository;
    private final TusupkaliMirasZhaugashNurzhanFileAttachmentMapper fileAttachmentMapper;
    private final TusupkaliMirasZhaugashNurzhanAsyncFileProcessingService asyncFileProcessingService;

    public TusupkaliMirasZhaugashNurzhanFileStorageService(
            TusupkaliMirasZhaugashNurzhanFileStorageProperties fileStorageProperties,
            TusupkaliMirasZhaugashNurzhanFileAttachmentRepository fileAttachmentRepository,
            TusupkaliMirasZhaugashNurzhanUserRepository userRepository,
            TusupkaliMirasZhaugashNurzhanStudentRepository studentRepository,
            TusupkaliMirasZhaugashNurzhanTeacherRepository teacherRepository,
            TusupkaliMirasZhaugashNurzhanCourseRepository courseRepository,
            TusupkaliMirasZhaugashNurzhanFileAttachmentMapper fileAttachmentMapper,
            TusupkaliMirasZhaugashNurzhanAsyncFileProcessingService asyncFileProcessingService) {
        this.uploadPath = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        this.maxSizeBytes = fileStorageProperties.getMaxSizeBytes();
        this.allowedContentTypes = fileStorageProperties.getAllowedContentTypes();
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.fileAttachmentMapper = fileAttachmentMapper;
        this.asyncFileProcessingService = asyncFileProcessingService;
        createUploadDirectory();
    }

    @Transactional
    public TusupkaliMirasZhaugashNurzhanFileAttachmentResponseDto upload(
            MultipartFile file,
            Long studentId,
            Long teacherId,
            Long courseId,
            String uploadedByUsername) {
        validateFile(file);
        String originalFileName = cleanOriginalFileName(file);
        log.info("File upload started originalFileName={} contentType={} size={} uploadedBy={}",
                originalFileName,
                file.getContentType(),
                file.getSize(),
                uploadedByUsername);
        String storedFileName = UUID.randomUUID() + extractExtension(originalFileName);
        Path destination = uploadPath.resolve(storedFileName).normalize();

        if (!destination.startsWith(uploadPath)) {
            throw new TusupkaliMirasZhaugashNurzhanFileStorageException("Invalid file storage path");
        }
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            throw new TusupkaliMirasZhaugashNurzhanFileStorageException("Failed to store file", exception);
        }

        TusupkaliMirasZhaugashNurzhanFileAttachment attachment =
                buildAttachment(file, originalFileName, storedFileName, studentId, teacherId, courseId,
                        uploadedByUsername);
        TusupkaliMirasZhaugashNurzhanFileAttachmentResponseDto response =
                fileAttachmentMapper.toResponse(fileAttachmentRepository.save(attachment));
        asyncFileProcessingService.processUploadedFile(response);
        log.info("File upload completed id={} storedFileName={}", response.id(), response.storedFileName());
        return response;
    }

    @Transactional(readOnly = true)
    public List<TusupkaliMirasZhaugashNurzhanFileAttachmentResponseDto> findAll(
            Long studentId,
            Long teacherId,
            Long courseId) {
        log.info("Listing files studentId={} teacherId={} courseId={}", studentId, teacherId, courseId);
        List<TusupkaliMirasZhaugashNurzhanFileAttachment> attachments;
        if (studentId != null) {
            attachments = fileAttachmentRepository.findByStudentId(studentId);
        } else if (teacherId != null) {
            attachments = fileAttachmentRepository.findByTeacherId(teacherId);
        } else if (courseId != null) {
            attachments = fileAttachmentRepository.findByCourseId(courseId);
        } else {
            attachments = fileAttachmentRepository.findAll();
        }
        return attachments.stream()
                .map(fileAttachmentMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TusupkaliMirasZhaugashNurzhanFileDownloadDto download(Long id) {
        TusupkaliMirasZhaugashNurzhanFileAttachment attachment = findAttachment(id);
        log.info("File download requested id={} storedFileName={}", id, attachment.getStoredFileName());
        Path filePath = uploadPath.resolve(attachment.getStoredFileName()).normalize();
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new TusupkaliMirasZhaugashNurzhanFileStorageException("File is missing or unreadable");
            }
            return new TusupkaliMirasZhaugashNurzhanFileDownloadDto(
                    resource,
                    attachment.getOriginalFileName(),
                    attachment.getContentType(),
                    attachment.getSize()
            );
        } catch (MalformedURLException exception) {
            throw new TusupkaliMirasZhaugashNurzhanFileStorageException("Failed to load file", exception);
        }
    }

    @Transactional
    public void delete(Long id) {
        TusupkaliMirasZhaugashNurzhanFileAttachment attachment = findAttachment(id);
        log.info("File delete requested id={} storedFileName={}", id, attachment.getStoredFileName());
        Path filePath = uploadPath.resolve(attachment.getStoredFileName()).normalize();
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException exception) {
            throw new TusupkaliMirasZhaugashNurzhanFileStorageException("Failed to delete file", exception);
        }
        fileAttachmentRepository.delete(attachment);
        log.info("File delete completed id={}", id);
    }

    private void createUploadDirectory() {
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException exception) {
            throw new TusupkaliMirasZhaugashNurzhanFileStorageException(
                    "Could not create upload directory",
                    exception
            );
        }
    }

    private TusupkaliMirasZhaugashNurzhanFileAttachment buildAttachment(
            MultipartFile file,
            String originalFileName,
            String storedFileName,
            Long studentId,
            Long teacherId,
            Long courseId,
            String uploadedByUsername) {
        TusupkaliMirasZhaugashNurzhanFileAttachment attachment =
                new TusupkaliMirasZhaugashNurzhanFileAttachment();
        attachment.setOriginalFileName(originalFileName);
        attachment.setStoredFileName(storedFileName);
        attachment.setContentType(file.getContentType());
        attachment.setSize(file.getSize());
        if (StringUtils.hasText(uploadedByUsername)) {
            userRepository.findByUsername(uploadedByUsername)
                    .or(() -> userRepository.findByEmail(uploadedByUsername))
                    .ifPresent(attachment::setUploadedBy);
        }
        if (studentId != null) {
            attachment.setStudent(studentRepository.findById(studentId)
                    .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + studentId)));
        }
        if (teacherId != null) {
            attachment.setTeacher(teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new NoSuchElementException("Teacher not found with id: " + teacherId)));
        }
        if (courseId != null) {
            attachment.setCourse(courseRepository.findById(courseId)
                    .orElseThrow(() -> new NoSuchElementException("Course not found with id: " + courseId)));
        }
        return attachment;
    }

    private TusupkaliMirasZhaugashNurzhanFileAttachment findAttachment(Long id) {
        return fileAttachmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("File attachment not found with id: " + id));
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new TusupkaliMirasZhaugashNurzhanFileStorageException("File must not be empty");
        }
        String originalFileName = cleanOriginalFileName(file);
        if (!StringUtils.hasText(originalFileName) || originalFileName.contains("..")) {
            throw new TusupkaliMirasZhaugashNurzhanFileStorageException("Invalid file name");
        }
        if (file.getSize() > maxSizeBytes) {
            throw new TusupkaliMirasZhaugashNurzhanFileStorageException("File exceeds allowed size");
        }
        if (!allowedContentTypes.contains(file.getContentType())) {
            throw new TusupkaliMirasZhaugashNurzhanFileStorageException("File type is not allowed");
        }
    }

    private String extractExtension(String fileName) {
        int extensionIndex = fileName.lastIndexOf('.');
        return extensionIndex == -1 ? "" : fileName.substring(extensionIndex);
    }

    private String cleanOriginalFileName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        return StringUtils.cleanPath(originalFileName == null ? "" : originalFileName);
    }
}
