package it.live.educationtest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.RoleDTO;
import it.live.educationtest.payload.UserDTO;
import it.live.educationtest.service.RoleTeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController


@RequestMapping("/api/v1/role-teacher")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_SUPER_ADMIN')")
@Tag(name = "teacher")
public class RoleTeacherController {
    private final RoleTeacherService roleTeacherService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> add(@Valid @RequestBody RoleDTO roleTeacherDTO) {
        return roleTeacherService.add(roleTeacherDTO);
    }


    @GetMapping("/getAllTeacher")
    public List<UserDTO> getAllAdmin(@RequestParam(required = false) Long educationLocation) {
        return roleTeacherService.getAllTeacher(educationLocation);
    }


    @PutMapping("/update/{teacherId}")
    public ResponseEntity<ApiResponse> updateTeacher(@PathVariable Long teacherId, @Valid @RequestBody RoleDTO roleTeacherDTO) {
        return roleTeacherService.updateTeacher(teacherId, roleTeacherDTO);
    }
}