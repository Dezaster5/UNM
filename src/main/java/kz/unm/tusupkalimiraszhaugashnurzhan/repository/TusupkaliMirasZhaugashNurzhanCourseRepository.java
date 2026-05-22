package kz.unm.tusupkalimiraszhaugashnurzhan.repository;

import java.util.Optional;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TusupkaliMirasZhaugashNurzhanCourseRepository
        extends JpaRepository<TusupkaliMirasZhaugashNurzhanCourse, Long>,
        JpaSpecificationExecutor<TusupkaliMirasZhaugashNurzhanCourse> {

    Optional<TusupkaliMirasZhaugashNurzhanCourse> findByCode(String code);

    boolean existsByCode(String code);
}
