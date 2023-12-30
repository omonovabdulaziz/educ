package it.live.educationtest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.live.educationtest.entity.Contest;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.ContestDTO;
import it.live.educationtest.service.ContestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contest")
@Tag(name = "contest")
@RequiredArgsConstructor
public class ContestController {
    private final ContestService contestService;


    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN' , 'ROLE_ADMIN' , 'ROLE_TEACHER')")
    @PostMapping("/addContest")
    public ResponseEntity<ApiResponse> addContest(@Valid @RequestBody ContestDTO contestDTO) {
        return contestService.addContest(contestDTO);
    }


    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN' , 'ROLE_ADMIN' , 'ROLE_TEACHER')")
    @PutMapping("/updateContest/{contestId}")
    public ResponseEntity<ApiResponse> updateContest(@PathVariable Long contestId, @RequestBody ContestDTO contestDTO) {
        return contestService.updateContest(contestId, contestDTO);
    }

    @GetMapping("/getAllContextByGroupId/{groupId}")
    public Page<ContestDTO> getAllContextByGroupId(@PathVariable Long groupId , @RequestParam int page , @RequestParam int size) {
        return contestService.getAllContextByGroupId(groupId , page , size);
    }

    @GetMapping("/getContextById/{contextId}")
    public ContestDTO getContext(@PathVariable Long contextId) {
        return contestService.getContextById(contextId);
    }
}
