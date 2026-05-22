package kz.unm.tusupkalimiraszhaugashnurzhan.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Reports", description = "Asynchronous export and report endpoints")
public class TusupkaliMirasZhaugashNurzhanReportController {

    private final TusupkaliMirasZhaugashNurzhanAsyncReportService asyncReportService;

    public TusupkaliMirasZhaugashNurzhanReportController(
            TusupkaliMirasZhaugashNurzhanAsyncReportService asyncReportService) {
        this.asyncReportService = asyncReportService;
    }

    @GetMapping("/students/export")
    @Operation(summary = "Export students as CSV asynchronously", responses = {
            @ApiResponse(responseCode = "200", description = "CSV export returned")
    })
    public CompletableFuture<ResponseEntity<String>> exportStudents(
            @Parameter(description = "Search by first name, last name, email, or student number")
            @RequestParam(required = false) String search,
            @Parameter(description = "Filter exported students by department id")
            @RequestParam(required = false) Long departmentId,
            @Parameter(description = "Filter exported students by course id")
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
