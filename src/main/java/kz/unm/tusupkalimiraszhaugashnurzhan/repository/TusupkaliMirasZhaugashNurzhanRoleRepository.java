package kz.unm.tusupkalimiraszhaugashnurzhan.repository;

import java.util.Optional;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanRole;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanRoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TusupkaliMirasZhaugashNurzhanRoleRepository
        extends JpaRepository<TusupkaliMirasZhaugashNurzhanRole, Long> {

    Optional<TusupkaliMirasZhaugashNurzhanRole> findByName(TusupkaliMirasZhaugashNurzhanRoleName name);
}
