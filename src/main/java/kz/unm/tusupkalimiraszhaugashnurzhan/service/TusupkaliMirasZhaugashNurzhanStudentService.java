package kz.unm.tusupkalimiraszhaugashnurzhan.service;

import java.util.List;
import java.util.NoSuchElementException;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanStudentRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanStudentResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanDepartment;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanStudent;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanUser;
import kz.unm.tusupkalimiraszhaugashnurzhan.mapper.TusupkaliMirasZhaugashNurzhanStudentMapper;
import kz.unm.tusupkalimiraszhaugashnurzhan.repository.TusupkaliMirasZhaugashNurzhanDepartmentRepository;
import kz.unm.tusupkalimiraszhaugashnurzhan.repository.TusupkaliMirasZhaugashNurzhanStudentRepository;
import kz.unm.tusupkalimiraszhaugashnurzhan.repository.TusupkaliMirasZhaugashNurzhanUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TusupkaliMirasZhaugashNurzhanStudentService {

    private final TusupkaliMirasZhaugashNurzhanStudentRepository studentRepository;
    private final TusupkaliMirasZhaugashNurzhanDepartmentRepository departmentRepository;
    private final TusupkaliMirasZhaugashNurzhanUserRepository userRepository;
    private final TusupkaliMirasZhaugashNurzhanStudentMapper studentMapper;

    public TusupkaliMirasZhaugashNurzhanStudentService(
            TusupkaliMirasZhaugashNurzhanStudentRepository studentRepository,
            TusupkaliMirasZhaugashNurzhanDepartmentRepository departmentRepository,
            TusupkaliMirasZhaugashNurzhanUserRepository userRepository,
            TusupkaliMirasZhaugashNurzhanStudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.studentMapper = studentMapper;
    }

    @Transactional(readOnly = true)
    public List<TusupkaliMirasZhaugashNurzhanStudentResponseDto> findAll() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TusupkaliMirasZhaugashNurzhanStudentResponseDto findById(Long id) {
        return studentMapper.toResponse(getStudentEntity(id));
    }

    @Transactional
    public TusupkaliMirasZhaugashNurzhanStudentResponseDto create(
            TusupkaliMirasZhaugashNurzhanStudentRequestDto request) {
        if (studentRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Student email already exists: " + request.email());
        }
        if (studentRepository.existsByStudentNumber(request.studentNumber())) {
            throw new IllegalArgumentException("Student number already exists: " + request.studentNumber());
        }
        TusupkaliMirasZhaugashNurzhanStudent student = studentMapper.toEntity(request);
        assignRelations(student, request);
        return studentMapper.toResponse(studentRepository.save(student));
    }

    @Transactional
    public TusupkaliMirasZhaugashNurzhanStudentResponseDto update(
            Long id,
            TusupkaliMirasZhaugashNurzhanStudentRequestDto request) {
        TusupkaliMirasZhaugashNurzhanStudent student = getStudentEntity(id);
        studentRepository.findByEmail(request.email())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Student email already exists: " + request.email());
                });
        studentMapper.updateEntity(student, request);
        assignRelations(student, request);
        return studentMapper.toResponse(studentRepository.save(student));
    }

    @Transactional
    public void delete(Long id) {
        TusupkaliMirasZhaugashNurzhanStudent student = getStudentEntity(id);
        studentRepository.delete(student);
    }

    @Transactional(readOnly = true)
    public TusupkaliMirasZhaugashNurzhanStudent getStudentEntity(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student not found with id: " + id));
    }

    private void assignRelations(
            TusupkaliMirasZhaugashNurzhanStudent student,
            TusupkaliMirasZhaugashNurzhanStudentRequestDto request) {
        TusupkaliMirasZhaugashNurzhanDepartment department = request.departmentId() == null
                ? null
                : departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new NoSuchElementException(
                        "Department not found with id: " + request.departmentId()));
        TusupkaliMirasZhaugashNurzhanUser user = request.userId() == null
                ? null
                : userRepository.findById(request.userId())
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + request.userId()));
        student.setDepartment(department);
        student.setUser(user);
    }
}
