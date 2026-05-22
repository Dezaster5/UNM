package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

import java.time.LocalDate;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanEnrollmentStatus;

public record TusupkaliMirasZhaugashNurzhanEnrollmentRequestDto(
        Long studentId,
        Long courseId,
        LocalDate enrollmentDate,
        String grade,
        TusupkaliMirasZhaugashNurzhanEnrollmentStatus status
) {
}
