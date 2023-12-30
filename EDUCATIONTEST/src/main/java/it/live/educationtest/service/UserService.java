package it.live.educationtest.service;

import it.live.educationtest.entity.enums.SystemRoleName;
import it.live.educationtest.payload.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsersByGroupId(Long groupId);

    List<UserDTO> getRatingByGroupId(Long groupId);
}
