package it.live.educationtest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.RoleDTO;
import it.live.educationtest.payload.UserDTO;
import it.live.educationtest.service.RoleadminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/role-admin")
@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
@Tag(name = "Admin")
public class RoleAdminController {
    private final RoleadminService roleadminService;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> add(@Valid @RequestBody RoleDTO roleAdminDTO) {
        return roleadminService.add(roleAdminDTO);
    }


    @GetMapping("/getAllAdmin")
    public List<UserDTO> getAllAdmin() {
        return roleadminService.getAllAdmin();
    }


    @PutMapping("/update/{adminId}")
    public ResponseEntity<ApiResponse> updateAdmin(@PathVariable Long adminId, @Valid @RequestBody RoleDTO roleAdminDTO) {
        return roleadminService.updateAdmin(adminId, roleAdminDTO);
    }
}
