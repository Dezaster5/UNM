package kz.unm.tusupkalimiraszhaugashnurzhan.service.async;

import java.util.concurrent.CompletableFuture;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanFileAttachmentResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TusupkaliMirasZhaugashNurzhanAsyncFileProcessingService {

    private static final Logger log = LoggerFactory.getLogger(
            TusupkaliMirasZhaugashNurzhanAsyncFileProcessingService.class);

    @Async("tusupkaliMirasZhaugashNurzhanTaskExecutor")
    public CompletableFuture<String> processUploadedFile(
            TusupkaliMirasZhaugashNurzhanFileAttachmentResponseDto fileAttachment) {
        log.info("Async file processing started for stored file {}", fileAttachment.storedFileName());
        String result = "Processed " + fileAttachment.originalFileName()
                + " (" + fileAttachment.contentType() + ", " + fileAttachment.size() + " bytes)";
        log.info("Async file processing finished for stored file {}", fileAttachment.storedFileName());
        return CompletableFuture.completedFuture(result);
    }
}
