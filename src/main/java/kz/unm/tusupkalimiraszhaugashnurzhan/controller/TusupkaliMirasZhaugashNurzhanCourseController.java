package kz.unm.tusupkalimiraszhaugashnurzhan.controller;

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
public class TusupkaliMirasZhaugashNurzhanCourseController {

    private final TusupkaliMirasZhaugashNurzhanCourseService courseService;

    public TusupkaliMirasZhaugashNurzhanCourseController(
            TusupkaliMirasZhaugashNurzhanCourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<TusupkaliMirasZhaugashNurzhanCourseResponseDto>> findAll(
            @RequestParam(required = false) Long teacherId,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(courseService.findAll(teacherId, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanCourseResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanCourseResponseDto> create(
            @RequestBody TusupkaliMirasZhaugashNurzhanCourseRequestDto request) {
        TusupkaliMirasZhaugashNurzhanCourseResponseDto created = courseService.create(request);
        return ResponseEntity.created(URI.create("/api/courses/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanCourseResponseDto> update(
            @PathVariable Long id,
            @RequestBody TusupkaliMirasZhaugashNurzhanCourseRequestDto request) {
        return ResponseEntity.ok(courseService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
