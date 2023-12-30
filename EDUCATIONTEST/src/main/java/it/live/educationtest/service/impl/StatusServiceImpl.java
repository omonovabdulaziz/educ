package it.live.educationtest.service.impl;

import it.live.educationtest.config.SecurityConfiguration;
import it.live.educationtest.entity.Status;
import it.live.educationtest.entity.User;
import it.live.educationtest.entity.enums.SystemRoleName;
import it.live.educationtest.exception.MainException;
import it.live.educationtest.exception.NotFoundException;
import it.live.educationtest.mapper.StatusMapper;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.StatusDTO;
import it.live.educationtest.repository.EducationLocationRepository;
import it.live.educationtest.repository.StatusRepository;
import it.live.educationtest.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.security.SecurityConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;
    private final EducationLocationRepository educationLocationRepository;

    @Override
    public ResponseEntity<ApiResponse> addStatus(StatusDTO statusDTO) {
        User systemUser = SecurityConfiguration.getOwnSecurityInformation();
        if (systemUser.getSystemRoleName() == SystemRoleName.ROLE_ADMIN)
            if (!Objects.equals(systemUser.getEducationLocation().getId(), statusDTO.getEducationId()))
                throw new MainException("Sizni ushbu educationga status qo'shishga huquqingiz yoq");

        statusRepository.save(statusMapper.toEntity(statusDTO));
        return ResponseEntity.ok(ApiResponse.builder().message("Status saqlandi").status(201).build());
    }

    @Override
    public ResponseEntity<ApiResponse> updateStatus(Long statusId, StatusDTO statusDTO) {
        User systemUser = SecurityConfiguration.getOwnSecurityInformation();
        if (systemUser.getSystemRoleName() == SystemRoleName.ROLE_ADMIN)
            if (!Objects.equals(systemUser.getEducationLocation().getId(), statusDTO.getEducationId()))
                throw new MainException("Sizni ushbu educationga status qo'shishga huquqingiz yoq");
        Status status = statusRepository.findById(statusId).orElseThrow(() -> new NotFoundException("Bunday status topilmadi"));
        statusRepository.save(statusMapper.toEntity(status, statusDTO));
        return ResponseEntity.ok(ApiResponse.builder().message("Status yangilandi").status(200).build());
    }

    @Override
    public List<Status> getAllStatusByEducationId(Long educationId) {
        if (educationId != null)
            return statusRepository.findAllByEducationLocationId(educationId);
        return statusRepository.findAll();
    }
}
