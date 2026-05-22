package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

import java.time.LocalDate;
import java.util.List;

public record TusupkaliMirasZhaugashNurzhanTeacherResponseDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String employeeNumber,
        String academicTitle,
        LocalDate hireDate,
        Long departmentId,
        String departmentName,
        Long userId,
        List<Long> courseIds
) {
}
