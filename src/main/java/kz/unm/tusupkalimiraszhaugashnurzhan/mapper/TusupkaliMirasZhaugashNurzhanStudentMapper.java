package kz.unm.tusupkalimiraszhaugashnurzhan.mapper;

import java.util.List;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanStudentRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanStudentResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanEnrollment;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanStudent;
import org.springframework.stereotype.Component;

@Component
public class TusupkaliMirasZhaugashNurzhanStudentMapper {

    public TusupkaliMirasZhaugashNurzhanStudent toEntity(TusupkaliMirasZhaugashNurzhanStudentRequestDto request) {
        TusupkaliMirasZhaugashNurzhanStudent student = new TusupkaliMirasZhaugashNurzhanStudent();
        updateEntity(student, request);
        return student;
    }

    public void updateEntity(
            TusupkaliMirasZhaugashNurzhanStudent student,
            TusupkaliMirasZhaugashNurzhanStudentRequestDto request) {
        student.setFirstName(request.firstName());
        student.setLastName(request.lastName());
        student.setEmail(request.email());
        student.setStudentNumber(request.studentNumber());
        student.setDateOfBirth(request.dateOfBirth());
        student.setPhoneNumber(request.phoneNumber());
        student.setAddress(request.address());
    }

    public TusupkaliMirasZhaugashNurzhanStudentResponseDto toResponse(TusupkaliMirasZhaugashNurzhanStudent student) {
        Long departmentId = student.getDepartment() == null ? null : student.getDepartment().getId();
        String departmentName = student.getDepartment() == null ? null : student.getDepartment().getName();
        Long userId = student.getUser() == null ? null : student.getUser().getId();
        List<Long> courseIds = student.getEnrollments().stream()
                .map(TusupkaliMirasZhaugashNurzhanEnrollment::getCourse)
                .map(course -> course == null ? null : course.getId())
                .toList();

        return new TusupkaliMirasZhaugashNurzhanStudentResponseDto(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getStudentNumber(),
                student.getDateOfBirth(),
                student.getPhoneNumber(),
                student.getAddress(),
                departmentId,
                departmentName,
                userId,
                courseIds
        );
    }
}
