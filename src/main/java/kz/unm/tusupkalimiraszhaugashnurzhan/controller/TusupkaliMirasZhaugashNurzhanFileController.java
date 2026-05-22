package kz.unm.tusupkalimiraszhaugashnurzhan.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Files", description = "File upload, metadata listing, download, and delete endpoints")
public class TusupkaliMirasZhaugashNurzhanFileController {

    private final TusupkaliMirasZhaugashNurzhanFileStorageService fileStorageService;

    public TusupkaliMirasZhaugashNurzhanFileController(
            TusupkaliMirasZhaugashNurzhanFileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload a file attachment", responses = {
            @ApiResponse(responseCode = "201", description = "File uploaded"),
            @ApiResponse(responseCode = "400", description = "Invalid file")
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanFileAttachmentResponseDto> upload(
            @RequestPart("file") MultipartFile file,
            @Parameter(description = "Attach file to student id") @RequestParam(required = false) Long studentId,
            @Parameter(description = "Attach file to teacher id") @RequestParam(required = false) Long teacherId,
            @Parameter(description = "Attach file to course id") @RequestParam(required = false) Long courseId,
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
    @Operation(summary = "List file metadata", responses = {
            @ApiResponse(responseCode = "200", description = "File metadata returned")
    })
    public ResponseEntity<List<TusupkaliMirasZhaugashNurzhanFileAttachmentResponseDto>> findAll(
            @Parameter(description = "Filter files by student id") @RequestParam(required = false) Long studentId,
            @Parameter(description = "Filter files by teacher id") @RequestParam(required = false) Long teacherId,
            @Parameter(description = "Filter files by course id") @RequestParam(required = false) Long courseId) {
        return ResponseEntity.ok(fileStorageService.findAll(studentId, teacherId, courseId));
    }

    @GetMapping("/{id}/download")
    @Operation(summary = "Download a file", responses = {
            @ApiResponse(responseCode = "200", description = "File downloaded"),
            @ApiResponse(responseCode = "404", description = "File metadata not found")
    })
    public ResponseEntity<org.springframework.core.io.Resource> download(
            @Parameter(description = "File attachment id") @PathVariable Long id) {
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
    @Operation(summary = "Delete a file", responses = {
            @ApiResponse(responseCode = "204", description = "File deleted"),
            @ApiResponse(responseCode = "404", description = "File metadata not found")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "File attachment id") @PathVariable Long id) {
        fileStorageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
