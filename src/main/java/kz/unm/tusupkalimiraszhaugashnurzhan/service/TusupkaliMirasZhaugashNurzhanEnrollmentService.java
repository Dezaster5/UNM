package kz.unm.tusupkalimiraszhaugashnurzhan.service;

import java.util.List;
import java.util.NoSuchElementException;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanEnrollmentRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanEnrollmentResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanCourse;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanEnrollment;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanStudent;
import kz.unm.tusupkalimiraszhaugashnurzhan.mapper.TusupkaliMirasZhaugashNurzhanEnrollmentMapper;
import kz.unm.tusupkalimiraszhaugashnurzhan.repository.TusupkaliMirasZhaugashNurzhanCourseRepository;
import kz.unm.tusupkalimiraszhaugashnurzhan.repository.TusupkaliMirasZhaugashNurzhanEnrollmentRepository;
import kz.unm.tusupkalimiraszhaugashnurzhan.repository.TusupkaliMirasZhaugashNurzhanStudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TusupkaliMirasZhaugashNurzhanEnrollmentService {

    private final TusupkaliMirasZhaugashNurzhanEnrollmentRepository enrollmentRepository;
    private final TusupkaliMirasZhaugashNurzhanStudentRepository studentRepository;
    private final TusupkaliMirasZhaugashNurzhanCourseRepository courseRepository;
    private final TusupkaliMirasZhaugashNurzhanEnrollmentMapper enrollmentMapper;

    public TusupkaliMirasZhaugashNurzhanEnrollmentService(
            TusupkaliMirasZhaugashNurzhanEnrollmentRepository enrollmentRepository,
            TusupkaliMirasZhaugashNurzhanStudentRepository studentRepository,
            TusupkaliMirasZhaugashNurzhanCourseRepository courseRepository,
            TusupkaliMirasZhaugashNurzhanEnrollmentMapper enrollmentMapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentMapper = enrollmentMapper;
    }

    @Transactional(readOnly = true)
    public List<TusupkaliMirasZhaugashNurzhanEnrollmentResponseDto> findAll(Long studentId, Long courseId) {
        if (studentId != null) {
            return enrollmentRepository.findByStudentId(studentId).stream()
                    .map(enrollmentMapper::toResponse)
                    .toList();
        }
        if (courseId != null) {
            return enrollmentRepository.findByCourseId(courseId).stream()
                    .map(enrollmentMapper::toResponse)
                    .toList();
        }
        return enrollmentRepository.findAll().stream()
                .map(enrollmentMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TusupkaliMirasZhaugashNurzhanEnrollmentResponseDto findById(Long id) {
        return enrollmentMapper.toResponse(getEnrollmentEntity(id));
    }

    @Transactional
    public TusupkaliMirasZhaugashNurzhanEnrollmentResponseDto create(
            TusupkaliMirasZhaugashNurzhanEnrollmentRequestDto request) {
        if (enrollmentRepository.existsByStudentIdAndCourseId(request.studentId(), request.courseId())) {
            throw new IllegalArgumentException("Student is already enrolled in this course");
        }
        TusupkaliMirasZhaugashNurzhanEnrollment enrollment = enrollmentMapper.toEntity(request);
        assignRelations(enrollment, request);
        return enrollmentMapper.toResponse(enrollmentRepository.save(enrollment));
    }

    @Transactional
    public TusupkaliMirasZhaugashNurzhanEnrollmentResponseDto update(
            Long id,
            TusupkaliMirasZhaugashNurzhanEnrollmentRequestDto request) {
        TusupkaliMirasZhaugashNurzhanEnrollment enrollment = getEnrollmentEntity(id);
        enrollmentRepository.findByStudentIdAndCourseId(request.studentId(), request.courseId())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Student is already enrolled in this course");
                });
        enrollmentMapper.updateEntity(enrollment, request);
        assignRelations(enrollment, request);
        return enrollmentMapper.toResponse(enrollmentRepository.save(enrollment));
    }

    @Transactional
    public void delete(Long id) {
        enrollmentRepository.delete(getEnrollmentEntity(id));
    }

    private TusupkaliMirasZhaugashNurzhanEnrollment getEnrollmentEntity(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Enrollment not found with id: " + id));
    }

    private void assignRelations(
            TusupkaliMirasZhaugashNurzhanEnrollment enrollment,
            TusupkaliMirasZhaugashNurzhanEnrollmentRequestDto request) {
        TusupkaliMirasZhaugashNurzhanStudent student = studentRepository.findById(request.studentId())
                .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + request.studentId()));
        TusupkaliMirasZhaugashNurzhanCourse course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new NoSuchElementException("Course not found with id: " + request.courseId()));
        enrollment.setStudent(student);
        enrollment.setCourse(course);
    }
}
