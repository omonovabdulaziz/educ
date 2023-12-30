package it.live.educationtest.service;

import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.RoleDTO;
import it.live.educationtest.payload.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleadminService {
    ResponseEntity<ApiResponse> add(RoleDTO roleAdminDTO);

    List<UserDTO> getAllAdmin();

    ResponseEntity<ApiResponse> updateAdmin(Long adminId, RoleDTO roleAdminDTO);
}
