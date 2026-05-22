package kz.unm.tusupkalimiraszhaugashnurzhan.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanDepartmentRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanDepartmentResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.service.TusupkaliMirasZhaugashNurzhanDepartmentService;
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
@RequestMapping("/api/departments")
@Tag(name = "Departments", description = "Department CRUD endpoints")
public class TusupkaliMirasZhaugashNurzhanDepartmentController {

    private final TusupkaliMirasZhaugashNurzhanDepartmentService departmentService;

    public TusupkaliMirasZhaugashNurzhanDepartmentController(
            TusupkaliMirasZhaugashNurzhanDepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    @Operation(summary = "List departments", responses = {
            @ApiResponse(responseCode = "200", description = "Departments returned")
    })
    public ResponseEntity<List<TusupkaliMirasZhaugashNurzhanDepartmentResponseDto>> findAll() {
        return ResponseEntity.ok(departmentService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a department by id", responses = {
            @ApiResponse(responseCode = "200", description = "Department returned"),
            @ApiResponse(responseCode = "404", description = "Department not found")
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanDepartmentResponseDto> findById(
            @Parameter(description = "Department id") @PathVariable Long id) {
        return ResponseEntity.ok(departmentService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a department", responses = {
            @ApiResponse(responseCode = "201", description = "Department created"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanDepartmentResponseDto> create(
            @Valid @RequestBody TusupkaliMirasZhaugashNurzhanDepartmentRequestDto request) {
        TusupkaliMirasZhaugashNurzhanDepartmentResponseDto created = departmentService.create(request);
        return ResponseEntity.created(URI.create("/api/departments/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a department", responses = {
            @ApiResponse(responseCode = "200", description = "Department updated"),
            @ApiResponse(responseCode = "404", description = "Department not found")
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanDepartmentResponseDto> update(
            @Parameter(description = "Department id") @PathVariable Long id,
            @Valid @RequestBody TusupkaliMirasZhaugashNurzhanDepartmentRequestDto request) {
        return ResponseEntity.ok(departmentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a department", responses = {
            @ApiResponse(responseCode = "204", description = "Department deleted"),
            @ApiResponse(responseCode = "404", description = "Department not found")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "Department id") @PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
