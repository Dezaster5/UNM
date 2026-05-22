package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

import java.time.LocalDate;

public record TusupkaliMirasZhaugashNurzhanStudentRequestDto(
        String firstName,
        String lastName,
        String email,
        String studentNumber,
        LocalDate dateOfBirth,
        String phoneNumber,
        String address,
        Long departmentId,
        Long userId
) {
}
