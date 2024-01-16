package it.live.educationtest.service.impl;

import it.live.educationtest.config.SecurityConfiguration;
import it.live.educationtest.entity.User;
import it.live.educationtest.entity.enums.SystemRoleName;
import it.live.educationtest.exception.ForbiddenException;
import it.live.educationtest.exception.MainException;
import it.live.educationtest.exception.NotFoundException;
import it.live.educationtest.mapper.UserMapper;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.RoleDTO;
import it.live.educationtest.payload.UserDTO;
import it.live.educationtest.repository.EducationLocationRepository;
import it.live.educationtest.repository.StatusRepository;
import it.live.educationtest.repository.UserRepository;
import it.live.educationtest.service.RoleTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.SpringTransactionAnnotationParser;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleTeacherServiceImpl implements RoleTeacherService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EducationLocationRepository educationLocationRepository;
    private final StatusRepository statusRepository;
    private final PasswordEncoder passwordEncoder;

    @Override

    public ResponseEntity<ApiResponse> add(RoleDTO roleTeacherDTO) {
        if (userRepository.existsByPhoneNumber(roleTeacherDTO.getPhoneNumber()))
            throw new MainException("Ushbu raqam qo'lanilgan");
        if (!statusRepository.existsByEducationLocationIdAndId(roleTeacherDTO.getEducationId(), roleTeacherDTO.getStatusId()))
            throw new NotFoundException("Ushbu statusga ushbu education bog'lanmaagan");
        User systemUser = SecurityConfiguration.getOwnSecurityInformation();
        roleTeacherDTO.setSystemRoleName(SystemRoleName.ROLE_TEACHER);
        userRepository.save(checkRoleAndReturnUser(systemUser, roleTeacherDTO, userMapper.toRoleEntity(roleTeacherDTO)));
        return ResponseEntity.ok(ApiResponse.builder().message("Teacher saqlandi va ushbu status uchun biriktirldi").status(201).build());
    }

    @Override
    public List<UserDTO> getAllTeacher(Long educationId) {
        User systemUser = SecurityConfiguration.getOwnSecurityInformation();
        if (systemUser.getSystemRoleName() == SystemRoleName.ROLE_SUPER_ADMIN) {
            if (educationId == null)
                return userRepository.findAllBySystemRoleName(SystemRoleName.ROLE_TEACHER).stream().map(userMapper::toDTO).collect(Collectors.toList());
            return userRepository.findAllByEducationLocationIdAndSystemRoleName(educationId, SystemRoleName.ROLE_TEACHER).stream().map(userMapper::toDTO).collect(Collectors.toList());
        }
        return userRepository.findAllByEducationLocationIdAndSystemRoleName(systemUser.getEducationLocation().getId(), SystemRoleName.ROLE_TEACHER).stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<ApiResponse> updateTeacher(Long teacherId, RoleDTO roleTeacherDTO) {
        if (userRepository.phoneNumberCheck(roleTeacherDTO.getPhoneNumber(), teacherId) != 0)
            throw new MainException("Ushbu raqam qo'lanilgan");
        if (!statusRepository.existsByEducationLocationIdAndId(roleTeacherDTO.getEducationId(), roleTeacherDTO.getStatusId()))
            throw new NotFoundException("Ushbu statusga ushbu education bog'lanmaagan");
        User systemUser = SecurityConfiguration.getOwnSecurityInformation();
        roleTeacherDTO.setSystemRoleName(SystemRoleName.ROLE_TEACHER);
        User roleTeacher = userRepository.findByIdAndSystemRoleName(teacherId, SystemRoleName.ROLE_TEACHER).orElseThrow(() -> new NotFoundException("Bunday teacher autopilot"));
        roleTeacher.setName(roleTeacherDTO.getName());
        roleTeacher.setPhoneNumber(roleTeacherDTO.getPhoneNumber());
        roleTeacher.setSurname(roleTeacherDTO.getSurname());
        if (roleTeacher.getPassword() != null) {
            roleTeacher.setPassword(passwordEncoder.encode(roleTeacherDTO.getPassword()));
        }
        userRepository.save(checkRoleAndReturnUser(systemUser, roleTeacherDTO, roleTeacher));
        return ResponseEntity.ok(ApiResponse.builder().message("Teacher yangilandi").status(200).build());
    }

    ///extra methods
    public User checkRoleAndReturnUser(User systemUser, RoleDTO roleTeacherDTO, User roleTeacher) {
        if (systemUser.getSystemRoleName() == SystemRoleName.ROLE_SUPER_ADMIN) {
            roleTeacher.setEducationLocation(educationLocationRepository.findById(roleTeacherDTO.getEducationId()).orElseThrow(() -> new NotFoundException("Bunday education topilmadi")));
            roleTeacher.setStatus(statusRepository.findById(roleTeacherDTO.getStatusId()).orElseThrow(() -> new NotFoundException("Bunday status topilmadi")));
        } else {
            if (!Objects.equals(systemUser.getEducationLocation().getId(), roleTeacherDTO.getEducationId()))
                throw new ForbiddenException("Sizda ushbu yolga ruxsat yoq");
            roleTeacher.setEducationLocation(educationLocationRepository.findById(roleTeacherDTO.getEducationId()).orElseThrow(() -> new NotFoundException("Bunday education topilmadi")));
            roleTeacher.setStatus(statusRepository.findById(roleTeacherDTO.getStatusId()).orElseThrow(() -> new NotFoundException("Bunday status topilmadi")));
        }
        return roleTeacher;
    }
}

