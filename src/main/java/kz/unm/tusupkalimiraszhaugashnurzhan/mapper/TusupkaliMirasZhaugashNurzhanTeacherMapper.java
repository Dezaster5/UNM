package kz.unm.tusupkalimiraszhaugashnurzhan.mapper;

import java.util.List;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanTeacherRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanTeacherResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanCourse;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanTeacher;
import org.springframework.stereotype.Component;

@Component
public class TusupkaliMirasZhaugashNurzhanTeacherMapper {

    public TusupkaliMirasZhaugashNurzhanTeacher toEntity(TusupkaliMirasZhaugashNurzhanTeacherRequestDto request) {
        TusupkaliMirasZhaugashNurzhanTeacher teacher = new TusupkaliMirasZhaugashNurzhanTeacher();
        updateEntity(teacher, request);
        return teacher;
    }

    public void updateEntity(
            TusupkaliMirasZhaugashNurzhanTeacher teacher,
            TusupkaliMirasZhaugashNurzhanTeacherRequestDto request) {
        teacher.setFirstName(request.firstName());
        teacher.setLastName(request.lastName());
        teacher.setEmail(request.email());
        teacher.setEmployeeNumber(request.employeeNumber());
        teacher.setAcademicTitle(request.academicTitle());
        teacher.setHireDate(request.hireDate());
    }

    public TusupkaliMirasZhaugashNurzhanTeacherResponseDto toResponse(TusupkaliMirasZhaugashNurzhanTeacher teacher) {
        Long departmentId = teacher.getDepartment() == null ? null : teacher.getDepartment().getId();
        String departmentName = teacher.getDepartment() == null ? null : teacher.getDepartment().getName();
        Long userId = teacher.getUser() == null ? null : teacher.getUser().getId();
        List<Long> courseIds = teacher.getCourses().stream()
                .map(TusupkaliMirasZhaugashNurzhanCourse::getId)
                .toList();

        return new TusupkaliMirasZhaugashNurzhanTeacherResponseDto(
                teacher.getId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getEmail(),
                teacher.getEmployeeNumber(),
                teacher.getAcademicTitle(),
                teacher.getHireDate(),
                departmentId,
                departmentName,
                userId,
                courseIds
        );
    }
}
