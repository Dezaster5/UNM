package kz.unm.tusupkalimiraszhaugashnurzhan.service;

import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanAuthRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanAuthResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanRegisterRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanUserResponseDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanRole;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanRoleName;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanUser;
import kz.unm.tusupkalimiraszhaugashnurzhan.exception.TusupkaliMirasZhaugashNurzhanBadRequestException;
import kz.unm.tusupkalimiraszhaugashnurzhan.mapper.TusupkaliMirasZhaugashNurzhanUserMapper;
import kz.unm.tusupkalimiraszhaugashnurzhan.repository.TusupkaliMirasZhaugashNurzhanRoleRepository;
import kz.unm.tusupkalimiraszhaugashnurzhan.repository.TusupkaliMirasZhaugashNurzhanUserRepository;
import kz.unm.tusupkalimiraszhaugashnurzhan.security.TusupkaliMirasZhaugashNurzhanJwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TusupkaliMirasZhaugashNurzhanAuthService {

    private final TusupkaliMirasZhaugashNurzhanUserRepository userRepository;
    private final TusupkaliMirasZhaugashNurzhanRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TusupkaliMirasZhaugashNurzhanUserMapper userMapper;
    private final TusupkaliMirasZhaugashNurzhanJwtUtil jwtUtil;

    public TusupkaliMirasZhaugashNurzhanAuthService(
            TusupkaliMirasZhaugashNurzhanUserRepository userRepository,
            TusupkaliMirasZhaugashNurzhanRoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            TusupkaliMirasZhaugashNurzhanUserMapper userMapper,
            TusupkaliMirasZhaugashNurzhanJwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public TusupkaliMirasZhaugashNurzhanUserResponseDto register(
            TusupkaliMirasZhaugashNurzhanRegisterRequestDto request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new TusupkaliMirasZhaugashNurzhanBadRequestException(
                    "Username already exists: " + request.username());
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new TusupkaliMirasZhaugashNurzhanBadRequestException("Email already exists: " + request.email());
        }

        TusupkaliMirasZhaugashNurzhanUser user = new TusupkaliMirasZhaugashNurzhanUser();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFullName(request.fullName());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(resolveRole(request.role()));
        user.setEnabled(true);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public TusupkaliMirasZhaugashNurzhanAuthResponseDto login(
            TusupkaliMirasZhaugashNurzhanAuthRequestDto request) {
        TusupkaliMirasZhaugashNurzhanUser user = userRepository.findByUsername(request.usernameOrEmail())
                .or(() -> userRepository.findByEmail(request.usernameOrEmail()))
                .orElseThrow(() -> new TusupkaliMirasZhaugashNurzhanBadRequestException("Invalid credentials"));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new TusupkaliMirasZhaugashNurzhanBadRequestException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(user);
        return new TusupkaliMirasZhaugashNurzhanAuthResponseDto(
                token,
                "Bearer",
                jwtUtil.getJwtExpiration(),
                user.getUsername(),
                user.getRole().getName().name()
        );
    }

    private TusupkaliMirasZhaugashNurzhanRole resolveRole(TusupkaliMirasZhaugashNurzhanRoleName requestedRole) {
        TusupkaliMirasZhaugashNurzhanRoleName roleName = requestedRole == null
                ? TusupkaliMirasZhaugashNurzhanRoleName.STUDENT
                : requestedRole;
        return roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    TusupkaliMirasZhaugashNurzhanRole role = new TusupkaliMirasZhaugashNurzhanRole();
                    role.setName(roleName);
                    role.setDescription(roleName.name() + " role");
                    return roleRepository.save(role);
                });
    }
}
