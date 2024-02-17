package it.live.educationtest.service.impl;

import it.live.educationtest.config.SecurityConfiguration;
import it.live.educationtest.entity.User;
import it.live.educationtest.entity.enums.SystemRoleName;
import it.live.educationtest.mapper.UserMapper;
import it.live.educationtest.payload.UserDTO;
import it.live.educationtest.repository.UserRepository;
import it.live.educationtest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDTO> getAllUsersByGroupId(Long groupId) {
        User systemUser = SecurityConfiguration.getOwnSecurityInformation();
        if (systemUser.getSystemRoleName() == SystemRoleName.ROLE_USER)
            return userRepository.findAllByGroupIdAndSystemRoleName(systemUser.getGroup().getId(), SystemRoleName.ROLE_USER).stream().map(userMapper::toDTO).collect(Collectors.toList());
        return userRepository.findAllByGroupIdAndSystemRoleName(groupId, SystemRoleName.ROLE_USER).stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getRatingByGroupId(Long groupId) {
        User systemUser = SecurityConfiguration.getOwnSecurityInformation();
        if (systemUser.getSystemRoleName() == SystemRoleName.ROLE_USER)
            return userRepository.findAllByGroupIdAndSystemRoleNameOrderByBallDesc(systemUser.getGroup().getId(), SystemRoleName.ROLE_USER).stream().map(userMapper::toDTO).collect(Collectors.toList());
        return userRepository.findAllByGroupIdAndSystemRoleNameOrderByBallDesc(groupId, SystemRoleName.ROLE_USER).stream().map(userMapper::toDTO).collect(Collectors.toList());
    }
}
