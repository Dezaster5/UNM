package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

import jakarta.validation.constraints.NotBlank;

public record TusupkaliMirasZhaugashNurzhanAuthRequestDto(
        @NotBlank
        String usernameOrEmail,
        @NotBlank
        String password
) {
}
