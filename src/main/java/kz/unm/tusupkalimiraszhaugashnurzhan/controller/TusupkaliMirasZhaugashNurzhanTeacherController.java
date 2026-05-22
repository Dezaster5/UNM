package kz.unm.tusupkalimiraszhaugashnurzhan.controller;

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
public class TusupkaliMirasZhaugashNurzhanTeacherController {

    private final TusupkaliMirasZhaugashNurzhanTeacherService teacherService;

    public TusupkaliMirasZhaugashNurzhanTeacherController(
            TusupkaliMirasZhaugashNurzhanTeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public ResponseEntity<List<TusupkaliMirasZhaugashNurzhanTeacherResponseDto>> findAll() {
        return ResponseEntity.ok(teacherService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanTeacherResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanTeacherResponseDto> create(
            @RequestBody TusupkaliMirasZhaugashNurzhanTeacherRequestDto request) {
        TusupkaliMirasZhaugashNurzhanTeacherResponseDto created = teacherService.create(request);
        return ResponseEntity.created(URI.create("/api/teachers/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanTeacherResponseDto> update(
            @PathVariable Long id,
            @RequestBody TusupkaliMirasZhaugashNurzhanTeacherRequestDto request) {
        return ResponseEntity.ok(teacherService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teacherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
