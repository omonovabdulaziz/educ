package it.live.educationtest.service.impl;

import it.live.educationtest.entity.User;
import it.live.educationtest.entity.enums.SystemRoleName;
import it.live.educationtest.exception.MainException;
import it.live.educationtest.exception.NotFoundException;
import it.live.educationtest.mapper.UserMapper;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.RoleDTO;
import it.live.educationtest.payload.UserDTO;
import it.live.educationtest.repository.EducationLocationRepository;
import it.live.educationtest.repository.UserRepository;
import it.live.educationtest.service.RoleadminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleAdminServiceImpl implements RoleadminService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EducationLocationRepository educationLocationRepository;

    @Override
    public ResponseEntity<ApiResponse> add(RoleDTO roleAdminDTO) {
        if (userRepository.existsByPhoneNumber(roleAdminDTO.getPhoneNumber()))
            throw new MainException("Bunday telefon raqamli foydalanuvchi mavjud");
        roleAdminDTO.setSystemRoleName(SystemRoleName.ROLE_ADMIN);
        User admin = userMapper.toRoleEntity(roleAdminDTO);
        admin.setEducationLocation(educationLocationRepository.findById(roleAdminDTO.getEducationId()).orElseThrow(() -> new NotFoundException("Bunday education joyi topilmadi")));
        userRepository.save(admin);
        return ResponseEntity.ok(ApiResponse.builder().message("Admin saqlandi").status(200).build());
    }

    @Override
    public List<UserDTO> getAllAdmin() {
        return userRepository.findAllBySystemRoleName(SystemRoleName.ROLE_ADMIN).stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<ApiResponse> updateAdmin(Long adminId, RoleDTO roleAdminDTO) {
        roleAdminDTO.setSystemRoleName(SystemRoleName.ROLE_ADMIN);
        User admin = userMapper.toRoleEntity(roleAdminDTO, adminId);
        admin.setEducationLocation(educationLocationRepository.findById(roleAdminDTO.getEducationId()).orElseThrow(() -> new NotFoundException("Bunday education joyi topilmadi")));
        userRepository.save(admin);
        return ResponseEntity.ok(ApiResponse.builder().message("Admin yangilandi").status(200).build());
    }
}
