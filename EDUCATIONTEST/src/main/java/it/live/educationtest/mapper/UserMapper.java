package it.live.educationtest.mapper;

import it.live.educationtest.entity.EducationLocation;
import it.live.educationtest.entity.User;
import it.live.educationtest.entity.enums.SystemRoleName;
import it.live.educationtest.exception.NotFoundException;
import it.live.educationtest.payload.RegisterDTO;
import it.live.educationtest.payload.RoleDTO;
import it.live.educationtest.payload.UserDTO;
import it.live.educationtest.repository.EducationLocationRepository;
import it.live.educationtest.repository.GroupRepository;
import it.live.educationtest.repository.StatusRepository;
import it.live.educationtest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final EducationLocationRepository educationLocationRepository;

    public User toEntity(RegisterDTO registerUserDTO, Integer phoneCode) {
        return User.builder()
                .status(statusRepository.findById(registerUserDTO.getStatusId()).orElseThrow(() -> new NotFoundException("Bunday status mavjud emas")))
                .educationLocation(educationLocationRepository.findById(registerUserDTO.getEducationId()).orElseThrow(() -> new NotFoundException("Bunday education joyi mavjud emas")))
                .ball(0)
                .name(registerUserDTO.getName())
                .enabled(false)
                .group(groupRepository.findById(registerUserDTO.getGroupId()).orElseThrow(() -> new NotFoundException("Bunday guruh mavjud emas")))
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .surname(registerUserDTO.getSurname())
                .password(passwordEncoder.encode(registerUserDTO.getPassword()))
                .systemRoleName(SystemRoleName.ROLE_USER)
                .phoneNumber(registerUserDTO.getPhoneNumber())
                .phoneCode(phoneCode).build();
    }

    public UserDTO toDTO(User user) {
        return UserDTO
                .builder()
                .id(user.getId())
                .ball(user.getBall())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .educationLocation(user.getEducationLocation())
                .name(user.getName()).build();
    }

    public User toRoleEntity(RoleDTO roleAdminDTO) {
        return User
                .builder()
                .ball(0)
                .name(roleAdminDTO.getName())
                .phoneNumber(roleAdminDTO.getPhoneNumber())
                .surname(roleAdminDTO.getSurname())
                .enabled(true)
                .isCredentialsNonExpired(true)
                .isAccountNonLocked(true)
                .isAccountNonExpired(true)
                .password(passwordEncoder.encode(roleAdminDTO.getPassword()))
                .systemRoleName(roleAdminDTO.getSystemRoleName())
                .build();
    }

    public User toRoleEntity(RoleDTO roleAdminDTO, Long adminId) {
        User user = userRepository.findByIdAndSystemRoleName(adminId, SystemRoleName.ROLE_ADMIN).orElseThrow(() -> new NotFoundException("Bunday admin mavjud emas"));
        user.setPassword(passwordEncoder.encode(roleAdminDTO.getPassword()));
        user.setSurname(roleAdminDTO.getSurname());
        user.setName(roleAdminDTO.getName());
        user.setSystemRoleName(roleAdminDTO.getSystemRoleName());
        user.setPhoneNumber(roleAdminDTO.getPhoneNumber());
        return user;
    }
}
