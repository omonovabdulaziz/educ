package it.live.educationtest.mapper;

import it.live.educationtest.entity.EducationLocation;
import it.live.educationtest.exception.NotFoundException;
import it.live.educationtest.payload.EducationLocationDTO;
import it.live.educationtest.repository.EducationLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducationMapper {
    private final EducationLocationRepository educationLocationRepository;

    public EducationLocation toEntity(EducationLocationDTO educationLocationDTO) {
        return EducationLocation
                .builder()
                .name(educationLocationDTO.getName())
                .IsValid(educationLocationDTO.getIsValid())
                .build();
    }

    public EducationLocation toEntity(EducationLocationDTO educationLocationDTO, Long educationId) {
        EducationLocation education = educationLocationRepository.findById(educationId).orElseThrow(() -> new NotFoundException("Bunday education joyi mavjud emas"));

        education.setName(educationLocationDTO.getName());
        education.setIsValid(educationLocationDTO.getIsValid());
        return education;
    }
}
