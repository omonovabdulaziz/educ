package it.live.educationtest.service;

import it.live.educationtest.entity.EducationLocation;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.EducationLocationDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EducationLocationService {
    ResponseEntity<ApiResponse> addEducation(EducationLocationDTO educationLocation);

    List<EducationLocation> getEducation(Long educationLocationId);

    ResponseEntity<ApiResponse> updateEducation(EducationLocationDTO educationLocation, Long educationId);

}
