package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record TusupkaliMirasZhaugashNurzhanErrorResponseDto(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> fieldErrors
) {
}
