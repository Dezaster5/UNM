package kz.unm.tusupkalimiraszhaugashnurzhan.controller;

import java.net.URI;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class TusupkaliMirasZhaugashNurzhanStudentController {

    private final TusupkaliMirasZhaugashNurzhanStudentService studentService;

    public TusupkaliMirasZhaugashNurzhanStudentController(
            TusupkaliMirasZhaugashNurzhanStudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<TusupkaliMirasZhaugashNurzhanStudentResponseDto>> findAll() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanStudentResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanStudentResponseDto> create(
            @RequestBody TusupkaliMirasZhaugashNurzhanStudentRequestDto request) {
        TusupkaliMirasZhaugashNurzhanStudentResponseDto created = studentService.create(request);
        return ResponseEntity.created(URI.create("/api/students/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanStudentResponseDto> update(
            @PathVariable Long id,
            @RequestBody TusupkaliMirasZhaugashNurzhanStudentRequestDto request) {
        return ResponseEntity.ok(studentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
