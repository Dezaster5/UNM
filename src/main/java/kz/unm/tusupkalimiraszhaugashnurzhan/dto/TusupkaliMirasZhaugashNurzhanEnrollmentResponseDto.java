package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

import java.time.LocalDate;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanEnrollmentStatus;

public record TusupkaliMirasZhaugashNurzhanEnrollmentResponseDto(
        Long id,
        Long studentId,
        String studentName,
        Long courseId,
        String courseTitle,
        LocalDate enrollmentDate,
        String grade,
        TusupkaliMirasZhaugashNurzhanEnrollmentStatus status
) {
}
