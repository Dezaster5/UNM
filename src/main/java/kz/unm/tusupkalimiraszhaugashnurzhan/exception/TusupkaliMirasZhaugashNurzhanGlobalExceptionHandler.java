package kz.unm.tusupkalimiraszhaugashnurzhan.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

@RestControllerAdvice
public class TusupkaliMirasZhaugashNurzhanGlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(
            TusupkaliMirasZhaugashNurzhanGlobalExceptionHandler.class);

    @ExceptionHandler({
            TusupkaliMirasZhaugashNurzhanResourceNotFoundException.class,
            NoSuchElementException.class
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanErrorResponseDto> handleNotFound(
            RuntimeException exception,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler({
            TusupkaliMirasZhaugashNurzhanBadRequestException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanErrorResponseDto> handleBadRequest(
            RuntimeException exception,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanErrorResponseDto> handleValidation(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage()));
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed", request, fieldErrors);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanErrorResponseDto> handleAuthentication(
            AuthenticationException exception,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.UNAUTHORIZED, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanErrorResponseDto> handleAccessDenied(
            AccessDeniedException exception,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler({
            TusupkaliMirasZhaugashNurzhanFileStorageException.class,
            MultipartException.class,
            MaxUploadSizeExceededException.class
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanErrorResponseDto> handleFileErrors(
            RuntimeException exception,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanErrorResponseDto> handleInternalError(
            Exception exception,
            HttpServletRequest request) {
        log.error("Unhandled API error", exception);
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error",
                request,
                Map.of()
        );
    }

    private ResponseEntity<TusupkaliMirasZhaugashNurzhanErrorResponseDto> buildResponse(
            HttpStatus status,
            String message,
            HttpServletRequest request,
            Map<String, String> fieldErrors) {
        TusupkaliMirasZhaugashNurzhanErrorResponseDto response =
                new TusupkaliMirasZhaugashNurzhanErrorResponseDto(
                        LocalDateTime.now(),
                        status.value(),
                        status.getReasonPhrase(),
                        message,
                        request.getRequestURI(),
                        fieldErrors
                );
        return ResponseEntity.status(status).body(response);
    }
}
