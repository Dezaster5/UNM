package kz.unm.tusupkalimiraszhaugashnurzhan.controller;

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
public class TusupkaliMirasZhaugashNurzhanEnrollmentController {

    private final TusupkaliMirasZhaugashNurzhanEnrollmentService enrollmentService;

    public TusupkaliMirasZhaugashNurzhanEnrollmentController(
            TusupkaliMirasZhaugashNurzhanEnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<List<TusupkaliMirasZhaugashNurzhanEnrollmentResponseDto>> findAll(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long courseId) {
        return ResponseEntity.ok(enrollmentService.findAll(studentId, courseId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanEnrollmentResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanEnrollmentResponseDto> create(
            @RequestBody TusupkaliMirasZhaugashNurzhanEnrollmentRequestDto request) {
        TusupkaliMirasZhaugashNurzhanEnrollmentResponseDto created = enrollmentService.create(request);
        return ResponseEntity.created(URI.create("/api/enrollments/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanEnrollmentResponseDto> update(
            @PathVariable Long id,
            @RequestBody TusupkaliMirasZhaugashNurzhanEnrollmentRequestDto request) {
        return ResponseEntity.ok(enrollmentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        enrollmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
