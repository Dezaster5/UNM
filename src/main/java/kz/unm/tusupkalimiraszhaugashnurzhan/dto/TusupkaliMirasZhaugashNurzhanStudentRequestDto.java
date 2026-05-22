package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record TusupkaliMirasZhaugashNurzhanStudentRequestDto(
        @NotBlank
        @Size(max = 80)
        String firstName,
        @NotBlank
        @Size(max = 80)
        String lastName,
        @NotBlank
        @Email
        @Size(max = 160)
        String email,
        @NotBlank
        @Size(max = 40)
        String studentNumber,
        @NotNull
        @Past
        LocalDate dateOfBirth,
        @Size(max = 40)
        String phoneNumber,
        @Size(max = 255)
        String address,
        Long departmentId,
        Long userId
) {
}
