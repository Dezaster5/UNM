package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

public record TusupkaliMirasZhaugashNurzhanAuthResponseDto(
        String token,
        String tokenType,
        long expiresIn,
        String username,
        String role
) {
}
