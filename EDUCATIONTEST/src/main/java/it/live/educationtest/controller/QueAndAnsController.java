package it.live.educationtest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.QueAnsDataDTO;
import it.live.educationtest.payload.ResponseQueAnsData;
import it.live.educationtest.service.QueAndAnsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/queAndAns")
@Tag(name = "QueAndAns" , description = "Savollar hamda javoblar boyicha")
public class QueAndAnsController {
    private final QueAndAnsService queAndAnsService;


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/addQueAnsData")
    public ResponseEntity<ApiResponse> addQueAnsData(@RequestBody QueAnsDataDTO queAnsDataDTO) {
        return queAndAnsService.addQueAnsData(queAnsDataDTO);
    }

    @GetMapping("/getQueAnsDataByStudentId/{userId}")
    public List<ResponseQueAnsData> getQueAnsData(@PathVariable Long userId, @RequestParam Long contestId) {
        return queAndAnsService.getQueAnsData(userId , contestId);
    }

}
