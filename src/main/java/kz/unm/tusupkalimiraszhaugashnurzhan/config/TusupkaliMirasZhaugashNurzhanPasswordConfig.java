package kz.unm.tusupkalimiraszhaugashnurzhan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class TusupkaliMirasZhaugashNurzhanPasswordConfig {

    @Bean
    public PasswordEncoder tusupkaliMirasZhaugashNurzhanPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
