package kz.unm.tusupkalimiraszhaugashnurzhan.controller;

import java.util.concurrent.CompletableFuture;
import kz.unm.tusupkalimiraszhaugashnurzhan.service.async.TusupkaliMirasZhaugashNurzhanAsyncReportService;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class TusupkaliMirasZhaugashNurzhanReportController {

    private final TusupkaliMirasZhaugashNurzhanAsyncReportService asyncReportService;

    public TusupkaliMirasZhaugashNurzhanReportController(
            TusupkaliMirasZhaugashNurzhanAsyncReportService asyncReportService) {
        this.asyncReportService = asyncReportService;
    }

    @GetMapping("/students/export")
    public CompletableFuture<ResponseEntity<String>> exportStudents(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Long courseId) {
        return asyncReportService.generateStudentExport(search, departmentId, courseId)
                .thenApply(csv -> ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("text/csv"))
                        .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                                .filename("students-export.csv")
                                .build()
                                .toString())
                        .body(csv));
    }
}
