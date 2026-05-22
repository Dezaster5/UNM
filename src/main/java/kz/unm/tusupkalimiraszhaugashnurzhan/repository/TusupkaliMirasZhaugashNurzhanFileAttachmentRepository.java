package kz.unm.tusupkalimiraszhaugashnurzhan.repository;

import java.util.List;
import java.util.Optional;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanFileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TusupkaliMirasZhaugashNurzhanFileAttachmentRepository
        extends JpaRepository<TusupkaliMirasZhaugashNurzhanFileAttachment, Long> {

    Optional<TusupkaliMirasZhaugashNurzhanFileAttachment> findByStoredFileName(String storedFileName);

    List<TusupkaliMirasZhaugashNurzhanFileAttachment> findByStudentId(Long studentId);

    List<TusupkaliMirasZhaugashNurzhanFileAttachment> findByTeacherId(Long teacherId);

    List<TusupkaliMirasZhaugashNurzhanFileAttachment> findByCourseId(Long courseId);
}
