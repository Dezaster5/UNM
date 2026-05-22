package kz.unm.tusupkalimiraszhaugashnurzhan.service;

import java.util.List;
import java.util.NoSuchElementException;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanTeacherRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanTeacherResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanDepartment;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanTeacher;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanUser;
import kz.unm.tusupkalimiraszhaugashnurzhan.mapper.TusupkaliMirasZhaugashNurzhanTeacherMapper;
import kz.unm.tusupkalimiraszhaugashnurzhan.repository.TusupkaliMirasZhaugashNurzhanDepartmentRepository;
import kz.unm.tusupkalimiraszhaugashnurzhan.repository.TusupkaliMirasZhaugashNurzhanTeacherRepository;
import kz.unm.tusupkalimiraszhaugashnurzhan.repository.TusupkaliMirasZhaugashNurzhanUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TusupkaliMirasZhaugashNurzhanTeacherService {

    private final TusupkaliMirasZhaugashNurzhanTeacherRepository teacherRepository;
    private final TusupkaliMirasZhaugashNurzhanDepartmentRepository departmentRepository;
    private final TusupkaliMirasZhaugashNurzhanUserRepository userRepository;
    private final TusupkaliMirasZhaugashNurzhanTeacherMapper teacherMapper;

    public TusupkaliMirasZhaugashNurzhanTeacherService(
            TusupkaliMirasZhaugashNurzhanTeacherRepository teacherRepository,
            TusupkaliMirasZhaugashNurzhanDepartmentRepository departmentRepository,
            TusupkaliMirasZhaugashNurzhanUserRepository userRepository,
            TusupkaliMirasZhaugashNurzhanTeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.teacherMapper = teacherMapper;
    }

    @Transactional(readOnly = true)
    public List<TusupkaliMirasZhaugashNurzhanTeacherResponseDto> findAll() {
        return teacherRepository.findAll().stream()
                .map(teacherMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TusupkaliMirasZhaugashNurzhanTeacherResponseDto findById(Long id) {
        return teacherMapper.toResponse(getTeacherEntity(id));
    }

    @Transactional
    public TusupkaliMirasZhaugashNurzhanTeacherResponseDto create(
            TusupkaliMirasZhaugashNurzhanTeacherRequestDto request) {
        if (teacherRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Teacher email already exists: " + request.email());
        }
        if (teacherRepository.existsByEmployeeNumber(request.employeeNumber())) {
            throw new IllegalArgumentException("Employee number already exists: " + request.employeeNumber());
        }
        TusupkaliMirasZhaugashNurzhanTeacher teacher = teacherMapper.toEntity(request);
        assignRelations(teacher, request);
        return teacherMapper.toResponse(teacherRepository.save(teacher));
    }

    @Transactional
    public TusupkaliMirasZhaugashNurzhanTeacherResponseDto update(
            Long id,
            TusupkaliMirasZhaugashNurzhanTeacherRequestDto request) {
        TusupkaliMirasZhaugashNurzhanTeacher teacher = getTeacherEntity(id);
        teacherRepository.findByEmail(request.email())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Teacher email already exists: " + request.email());
                });
        teacherMapper.updateEntity(teacher, request);
        assignRelations(teacher, request);
        return teacherMapper.toResponse(teacherRepository.save(teacher));
    }

    @Transactional
    public void delete(Long id) {
        teacherRepository.delete(getTeacherEntity(id));
    }

    @Transactional(readOnly = true)
    public TusupkaliMirasZhaugashNurzhanTeacher getTeacherEntity(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Teacher not found with id: " + id));
    }

    private void assignRelations(
            TusupkaliMirasZhaugashNurzhanTeacher teacher,
            TusupkaliMirasZhaugashNurzhanTeacherRequestDto request) {
        TusupkaliMirasZhaugashNurzhanDepartment department = request.departmentId() == null
                ? null
                : departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new NoSuchElementException(
                        "Department not found with id: " + request.departmentId()));
        TusupkaliMirasZhaugashNurzhanUser user = request.userId() == null
                ? null
                : userRepository.findById(request.userId())
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + request.userId()));
        teacher.setDepartment(department);
        teacher.setUser(user);
    }
}
