package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

public record TusupkaliMirasZhaugashNurzhanDepartmentResponseDto(
        Long id,
        String name,
        String code,
        String description,
        int studentCount,
        int teacherCount
) {
}
