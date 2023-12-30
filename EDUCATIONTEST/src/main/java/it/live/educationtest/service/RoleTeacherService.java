package it.live.educationtest.service;

import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.RoleDTO;
import it.live.educationtest.payload.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleTeacherService {
    ResponseEntity<ApiResponse> add(RoleDTO roleTeacherDTO);

    List<UserDTO> getAllTeacher(Long educationId);


    ResponseEntity<ApiResponse> updateTeacher(Long teacherId, RoleDTO roleTeacherDTO);

}
