package it.live.educationtest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.live.educationtest.entity.EducationLocation;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.EducationLocationDTO;
import it.live.educationtest.service.EducationLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/education")
@Tag(name = "Education")
public class EducationLocationController {
    private final EducationLocationService educationLocationService;

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/addEducation")
    public ResponseEntity<ApiResponse> addEducation(@RequestBody EducationLocationDTO educationLocation) {
        return educationLocationService.addEducation(educationLocation);
    }


    @GetMapping("/getEducation")
    public List<EducationLocation> getEducation(@RequestParam(required = false) Long educationLocationId) {
        return educationLocationService.getEducation(educationLocationId);
    }


    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/updateEducation/{educationId}")
    public ResponseEntity<ApiResponse> updateEducation(@RequestBody EducationLocationDTO educationLocation, @PathVariable Long educationId) {
        return educationLocationService.updateEducation(educationLocation, educationId);
    }
}
