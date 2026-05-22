package kz.unm.tusupkalimiraszhaugashnurzhan;

import static org.assertj.core.api.Assertions.assertThat;

import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanAuthRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanAuthResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanRegisterRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanUserResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanRoleName;
import kz.unm.tusupkalimiraszhaugashnurzhan.repository.TusupkaliMirasZhaugashNurzhanUserRepository;
import kz.unm.tusupkalimiraszhaugashnurzhan.service.TusupkaliMirasZhaugashNurzhanAuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TusupkaliMirasZhaugashNurzhanAuthServiceTest {

    @Autowired
    private TusupkaliMirasZhaugashNurzhanAuthService authService;

    @Autowired
    private TusupkaliMirasZhaugashNurzhanUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void registerCreatesUserWithEncodedPassword() {
        TusupkaliMirasZhaugashNurzhanRegisterRequestDto request = registerRequest("service-user");

        TusupkaliMirasZhaugashNurzhanUserResponseDto response = authService.register(request);

        assertThat(response.username()).isEqualTo("service-user");
        assertThat(response.role()).isEqualTo(TusupkaliMirasZhaugashNurzhanRoleName.STUDENT.name());
        assertThat(userRepository.findByUsername("service-user"))
                .isPresent()
                .get()
                .satisfies(user -> assertThat(passwordEncoder.matches("Password123", user.getPassword())).isTrue());
    }

    @Test
    void loginReturnsBearerToken() {
        authService.register(registerRequest("login-user"));

        TusupkaliMirasZhaugashNurzhanAuthResponseDto response = authService.login(
                new TusupkaliMirasZhaugashNurzhanAuthRequestDto("login-user", "Password123"));

        assertThat(response.token()).isNotBlank();
        assertThat(response.tokenType()).isEqualTo("Bearer");
        assertThat(response.username()).isEqualTo("login-user");
        assertThat(response.role()).isEqualTo(TusupkaliMirasZhaugashNurzhanRoleName.STUDENT.name());
    }

    private TusupkaliMirasZhaugashNurzhanRegisterRequestDto registerRequest(String username) {
        return new TusupkaliMirasZhaugashNurzhanRegisterRequestDto(
                username,
                username + "@unm.test",
                "Password123",
                "Test User",
                TusupkaliMirasZhaugashNurzhanRoleName.STUDENT
        );
    }
}
