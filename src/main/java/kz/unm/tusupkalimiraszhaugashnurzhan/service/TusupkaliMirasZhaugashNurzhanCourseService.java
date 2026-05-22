package kz.unm.tusupkalimiraszhaugashnurzhan.service;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanCourseRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanCourseResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanCourse;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanTeacher;
import kz.unm.tusupkalimiraszhaugashnurzhan.mapper.TusupkaliMirasZhaugashNurzhanCourseMapper;
import kz.unm.tusupkalimiraszhaugashnurzhan.repository.TusupkaliMirasZhaugashNurzhanCourseRepository;
import kz.unm.tusupkalimiraszhaugashnurzhan.repository.TusupkaliMirasZhaugashNurzhanTeacherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class TusupkaliMirasZhaugashNurzhanCourseService {

    private static final Logger log = LoggerFactory.getLogger(TusupkaliMirasZhaugashNurzhanCourseService.class);

    private final TusupkaliMirasZhaugashNurzhanCourseRepository courseRepository;
    private final TusupkaliMirasZhaugashNurzhanTeacherRepository teacherRepository;
    private final TusupkaliMirasZhaugashNurzhanCourseMapper courseMapper;

    public TusupkaliMirasZhaugashNurzhanCourseService(
            TusupkaliMirasZhaugashNurzhanCourseRepository courseRepository,
            TusupkaliMirasZhaugashNurzhanTeacherRepository teacherRepository,
            TusupkaliMirasZhaugashNurzhanCourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.courseMapper = courseMapper;
    }

    @Transactional(readOnly = true)
    public List<TusupkaliMirasZhaugashNurzhanCourseResponseDto> findAll(Long teacherId, String search) {
        log.info("Listing courses teacherId={} search={}", teacherId, search);
        return courseRepository.findAll(buildSpecification(teacherId, search)).stream()
                .map(courseMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TusupkaliMirasZhaugashNurzhanCourseResponseDto findById(Long id) {
        return courseMapper.toResponse(getCourseEntity(id));
    }

    @Transactional
    public TusupkaliMirasZhaugashNurzhanCourseResponseDto create(
            TusupkaliMirasZhaugashNurzhanCourseRequestDto request) {
        if (courseRepository.existsByCode(request.code())) {
            throw new IllegalArgumentException("Course code already exists: " + request.code());
        }
        TusupkaliMirasZhaugashNurzhanCourse course = courseMapper.toEntity(request);
        assignTeacher(course, request.teacherId());
        TusupkaliMirasZhaugashNurzhanCourseResponseDto response =
                courseMapper.toResponse(courseRepository.save(course));
        log.info("Course created id={} code={}", response.id(), response.code());
        return response;
    }

    @Transactional
    public TusupkaliMirasZhaugashNurzhanCourseResponseDto update(
            Long id,
            TusupkaliMirasZhaugashNurzhanCourseRequestDto request) {
        TusupkaliMirasZhaugashNurzhanCourse course = getCourseEntity(id);
        courseRepository.findByCode(request.code())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Course code already exists: " + request.code());
                });
        courseMapper.updateEntity(course, request);
        assignTeacher(course, request.teacherId());
        TusupkaliMirasZhaugashNurzhanCourseResponseDto response =
                courseMapper.toResponse(courseRepository.save(course));
        log.info("Course updated id={} code={}", response.id(), response.code());
        return response;
    }

    @Transactional
    public void delete(Long id) {
        TusupkaliMirasZhaugashNurzhanCourse course = getCourseEntity(id);
        courseRepository.delete(course);
        log.info("Course deleted id={} code={}", id, course.getCode());
    }

    @Transactional(readOnly = true)
    public TusupkaliMirasZhaugashNurzhanCourse getCourseEntity(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Course not found with id: " + id));
    }

    private Specification<TusupkaliMirasZhaugashNurzhanCourse> buildSpecification(Long teacherId, String search) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (teacherId != null) {
                predicates.add(criteriaBuilder.equal(root.get("teacher").get("id"), teacherId));
            }
            if (StringUtils.hasText(search)) {
                String likeValue = "%" + search.toLowerCase() + "%";
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), likeValue),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("code")), likeValue)
                ));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private void assignTeacher(TusupkaliMirasZhaugashNurzhanCourse course, Long teacherId) {
        TusupkaliMirasZhaugashNurzhanTeacher teacher = teacherId == null
                ? null
                : teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NoSuchElementException("Teacher not found with id: " + teacherId));
        course.setTeacher(teacher);
    }
}
