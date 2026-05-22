package kz.unm.tusupkalimiraszhaugashnurzhan.controller;

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
public class TusupkaliMirasZhaugashNurzhanDepartmentController {

    private final TusupkaliMirasZhaugashNurzhanDepartmentService departmentService;

    public TusupkaliMirasZhaugashNurzhanDepartmentController(
            TusupkaliMirasZhaugashNurzhanDepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<TusupkaliMirasZhaugashNurzhanDepartmentResponseDto>> findAll() {
        return ResponseEntity.ok(departmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanDepartmentResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanDepartmentResponseDto> create(
            @Valid @RequestBody TusupkaliMirasZhaugashNurzhanDepartmentRequestDto request) {
        TusupkaliMirasZhaugashNurzhanDepartmentResponseDto created = departmentService.create(request);
        return ResponseEntity.created(URI.create("/api/departments/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanDepartmentResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody TusupkaliMirasZhaugashNurzhanDepartmentRequestDto request) {
        return ResponseEntity.ok(departmentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
