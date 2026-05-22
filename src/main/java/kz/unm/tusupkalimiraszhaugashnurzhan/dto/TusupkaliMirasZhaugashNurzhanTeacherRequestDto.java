package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

import java.time.LocalDate;

public record TusupkaliMirasZhaugashNurzhanTeacherRequestDto(
        String firstName,
        String lastName,
        String email,
        String employeeNumber,
        String academicTitle,
        LocalDate hireDate,
        Long departmentId,
        Long userId
) {
}
