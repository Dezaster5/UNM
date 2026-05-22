package kz.unm.tusupkalimiraszhaugashnurzhan.service.async;

import java.util.concurrent.CompletableFuture;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanPageResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanStudentResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.service.TusupkaliMirasZhaugashNurzhanStudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TusupkaliMirasZhaugashNurzhanAsyncReportService {

    private static final Logger log = LoggerFactory.getLogger(
            TusupkaliMirasZhaugashNurzhanAsyncReportService.class);

    private final TusupkaliMirasZhaugashNurzhanStudentService studentService;

    public TusupkaliMirasZhaugashNurzhanAsyncReportService(
            TusupkaliMirasZhaugashNurzhanStudentService studentService) {
        this.studentService = studentService;
    }

    @Async("tusupkaliMirasZhaugashNurzhanTaskExecutor")
    public CompletableFuture<String> generateStudentExport(
            String search,
            Long departmentId,
            Long courseId) {
        log.info("Async student export started");
        TusupkaliMirasZhaugashNurzhanPageResponseDto<TusupkaliMirasZhaugashNurzhanStudentResponseDto> page =
                studentService.findAll(0, 500, "lastName", "asc", search, departmentId, courseId);
        StringBuilder csv = new StringBuilder("id,studentNumber,firstName,lastName,email,departmentName\n");
        page.content().forEach(student -> csv.append(student.id()).append(',')
                .append(student.studentNumber()).append(',')
                .append(student.firstName()).append(',')
                .append(student.lastName()).append(',')
                .append(student.email()).append(',')
                .append(student.departmentName() == null ? "" : student.departmentName())
                .append('\n'));
        log.info("Async student export finished with {} rows", page.content().size());
        return CompletableFuture.completedFuture(csv.toString());
    }
}
