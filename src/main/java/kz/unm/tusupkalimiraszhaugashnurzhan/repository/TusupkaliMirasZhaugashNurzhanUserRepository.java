package kz.unm.tusupkalimiraszhaugashnurzhan.repository;

import java.util.Optional;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TusupkaliMirasZhaugashNurzhanUserRepository
        extends JpaRepository<TusupkaliMirasZhaugashNurzhanUser, Long> {

    Optional<TusupkaliMirasZhaugashNurzhanUser> findByUsername(String username);

    Optional<TusupkaliMirasZhaugashNurzhanUser> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
