package kz.unm.tusupkalimiraszhaugashnurzhan.mapper;

import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanEnrollmentRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanEnrollmentResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanEnrollment;
import org.springframework.stereotype.Component;

@Component
public class TusupkaliMirasZhaugashNurzhanEnrollmentMapper {

    public TusupkaliMirasZhaugashNurzhanEnrollment toEntity(
            TusupkaliMirasZhaugashNurzhanEnrollmentRequestDto request) {
        TusupkaliMirasZhaugashNurzhanEnrollment enrollment = new TusupkaliMirasZhaugashNurzhanEnrollment();
        updateEntity(enrollment, request);
        return enrollment;
    }

    public void updateEntity(
            TusupkaliMirasZhaugashNurzhanEnrollment enrollment,
            TusupkaliMirasZhaugashNurzhanEnrollmentRequestDto request) {
        enrollment.setEnrollmentDate(request.enrollmentDate());
        enrollment.setGrade(request.grade());
        if (request.status() != null) {
            enrollment.setStatus(request.status());
        }
    }

    public TusupkaliMirasZhaugashNurzhanEnrollmentResponseDto toResponse(
            TusupkaliMirasZhaugashNurzhanEnrollment enrollment) {
        String studentName = enrollment.getStudent().getFirstName() + " " + enrollment.getStudent().getLastName();
        return new TusupkaliMirasZhaugashNurzhanEnrollmentResponseDto(
                enrollment.getId(),
                enrollment.getStudent().getId(),
                studentName,
                enrollment.getCourse().getId(),
                enrollment.getCourse().getTitle(),
                enrollment.getEnrollmentDate(),
                enrollment.getGrade(),
                enrollment.getStatus()
        );
    }
}
