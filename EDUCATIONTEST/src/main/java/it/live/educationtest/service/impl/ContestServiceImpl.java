package it.live.educationtest.service.impl;

import com.sun.tools.jconsole.JConsoleContext;
import it.live.educationtest.config.SecurityConfiguration;
import it.live.educationtest.entity.Contest;
import it.live.educationtest.entity.Group;
import it.live.educationtest.entity.User;
import it.live.educationtest.entity.enums.SystemRoleName;
import it.live.educationtest.exception.MainException;
import it.live.educationtest.exception.NotFoundException;
import it.live.educationtest.mapper.ContestMapper;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.ContestDTO;
import it.live.educationtest.repository.ContestRepository;
import it.live.educationtest.repository.GroupRepository;
import it.live.educationtest.service.ContestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ContestServiceImpl implements ContestService {
    private final ContestRepository contestRepository;
    private final GroupRepository groupRepository;
    private final ContestMapper contestMapper;

    @Override
    public ResponseEntity<ApiResponse> addContest(ContestDTO contestDTO) {
        if (contestRepository.existsByTitleAndGroupId(contestDTO.getTitle(), contestDTO.getGroupId()))
            throw new MainException("Bunday contest nomi ushbu guruhda mavjud");
        User systemUser = SecurityConfiguration.getOwnSecurityInformation();
        Group group = groupRepository.findById(contestDTO.getGroupId()).orElseThrow(() -> new NotFoundException("Bunday guruh mavjud emas"));
        checkRolePermission(systemUser, group);
        contestRepository.save(contestMapper.toEntity(contestDTO));
        return ResponseEntity.ok(ApiResponse.builder().message("Contest qo'shildi").status(201).build());
    }

    @Override
    public ResponseEntity<ApiResponse> updateContest(Long contestId, ContestDTO contestDTO) {
        User systemUser = SecurityConfiguration.getOwnSecurityInformation();
        Group group = groupRepository.findById(contestDTO.getGroupId()).orElseThrow(() -> new NotFoundException("Bunday guruh mavjud emas"));
        Contest contest = contestRepository.findById(contestId).orElseThrow(() -> new NotFoundException("Bunday contest topilmadi"));
        checkRolePermission(systemUser, group);
        contestRepository.save(contestMapper.toEntity(contestDTO, contest));
        return ResponseEntity.ok(ApiResponse.builder().message("Contest yangilandi").status(200).build());
    }

    @Override
    public Page<ContestDTO> getAllContextByGroupId(Long groupId, int page, int size) {
        return contestRepository.findAll(PageRequest.of(page, size, Sort.by("isValid"))).map(contestMapper::toDTO);
    }

    @Override
    public ContestDTO getContextById(Long contextId) {
        return contestMapper.toDTO(contestRepository.findById(contextId).orElseThrow(() -> new NotFoundException("Bunday contest mavjud emas")));
    }


    public void checkRolePermission(User systemUser, Group group) {
        if (systemUser.getSystemRoleName() == SystemRoleName.ROLE_TEACHER)
            if (!Objects.equals(group.getTeacher().getId(), systemUser.getId()))
                throw new MainException("Sizga ushbu guruhga contest qo'shishga ruxsat yoq");
        if (systemUser.getSystemRoleName() == SystemRoleName.ROLE_ADMIN)
            if (!Objects.equals(systemUser.getEducationLocation().getId(), group.getStatus().getEducationLocation().getId()))
                throw new MainException("Sizda ushbu guruhga contest qo'shishga ruxsat yoq sizga biriktirlgan education boshqa");
    }
}
