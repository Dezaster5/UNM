package kz.unm.tusupkalimiraszhaugashnurzhan.service.async;

import java.util.concurrent.CompletableFuture;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanUserResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TusupkaliMirasZhaugashNurzhanAsyncNotificationService {

    private static final Logger log = LoggerFactory.getLogger(
            TusupkaliMirasZhaugashNurzhanAsyncNotificationService.class);

    @Async("tusupkaliMirasZhaugashNurzhanTaskExecutor")
    public CompletableFuture<Void> sendRegistrationNotification(
            TusupkaliMirasZhaugashNurzhanUserResponseDto user) {
        log.info("Async registration notification started for user {}", user.username());
        log.info("Simulated email sent to {} for role {}", user.email(), user.role());
        log.info("Async registration notification finished for user {}", user.username());
        return CompletableFuture.completedFuture(null);
    }
}
