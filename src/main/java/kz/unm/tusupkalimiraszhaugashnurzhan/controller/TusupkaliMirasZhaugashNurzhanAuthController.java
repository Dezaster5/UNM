package kz.unm.tusupkalimiraszhaugashnurzhan.controller;

import jakarta.validation.Valid;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanAuthRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanAuthResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanRegisterRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanUserResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.service.TusupkaliMirasZhaugashNurzhanAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class TusupkaliMirasZhaugashNurzhanAuthController {

    private final TusupkaliMirasZhaugashNurzhanAuthService authService;

    public TusupkaliMirasZhaugashNurzhanAuthController(
            TusupkaliMirasZhaugashNurzhanAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanUserResponseDto> register(
            @Valid @RequestBody TusupkaliMirasZhaugashNurzhanRegisterRequestDto request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanAuthResponseDto> login(
            @Valid @RequestBody TusupkaliMirasZhaugashNurzhanAuthRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
