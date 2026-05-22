package kz.unm.tusupkalimiraszhaugashnurzhan.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanTeacherRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanTeacherResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.service.TusupkaliMirasZhaugashNurzhanTeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teachers")
@Tag(name = "Teachers", description = "Teacher CRUD endpoints")
public class TusupkaliMirasZhaugashNurzhanTeacherController {

    private final TusupkaliMirasZhaugashNurzhanTeacherService teacherService;

    public TusupkaliMirasZhaugashNurzhanTeacherController(
            TusupkaliMirasZhaugashNurzhanTeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    @Operation(summary = "List teachers", responses = {
            @ApiResponse(responseCode = "200", description = "Teachers returned")
    })
    public ResponseEntity<List<TusupkaliMirasZhaugashNurzhanTeacherResponseDto>> findAll() {
        return ResponseEntity.ok(teacherService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a teacher by id", responses = {
            @ApiResponse(responseCode = "200", description = "Teacher returned"),
            @ApiResponse(responseCode = "404", description = "Teacher not found")
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanTeacherResponseDto> findById(
            @Parameter(description = "Teacher id") @PathVariable Long id) {
        return ResponseEntity.ok(teacherService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a teacher", responses = {
            @ApiResponse(responseCode = "201", description = "Teacher created"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanTeacherResponseDto> create(
            @Valid @RequestBody TusupkaliMirasZhaugashNurzhanTeacherRequestDto request) {
        TusupkaliMirasZhaugashNurzhanTeacherResponseDto created = teacherService.create(request);
        return ResponseEntity.created(URI.create("/api/teachers/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a teacher", responses = {
            @ApiResponse(responseCode = "200", description = "Teacher updated"),
            @ApiResponse(responseCode = "404", description = "Teacher not found")
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanTeacherResponseDto> update(
            @Parameter(description = "Teacher id") @PathVariable Long id,
            @Valid @RequestBody TusupkaliMirasZhaugashNurzhanTeacherRequestDto request) {
        return ResponseEntity.ok(teacherService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a teacher", responses = {
            @ApiResponse(responseCode = "204", description = "Teacher deleted"),
            @ApiResponse(responseCode = "404", description = "Teacher not found")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "Teacher id") @PathVariable Long id) {
        teacherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
