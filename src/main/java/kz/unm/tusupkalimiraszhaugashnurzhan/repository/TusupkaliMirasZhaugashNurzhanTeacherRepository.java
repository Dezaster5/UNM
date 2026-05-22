package kz.unm.tusupkalimiraszhaugashnurzhan.repository;

import java.util.Optional;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TusupkaliMirasZhaugashNurzhanTeacherRepository
        extends JpaRepository<TusupkaliMirasZhaugashNurzhanTeacher, Long>,
        JpaSpecificationExecutor<TusupkaliMirasZhaugashNurzhanTeacher> {

    Optional<TusupkaliMirasZhaugashNurzhanTeacher> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmployeeNumber(String employeeNumber);
}
