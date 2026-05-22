package kz.unm.tusupkalimiraszhaugashnurzhan.controller;

import java.net.URI;
import java.util.List;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanFileAttachmentResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanFileDownloadDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.service.file.TusupkaliMirasZhaugashNurzhanFileStorageService;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class TusupkaliMirasZhaugashNurzhanFileController {

    private final TusupkaliMirasZhaugashNurzhanFileStorageService fileStorageService;

    public TusupkaliMirasZhaugashNurzhanFileController(
            TusupkaliMirasZhaugashNurzhanFileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanFileAttachmentResponseDto> upload(
            @RequestPart("file") MultipartFile file,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long teacherId,
            @RequestParam(required = false) Long courseId,
            @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails == null ? null : userDetails.getUsername();
        TusupkaliMirasZhaugashNurzhanFileAttachmentResponseDto uploaded = fileStorageService.upload(
                file,
                studentId,
                teacherId,
                courseId,
                username
        );
        return ResponseEntity.created(URI.create("/api/files/" + uploaded.id() + "/download")).body(uploaded);
    }

    @GetMapping
    public ResponseEntity<List<TusupkaliMirasZhaugashNurzhanFileAttachmentResponseDto>> findAll(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long teacherId,
            @RequestParam(required = false) Long courseId) {
        return ResponseEntity.ok(fileStorageService.findAll(studentId, teacherId, courseId));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<org.springframework.core.io.Resource> download(@PathVariable Long id) {
        TusupkaliMirasZhaugashNurzhanFileDownloadDto download = fileStorageService.download(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(download.contentType()))
                .contentLength(download.size())
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                        .filename(download.originalFileName())
                        .build()
                        .toString())
                .body(download.resource());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fileStorageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
