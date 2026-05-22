package kz.unm.tusupkalimiraszhaugashnurzhan.repository;

import java.util.List;
import java.util.Optional;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TusupkaliMirasZhaugashNurzhanEnrollmentRepository
        extends JpaRepository<TusupkaliMirasZhaugashNurzhanEnrollment, Long> {

    List<TusupkaliMirasZhaugashNurzhanEnrollment> findByStudentId(Long studentId);

    List<TusupkaliMirasZhaugashNurzhanEnrollment> findByCourseId(Long courseId);

    Optional<TusupkaliMirasZhaugashNurzhanEnrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}
