package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanRoleName;

public record TusupkaliMirasZhaugashNurzhanRegisterRequestDto(
        @NotBlank
        @Size(min = 3, max = 80)
        String username,
        @NotBlank
        @Email
        @Size(max = 160)
        String email,
        @NotBlank
        @Size(min = 8, max = 100)
        String password,
        @NotBlank
        @Size(max = 160)
        String fullName,
        @NotNull
        TusupkaliMirasZhaugashNurzhanRoleName role
) {
}
