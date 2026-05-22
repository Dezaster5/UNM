package kz.unm.tusupkalimiraszhaugashnurzhan.repository;

import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TusupkaliMirasZhaugashNurzhanDepartmentRepository
        extends JpaRepository<TusupkaliMirasZhaugashNurzhanDepartment, Long> {

    boolean existsByCode(String code);
}
