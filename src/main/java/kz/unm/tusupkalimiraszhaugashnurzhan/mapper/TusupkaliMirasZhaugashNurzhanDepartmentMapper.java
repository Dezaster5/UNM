package kz.unm.tusupkalimiraszhaugashnurzhan.mapper;

import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanDepartmentRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanDepartmentResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanDepartment;
import org.springframework.stereotype.Component;

@Component
public class TusupkaliMirasZhaugashNurzhanDepartmentMapper {

    public TusupkaliMirasZhaugashNurzhanDepartment toEntity(
            TusupkaliMirasZhaugashNurzhanDepartmentRequestDto request) {
        TusupkaliMirasZhaugashNurzhanDepartment department =
                new TusupkaliMirasZhaugashNurzhanDepartment();
        updateEntity(department, request);
        return department;
    }

    public void updateEntity(
            TusupkaliMirasZhaugashNurzhanDepartment department,
            TusupkaliMirasZhaugashNurzhanDepartmentRequestDto request) {
        department.setName(request.name());
        department.setCode(request.code());
        department.setDescription(request.description());
    }

    public TusupkaliMirasZhaugashNurzhanDepartmentResponseDto toResponse(
            TusupkaliMirasZhaugashNurzhanDepartment department) {
        return new TusupkaliMirasZhaugashNurzhanDepartmentResponseDto(
                department.getId(),
                department.getName(),
                department.getCode(),
                department.getDescription(),
                department.getStudents().size(),
                department.getTeachers().size()
        );
    }
}
