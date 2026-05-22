package kz.unm.tusupkalimiraszhaugashnurzhan.mapper;

import java.util.List;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanCourseRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanCourseResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanCourse;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanEnrollment;
import org.springframework.stereotype.Component;

@Component
public class TusupkaliMirasZhaugashNurzhanCourseMapper {

    public TusupkaliMirasZhaugashNurzhanCourse toEntity(TusupkaliMirasZhaugashNurzhanCourseRequestDto request) {
        TusupkaliMirasZhaugashNurzhanCourse course = new TusupkaliMirasZhaugashNurzhanCourse();
        updateEntity(course, request);
        return course;
    }

    public void updateEntity(
            TusupkaliMirasZhaugashNurzhanCourse course,
            TusupkaliMirasZhaugashNurzhanCourseRequestDto request) {
        course.setCode(request.code());
        course.setTitle(request.title());
        course.setDescription(request.description());
        course.setCredits(request.credits());
        course.setSemester(request.semester());
        course.setStartDate(request.startDate());
        course.setEndDate(request.endDate());
    }

    public TusupkaliMirasZhaugashNurzhanCourseResponseDto toResponse(TusupkaliMirasZhaugashNurzhanCourse course) {
        Long teacherId = course.getTeacher() == null ? null : course.getTeacher().getId();
        String teacherName = course.getTeacher() == null
                ? null
                : course.getTeacher().getFirstName() + " " + course.getTeacher().getLastName();
        List<Long> studentIds = course.getEnrollments().stream()
                .map(TusupkaliMirasZhaugashNurzhanEnrollment::getStudent)
                .map(student -> student == null ? null : student.getId())
                .toList();

        return new TusupkaliMirasZhaugashNurzhanCourseResponseDto(
                course.getId(),
                course.getCode(),
                course.getTitle(),
                course.getDescription(),
                course.getCredits(),
                course.getSemester(),
                course.getStartDate(),
                course.getEndDate(),
                teacherId,
                teacherName,
                studentIds
        );
    }
}
