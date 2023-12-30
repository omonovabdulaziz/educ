package it.live.educationtest.service;


import it.live.educationtest.entity.Group;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.GroupDTO;
import it.live.educationtest.payload.GroupResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GroupService {
    ResponseEntity<ApiResponse> addGroup(GroupDTO groupDTO);

    ResponseEntity<ApiResponse> updateGroup(GroupDTO groupDTO , Long groupId);

    Page<?> getAllGroup( Long statusId , int page , int size);

    List<GroupResponseDTO> getAllGroupByTeacherId(Long teacherId);

    Group getGroupById(Long groupId);
}
