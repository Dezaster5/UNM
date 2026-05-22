package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

import java.time.LocalDate;
import java.util.List;

public record TusupkaliMirasZhaugashNurzhanCourseResponseDto(
        Long id,
        String code,
        String title,
        String description,
        Integer credits,
        String semester,
        LocalDate startDate,
        LocalDate endDate,
        Long teacherId,
        String teacherName,
        List<Long> studentIds
) {
}
