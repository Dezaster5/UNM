package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

import org.springframework.core.io.Resource;

public record TusupkaliMirasZhaugashNurzhanFileDownloadDto(
        Resource resource,
        String originalFileName,
        String contentType,
        Long size
) {
}
