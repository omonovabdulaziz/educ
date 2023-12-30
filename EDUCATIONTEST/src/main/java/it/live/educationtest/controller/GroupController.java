package it.live.educationtest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.live.educationtest.entity.Group;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.GroupDTO;
import it.live.educationtest.payload.GroupResponseDTO;
import it.live.educationtest.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group")
@Tag(name = "group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_SUPER_ADMIN' , 'ROLE_TEACHER')")
    @PostMapping("addGroup")
    public ResponseEntity<ApiResponse> addGroup(@Valid @RequestBody GroupDTO groupDTO) {
        return groupService.addGroup(groupDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_SUPER_ADMIN' , 'ROLE_TEACHER')")
    @PutMapping("updateGroup/{groupId}")
    public ResponseEntity<ApiResponse> updateGroup(@PathVariable Long groupId, @RequestBody GroupDTO groupDTO) {
        return groupService.updateGroup(groupDTO, groupId);
    }

    @GetMapping("/getAllGroup")
    public Page<?> getAllGroup(@RequestParam(required = false) Long statusId, @RequestParam int page, @RequestParam int size) {
        return groupService.getAllGroup(statusId, page, size);
    }

    @GetMapping("/getAllGroupByTeacher/{teacherId}")
    public List<GroupResponseDTO> getAllGroupByTeacher(@PathVariable Long teacherId) {
        return groupService.getAllGroupByTeacherId(teacherId);
    }

    @GetMapping("/getGroupById/{groupId}")
    public Group getGroupById(@PathVariable Long groupId) {
        return groupService.getGroupById(groupId);
    }
}
