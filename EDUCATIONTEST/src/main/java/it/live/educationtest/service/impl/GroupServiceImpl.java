package it.live.educationtest.service.impl;

import it.live.educationtest.config.SecurityConfiguration;
import it.live.educationtest.entity.Group;
import it.live.educationtest.entity.User;
import it.live.educationtest.entity.enums.SystemRoleName;
import it.live.educationtest.exception.MainException;
import it.live.educationtest.exception.NotFoundException;
import it.live.educationtest.mapper.GroupMapper;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.GroupDTO;
import it.live.educationtest.payload.GroupResponseDTO;
import it.live.educationtest.repository.GroupRepository;
import it.live.educationtest.repository.StatusRepository;
import it.live.educationtest.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final StatusRepository statusRepository;

    @Override
    public ResponseEntity<ApiResponse> addGroup(GroupDTO groupDTO) {
        if (groupRepository.existsByNameAndStatusId(groupDTO.getName(), groupDTO.getStatusId()))
            throw new MainException("Bunday nomli guruh ushbu statusda mavjud");
        User systemUser = SecurityConfiguration.getOwnSecurityInformation();
        checkRolePermissions(systemUser, groupDTO);
        groupRepository.save(groupMapper.toEntity(groupDTO));
        return ResponseEntity.ok(ApiResponse.builder().message("Guruh qo'shildi").status(201).build());
    }

    @Override
    public ResponseEntity<ApiResponse> updateGroup(GroupDTO groupDTO, Long groupId) {
        User systemUser = SecurityConfiguration.getOwnSecurityInformation();
        checkRolePermissions(systemUser, groupDTO);
        Group editGroup = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Bunday guruh topiladi"));
        groupRepository.save(groupMapper.toEntity(editGroup, groupDTO));
        return ResponseEntity.ok(ApiResponse.builder().message("Gruppa yangilandi").status(200).build());
    }

    @Override
    public Page<?> getAllGroup(Long statusId, int page, int size) {
        if (statusId != null)
            return groupRepository.findAllByStatusId(statusId, PageRequest.of(page, size)).map(groupMapper::toDTO);
        return groupRepository.findAll(PageRequest.of(page, size)).map(groupMapper::toDTO);
    }

    @Override
    public List<GroupResponseDTO> getAllGroupByTeacherId(Long teacherId) {
        return groupRepository.findAllByTeacherId(teacherId).stream().map(groupMapper::toDTO).toList();
    }

    @Override
    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException("Bunday guruh mavjud emas"));
    }

    public void checkRolePermissions(User systemUser, GroupDTO groupDTO) {
        if (systemUser.getSystemRoleName() == SystemRoleName.ROLE_TEACHER)
            if (!Objects.equals(systemUser.getStatus().getId(), groupDTO.getStatusId()))
                throw new MainException("Sizga ushbu statusga guruh qo'shish mumkin emas");
        if (systemUser.getSystemRoleName() == SystemRoleName.ROLE_ADMIN)
            if (!Objects.equals(systemUser.getEducationLocation().getId(), statusRepository.findById(groupDTO.getStatusId()).orElseThrow(() -> new NotFoundException("Bunday status mavjud emas")).getEducationLocation().getId()))
                throw new MainException("Sizga ushbu educationning statusiga  guruh qo'shishingiz mumkin emas");
    }
}
