package kz.unm.tusupkalimiraszhaugashnurzhan.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanPageResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanStudentRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanStudentResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.service.TusupkaliMirasZhaugashNurzhanStudentService;
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
@RequestMapping("/api/students")
@Tag(name = "Students", description = "Student CRUD with pagination, sorting, search, and filtering")
public class TusupkaliMirasZhaugashNurzhanStudentController {

    private final TusupkaliMirasZhaugashNurzhanStudentService studentService;

    public TusupkaliMirasZhaugashNurzhanStudentController(
            TusupkaliMirasZhaugashNurzhanStudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @Operation(summary = "List students with pagination and filters", responses = {
            @ApiResponse(responseCode = "200", description = "Students returned"),
            @ApiResponse(responseCode = "403", description = "Role is not allowed")
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanPageResponseDto<
            TusupkaliMirasZhaugashNurzhanStudentResponseDto>> findAll(
            @Parameter(description = "Zero-based page index") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field name used for sorting") @RequestParam(defaultValue = "lastName")
            String sortBy,
            @Parameter(description = "Sort direction: asc or desc") @RequestParam(defaultValue = "asc")
            String direction,
            @Parameter(description = "Search by first name, last name, email, or student number")
            @RequestParam(required = false) String search,
            @Parameter(description = "Filter students by department id") @RequestParam(required = false)
            Long departmentId,
            @Parameter(description = "Filter students enrolled in a course id") @RequestParam(required = false)
            Long courseId) {
        return ResponseEntity.ok(studentService.findAll(
                page,
                size,
                sortBy,
                direction,
                search,
                departmentId,
                courseId
        ));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a student by id", responses = {
            @ApiResponse(responseCode = "200", description = "Student returned"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanStudentResponseDto> findById(
            @Parameter(description = "Student id") @PathVariable Long id) {
        return ResponseEntity.ok(studentService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a student", responses = {
            @ApiResponse(responseCode = "201", description = "Student created"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanStudentResponseDto> create(
            @Valid @RequestBody TusupkaliMirasZhaugashNurzhanStudentRequestDto request) {
        TusupkaliMirasZhaugashNurzhanStudentResponseDto created = studentService.create(request);
        return ResponseEntity.created(URI.create("/api/students/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a student", responses = {
            @ApiResponse(responseCode = "200", description = "Student updated"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanStudentResponseDto> update(
            @Parameter(description = "Student id") @PathVariable Long id,
            @Valid @RequestBody TusupkaliMirasZhaugashNurzhanStudentRequestDto request) {
        return ResponseEntity.ok(studentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a student", responses = {
            @ApiResponse(responseCode = "204", description = "Student deleted"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "Student id") @PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
