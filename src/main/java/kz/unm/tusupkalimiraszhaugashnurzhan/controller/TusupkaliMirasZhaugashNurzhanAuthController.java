package kz.unm.tusupkalimiraszhaugashnurzhan.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "Public registration and login endpoints")
public class TusupkaliMirasZhaugashNurzhanAuthController {

    private final TusupkaliMirasZhaugashNurzhanAuthService authService;

    public TusupkaliMirasZhaugashNurzhanAuthController(
            TusupkaliMirasZhaugashNurzhanAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a user", responses = {
            @ApiResponse(responseCode = "200", description = "User registered"),
            @ApiResponse(responseCode = "400", description = "Invalid registration request")
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanUserResponseDto> register(
            @Valid @RequestBody TusupkaliMirasZhaugashNurzhanRegisterRequestDto request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Login and receive a JWT", responses = {
            @ApiResponse(responseCode = "200", description = "JWT issued"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials")
    })
    public ResponseEntity<TusupkaliMirasZhaugashNurzhanAuthResponseDto> login(
            @Valid @RequestBody TusupkaliMirasZhaugashNurzhanAuthRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
