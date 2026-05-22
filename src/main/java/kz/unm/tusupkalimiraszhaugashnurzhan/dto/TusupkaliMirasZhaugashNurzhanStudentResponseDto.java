package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

import java.time.LocalDate;
import java.util.List;

public record TusupkaliMirasZhaugashNurzhanStudentResponseDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String studentNumber,
        LocalDate dateOfBirth,
        String phoneNumber,
        String address,
        Long departmentId,
        String departmentName,
        Long userId,
        List<Long> courseIds
) {
}
