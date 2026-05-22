package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

import java.util.List;

public record TusupkaliMirasZhaugashNurzhanPageResponseDto<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean last
) {
}
