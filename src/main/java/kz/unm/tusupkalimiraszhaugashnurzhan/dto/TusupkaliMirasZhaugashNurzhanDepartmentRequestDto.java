package kz.unm.tusupkalimiraszhaugashnurzhan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TusupkaliMirasZhaugashNurzhanDepartmentRequestDto(
        @NotBlank
        @Size(max = 120)
        String name,
        @NotBlank
        @Size(max = 30)
        String code,
        @Size(max = 1000)
        String description
) {
}
