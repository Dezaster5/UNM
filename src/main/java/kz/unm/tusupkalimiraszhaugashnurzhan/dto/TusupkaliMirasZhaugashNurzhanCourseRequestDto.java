package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

import java.time.LocalDate;

public record TusupkaliMirasZhaugashNurzhanCourseRequestDto(
        String code,
        String title,
        String description,
        Integer credits,
        String semester,
        LocalDate startDate,
        LocalDate endDate,
        Long teacherId
) {
}
