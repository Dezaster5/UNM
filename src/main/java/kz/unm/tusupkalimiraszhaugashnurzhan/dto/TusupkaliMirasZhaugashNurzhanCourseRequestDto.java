package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record TusupkaliMirasZhaugashNurzhanCourseRequestDto(
        @NotBlank
        @Size(max = 40)
        String code,
        @NotBlank
        @Size(max = 160)
        String title,
        @Size(max = 1000)
        String description,
        @NotNull
        @Min(1)
        @Max(10)
        Integer credits,
        @NotBlank
        @Size(max = 40)
        String semester,
        @NotNull
        @FutureOrPresent
        LocalDate startDate,
        @NotNull
        @FutureOrPresent
        LocalDate endDate,
        Long teacherId
) {
}
