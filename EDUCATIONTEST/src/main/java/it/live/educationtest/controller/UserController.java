package it.live.educationtest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.live.educationtest.config.SecurityConfiguration;
import it.live.educationtest.entity.User;
import it.live.educationtest.entity.enums.SystemRoleName;
import it.live.educationtest.payload.UserDTO;
import it.live.educationtest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "users")
public class UserController {
    private final UserService userService;

    @GetMapping("/getMyOwnInformation")
    public User getUser() {
        return SecurityConfiguration.getOwnSecurityInformation();
    }

    @GetMapping("/getAllSystemRoleName")
    public List<SystemRoleName> getAllSystemRoleName() {
        return Arrays.stream(SystemRoleName.values()).toList();
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN' , 'ROLE_ADMIN' , 'ROLE_TEACHER')")
    @GetMapping("/getAllUsersByGroupId")
    public List<UserDTO> getAllUsersByDynamic(@RequestParam(required = false) Long groupId) {
        return userService.getAllUsersByGroupId(groupId);
    }

    @GetMapping("/getRatingByGroupId")
    public List<UserDTO> getRatingByGroupId(@RequestParam(required = false) Long groupId) {
        return userService.getRatingByGroupId(groupId);
    }
}
