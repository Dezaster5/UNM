package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanRoleName;

public record TusupkaliMirasZhaugashNurzhanRegisterRequestDto(
        String username,
        String email,
        String password,
        String fullName,
        TusupkaliMirasZhaugashNurzhanRoleName role
) {
}
