package kz.unm.tusupkalimiraszhaugashnurzhan.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanCourseRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanCourseResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.service.TusupkaliMirasZhaugashNurzhanCourseService;
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
@RequestMapping("/api/courses")
@Tag(name = "Courses", description = "Course CRUD and search endpoints")
public class TusupkaliMirasZhaugashNurzhanCourseController {

    private final TusupkaliMirasZhaugashNurzhanCourseService courseService;

    public TusupkaliMirasZhaugashNurzhanCourseController(
            TusupkaliMirasZhaugashNurzhanCourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    @Operation(summary = "List courses", responses = {
            @ApiResponse(responseCode = "200", description = "Courses returned")
    })
    public ResponseEntity<List<TusupkaliMirasZhaugashNurzhanCourseResponseDto>> findAll(
            @Parameter(description = "Filter courses by teacher id") @RequestParam(required = false) Long teacherId,
            @Parameter(description = "Search by course title, code, or description")
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(courseService.findAll(teacherId, search));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a course by id", responses = {
            @ApiResponse(responseCode = "200", description = "Course returned"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanCourseResponseDto> findById(
            @Parameter(description = "Course id") @PathVariable Long id) {
        return ResponseEntity.ok(courseService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a course", responses = {
            @ApiResponse(responseCode = "201", description = "Course created"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanCourseResponseDto> create(
            @Valid @RequestBody TusupkaliMirasZhaugashNurzhanCourseRequestDto request) {
        TusupkaliMirasZhaugashNurzhanCourseResponseDto created = courseService.create(request);
        return ResponseEntity.created(URI.create("/api/courses/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a course", responses = {
            @ApiResponse(responseCode = "200", description = "Course updated"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanCourseResponseDto> update(
            @Parameter(description = "Course id") @PathVariable Long id,
            @Valid @RequestBody TusupkaliMirasZhaugashNurzhanCourseRequestDto request) {
        return ResponseEntity.ok(courseService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a course", responses = {
            @ApiResponse(responseCode = "204", description = "Course deleted"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "Course id") @PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
