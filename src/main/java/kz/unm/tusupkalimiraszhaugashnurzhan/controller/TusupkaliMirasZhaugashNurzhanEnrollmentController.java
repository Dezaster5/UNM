package kz.unm.tusupkalimiraszhaugashnurzhan.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanEnrollmentRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanEnrollmentResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.service.TusupkaliMirasZhaugashNurzhanEnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enrollments")
@Tag(name = "Enrollments", description = "Enrollment CRUD and filtering endpoints")
public class TusupkaliMirasZhaugashNurzhanEnrollmentController {

    private final TusupkaliMirasZhaugashNurzhanEnrollmentService enrollmentService;

    public TusupkaliMirasZhaugashNurzhanEnrollmentController(
            TusupkaliMirasZhaugashNurzhanEnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    @Operation(summary = "List enrollments", responses = {
            @ApiResponse(responseCode = "200", description = "Enrollments returned")
    })
    public ResponseEntity<List<TusupkaliMirasZhaugashNurzhanEnrollmentResponseDto>> findAll(
            @Parameter(description = "Filter enrollments by student id") @RequestParam(required = false)
            Long studentId,
            @Parameter(description = "Filter enrollments by course id") @RequestParam(required = false)
            Long courseId) {
        return ResponseEntity.ok(enrollmentService.findAll(studentId, courseId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an enrollment by id", responses = {
            @ApiResponse(responseCode = "200", description = "Enrollment returned"),
            @ApiResponse(responseCode = "404", description = "Enrollment not found")
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanEnrollmentResponseDto> findById(
            @Parameter(description = "Enrollment id") @PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create an enrollment", responses = {
            @ApiResponse(responseCode = "201", description = "Enrollment created"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanEnrollmentResponseDto> create(
            @Valid @RequestBody TusupkaliMirasZhaugashNurzhanEnrollmentRequestDto request) {
        TusupkaliMirasZhaugashNurzhanEnrollmentResponseDto created = enrollmentService.create(request);
        return ResponseEntity.created(URI.create("/api/enrollments/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an enrollment", responses = {
            @ApiResponse(responseCode = "200", description = "Enrollment updated"),
            @ApiResponse(responseCode = "404", description = "Enrollment not found")
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanEnrollmentResponseDto> update(
            @Parameter(description = "Enrollment id") @PathVariable Long id,
            @Valid @RequestBody TusupkaliMirasZhaugashNurzhanEnrollmentRequestDto request) {
        return ResponseEntity.ok(enrollmentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an enrollment", responses = {
            @ApiResponse(responseCode = "204", description = "Enrollment deleted"),
            @ApiResponse(responseCode = "404", description = "Enrollment not found")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "Enrollment id") @PathVariable Long id) {
        enrollmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
