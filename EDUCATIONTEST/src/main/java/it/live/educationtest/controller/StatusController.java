package it.live.educationtest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.live.educationtest.entity.Status;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.StatusDTO;
import it.live.educationtest.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/status")
@RequiredArgsConstructor
@Tag(name = "status")
public class StatusController {
    private final StatusService statusService;

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN' , 'ROLE_ADMIN')")
    @PostMapping("/addStatus")
    public ResponseEntity<ApiResponse> addStatus(@RequestBody StatusDTO statusDTO) {
        return statusService.addStatus(statusDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN' , 'ROLE_ADMIN')")
    @PutMapping("/updateStatus/{statusId}")
    public ResponseEntity<ApiResponse> updateStatus(@RequestBody StatusDTO statusDTO, @PathVariable Long statusId) {
        return statusService.updateStatus(statusId, statusDTO);
    }


    @GetMapping("/getAllStatus")
    public List<Status> getAllStatus(@RequestParam(required = false) Long educationId) {
        return statusService.getAllStatusByEducationId(educationId);
    }

}

