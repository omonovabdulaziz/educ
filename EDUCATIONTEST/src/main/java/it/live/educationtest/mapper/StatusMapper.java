package it.live.educationtest.mapper;

import it.live.educationtest.entity.Status;
import it.live.educationtest.exception.NotFoundException;
import it.live.educationtest.payload.StatusDTO;
import it.live.educationtest.repository.EducationLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusMapper {
    private final EducationLocationRepository educationLocationRepository;

    public Status toEntity(StatusDTO statusDTO) {
        return Status.builder()
                .isValid(statusDTO.getIsValid())
                .educationLocation(educationLocationRepository.findById(statusDTO.getEducationId()).orElseThrow(() -> new NotFoundException("Bunday Education topilmadi")))
                .name(statusDTO.getName()).build();
    }

    public Status toEntity(Status status, StatusDTO statusDTO) {
        status.setName(statusDTO.getName());
        status.setEducationLocation(educationLocationRepository.findById(statusDTO.getEducationId()).orElseThrow(() -> new NotFoundException("Bunday Education topilmadi")));
        status.setIsValid(statusDTO.getIsValid());
        return status;
    }
}
