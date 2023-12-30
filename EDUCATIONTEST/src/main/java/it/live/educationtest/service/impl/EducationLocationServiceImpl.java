package it.live.educationtest.service.impl;

import it.live.educationtest.entity.EducationLocation;
import it.live.educationtest.exception.MainException;
import it.live.educationtest.exception.NotFoundException;
import it.live.educationtest.mapper.EducationMapper;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.EducationLocationDTO;
import it.live.educationtest.repository.EducationLocationRepository;
import it.live.educationtest.service.EducationLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationLocationServiceImpl implements EducationLocationService {
    private final EducationLocationRepository educationLocationRepository;
    private final EducationMapper educationMapper;

    @Override
    public ResponseEntity<ApiResponse> addEducation(EducationLocationDTO educationLocationDTO) {
        if (educationLocationRepository.existsByName(educationLocationDTO.getName()))
            throw new MainException("Bunday nomli Education joyi mavjud");
        educationLocationRepository.save(educationMapper.toEntity(educationLocationDTO));
        return ResponseEntity.ok(ApiResponse.builder().message("Education joyi qo'shildi").status(201).build());
    }


    @Override
    public List<EducationLocation> getEducation(Long educationLocationId) {
        if (educationLocationId != null)
            return Collections.singletonList(educationLocationRepository.findById(educationLocationId).orElseThrow(() -> new NotFoundException("Bunday education joyi mavjud emas")));

        return educationLocationRepository.findAll();
    }

    @Override
    public ResponseEntity<ApiResponse> updateEducation(EducationLocationDTO educationLocationDTO, Long educationId) {
        educationLocationRepository.save(educationMapper.toEntity(educationLocationDTO, educationId));
        return ResponseEntity.ok(ApiResponse.builder().message("Education joyi yangilandi").status(200).build());
    }
}
