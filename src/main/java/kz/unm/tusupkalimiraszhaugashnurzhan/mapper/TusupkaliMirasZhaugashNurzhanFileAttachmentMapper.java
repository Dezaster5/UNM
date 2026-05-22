package kz.unm.tusupkalimiraszhaugashnurzhan.mapper;

import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanFileAttachmentResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanFileAttachment;
import org.springframework.stereotype.Component;

@Component
public class TusupkaliMirasZhaugashNurzhanFileAttachmentMapper {

    public TusupkaliMirasZhaugashNurzhanFileAttachmentResponseDto toResponse(
            TusupkaliMirasZhaugashNurzhanFileAttachment fileAttachment) {
        Long uploadedById = fileAttachment.getUploadedBy() == null ? null : fileAttachment.getUploadedBy().getId();
        String uploadedByUsername = fileAttachment.getUploadedBy() == null
                ? null
                : fileAttachment.getUploadedBy().getUsername();
        Long studentId = fileAttachment.getStudent() == null ? null : fileAttachment.getStudent().getId();
        Long teacherId = fileAttachment.getTeacher() == null ? null : fileAttachment.getTeacher().getId();
        Long courseId = fileAttachment.getCourse() == null ? null : fileAttachment.getCourse().getId();

        return new TusupkaliMirasZhaugashNurzhanFileAttachmentResponseDto(
                fileAttachment.getId(),
                fileAttachment.getOriginalFileName(),
                fileAttachment.getStoredFileName(),
                fileAttachment.getContentType(),
                fileAttachment.getSize(),
                fileAttachment.getUploadDate(),
                uploadedById,
                uploadedByUsername,
                studentId,
                teacherId,
                courseId
        );
    }
}
