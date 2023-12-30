package it.live.educationtest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.live.educationtest.payload.AnswerDTO;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/answer")
@RequiredArgsConstructor
@Tag(name = "Answer")
public class AnswerController {
    private final AnswerService answerService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_SUPER_ADMIN' , 'ROLE_TEACHER')")
    @PostMapping("/addAnswer")
    public ResponseEntity<ApiResponse> addAnswer(@RequestBody List<AnswerDTO> answerDTO) {
        return answerService.addAnswer(answerDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN' , 'ROLE_SUPER_ADMIN' , 'ROLE_TEACHER')")
    @PutMapping("/updateAnswer")
    public ResponseEntity<ApiResponse> updateAnswer(@RequestBody AnswerDTO answerDTO, @RequestParam UUID answerId) {
        return answerService.updateAnswer(answerDTO, answerId);
    }

}
