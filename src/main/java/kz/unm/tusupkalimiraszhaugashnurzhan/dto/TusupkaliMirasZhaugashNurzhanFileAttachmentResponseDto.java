package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

import java.time.LocalDateTime;

public record TusupkaliMirasZhaugashNurzhanFileAttachmentResponseDto(
        Long id,
        String originalFileName,
        String storedFileName,
        String contentType,
        Long size,
        LocalDateTime uploadDate,
        Long uploadedById,
        String uploadedByUsername,
        Long studentId,
        Long teacherId,
        Long courseId
) {
}
