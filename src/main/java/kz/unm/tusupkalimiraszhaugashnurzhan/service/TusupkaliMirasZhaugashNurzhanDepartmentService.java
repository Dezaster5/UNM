package kz.unm.tusupkalimiraszhaugashnurzhan.service;

import java.util.List;
import java.util.NoSuchElementException;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanDepartmentRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanDepartmentResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanDepartment;
import kz.unm.tusupkalimiraszhaugashnurzhan.mapper.TusupkaliMirasZhaugashNurzhanDepartmentMapper;
import kz.unm.tusupkalimiraszhaugashnurzhan.repository.TusupkaliMirasZhaugashNurzhanDepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TusupkaliMirasZhaugashNurzhanDepartmentService {

    private final TusupkaliMirasZhaugashNurzhanDepartmentRepository departmentRepository;
    private final TusupkaliMirasZhaugashNurzhanDepartmentMapper departmentMapper;

    public TusupkaliMirasZhaugashNurzhanDepartmentService(
            TusupkaliMirasZhaugashNurzhanDepartmentRepository departmentRepository,
            TusupkaliMirasZhaugashNurzhanDepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    @Transactional(readOnly = true)
    public List<TusupkaliMirasZhaugashNurzhanDepartmentResponseDto> findAll() {
        return departmentRepository.findAll().stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TusupkaliMirasZhaugashNurzhanDepartmentResponseDto findById(Long id) {
        return departmentMapper.toResponse(getDepartmentEntity(id));
    }

    @Transactional
    public TusupkaliMirasZhaugashNurzhanDepartmentResponseDto create(
            TusupkaliMirasZhaugashNurzhanDepartmentRequestDto request) {
        if (departmentRepository.existsByCode(request.code())) {
            throw new IllegalArgumentException("Department code already exists: " + request.code());
        }
        TusupkaliMirasZhaugashNurzhanDepartment department = departmentMapper.toEntity(request);
        return departmentMapper.toResponse(departmentRepository.save(department));
    }

    @Transactional
    public TusupkaliMirasZhaugashNurzhanDepartmentResponseDto update(
            Long id,
            TusupkaliMirasZhaugashNurzhanDepartmentRequestDto request) {
        TusupkaliMirasZhaugashNurzhanDepartment department = getDepartmentEntity(id);
        departmentMapper.updateEntity(department, request);
        return departmentMapper.toResponse(departmentRepository.save(department));
    }

    @Transactional
    public void delete(Long id) {
        departmentRepository.delete(getDepartmentEntity(id));
    }

    @Transactional(readOnly = true)
    public TusupkaliMirasZhaugashNurzhanDepartment getDepartmentEntity(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Department not found with id: " + id));
    }
}
