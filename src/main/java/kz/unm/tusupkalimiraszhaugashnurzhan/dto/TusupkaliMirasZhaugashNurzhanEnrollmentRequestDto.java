package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanEnrollmentStatus;
import jakarta.validation.constraints.NotNull;

public record TusupkaliMirasZhaugashNurzhanEnrollmentRequestDto(
        @NotNull
        Long studentId,
        @NotNull
        Long courseId,
        LocalDate enrollmentDate,
        @Size(max = 10)
        String grade,
        TusupkaliMirasZhaugashNurzhanEnrollmentStatus status
) {
}
