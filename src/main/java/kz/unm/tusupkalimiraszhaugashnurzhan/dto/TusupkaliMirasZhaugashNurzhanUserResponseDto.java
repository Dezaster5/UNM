package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

import java.time.LocalDateTime;

public record TusupkaliMirasZhaugashNurzhanUserResponseDto(
        Long id,
        String username,
        String email,
        String fullName,
        String role,
        boolean enabled,
        LocalDateTime createdAt
) {
}
