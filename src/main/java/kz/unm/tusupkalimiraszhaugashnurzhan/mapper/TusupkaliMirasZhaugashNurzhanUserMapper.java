package kz.unm.tusupkalimiraszhaugashnurzhan.mapper;

import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanUserResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanUser;
import org.springframework.stereotype.Component;

@Component
public class TusupkaliMirasZhaugashNurzhanUserMapper {

    public TusupkaliMirasZhaugashNurzhanUserResponseDto toResponse(TusupkaliMirasZhaugashNurzhanUser user) {
        return new TusupkaliMirasZhaugashNurzhanUserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                user.getRole().getName().name(),
                user.isEnabled(),
                user.getCreatedAt()
        );
    }
}
