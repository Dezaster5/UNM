package kz.unm.tusupkalimiraszhaugashnurzhan.config;

import java.util.Arrays;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanRole;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanRoleName;
import kz.unm.tusupkalimiraszhaugashnurzhan.repository.TusupkaliMirasZhaugashNurzhanRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TusupkaliMirasZhaugashNurzhanDataInitializer {

    @Bean
    public CommandLineRunner tusupkaliMirasZhaugashNurzhanSeedRoles(
            TusupkaliMirasZhaugashNurzhanRoleRepository roleRepository) {
        return args -> Arrays.stream(TusupkaliMirasZhaugashNurzhanRoleName.values())
                .filter(roleName -> roleRepository.findByName(roleName).isEmpty())
                .map(this::createRole)
                .forEach(roleRepository::save);
    }

    private TusupkaliMirasZhaugashNurzhanRole createRole(TusupkaliMirasZhaugashNurzhanRoleName roleName) {
        TusupkaliMirasZhaugashNurzhanRole role = new TusupkaliMirasZhaugashNurzhanRole();
        role.setName(roleName);
        role.setDescription(roleName.name() + " role for the University Management System");
        return role;
    }
}
