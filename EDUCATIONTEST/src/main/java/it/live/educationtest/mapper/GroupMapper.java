package it.live.educationtest.mapper;

import it.live.educationtest.entity.Group;
import it.live.educationtest.entity.enums.SystemRoleName;
import it.live.educationtest.exception.NotFoundException;
import it.live.educationtest.payload.GroupDTO;
import it.live.educationtest.payload.GroupResponseDTO;
import it.live.educationtest.repository.StatusRepository;
import it.live.educationtest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

@Service
@RequiredArgsConstructor
public class GroupMapper {
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;

    public Group toEntity(GroupDTO groupDTO) {
        return Group
                .builder()
                .teacher(userRepository.findByIdAndSystemRoleName(groupDTO.getTeacherId(), SystemRoleName.ROLE_TEACHER).orElseThrow(() -> new NotFoundException("Bunday teacher topilmadi")))
                .name(groupDTO.getName())
                .isValid(groupDTO.getIsValid())
                .status(statusRepository.findById(groupDTO.getStatusId()).orElseThrow(() -> new NotFoundException("Bunday status mavjud")))
                .build();
    }

    public Group toEntity(Group editGroup, GroupDTO groupDTO) {
        editGroup.setTeacher(userRepository.findByIdAndSystemRoleName(groupDTO.getTeacherId(), SystemRoleName.ROLE_TEACHER).orElseThrow(() -> new NotFoundException("Bunday teacher topilmadi")));
        editGroup.setIsValid(groupDTO.getIsValid());
        editGroup.setName(groupDTO.getName());
        editGroup.setStatus(statusRepository.findById(groupDTO.getStatusId()).orElseThrow(() -> new NotFoundException("Bunday status topilmadi")));
        return editGroup;
    }

    public GroupResponseDTO toDTO(Group group) {
        return GroupResponseDTO.builder()
                .id(group.getId())
                .name(group.getName())
                .isValid(group.getIsValid())
                .studentCount(userRepository.findAllByGroupIdAndSystemRoleName(group.getId(), SystemRoleName.ROLE_USER).size())
                .status(null).build();
    }
}
