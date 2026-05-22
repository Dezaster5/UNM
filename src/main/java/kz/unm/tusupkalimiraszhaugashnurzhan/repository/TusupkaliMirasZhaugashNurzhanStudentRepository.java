package kz.unm.tusupkalimiraszhaugashnurzhan.repository;

import java.util.Optional;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TusupkaliMirasZhaugashNurzhanStudentRepository
        extends JpaRepository<TusupkaliMirasZhaugashNurzhanStudent, Long>,
        JpaSpecificationExecutor<TusupkaliMirasZhaugashNurzhanStudent> {

    Optional<TusupkaliMirasZhaugashNurzhanStudent> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByStudentNumber(String studentNumber);
}
