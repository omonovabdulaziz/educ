package it.live.educationtest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.QuestionDTO;
import it.live.educationtest.payload.QuestionResponseDTO;
import it.live.educationtest.service.QuestionService;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/question")
@Tag(name = "question")
public class QuestionController {
    private final QuestionService questionService;

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN' , 'ROLE_ADMIN' , 'ROLE_TEACHER')")
    @PostMapping(value = "/addQuestion")
    public ResponseEntity<ApiResponse> addQuestion(@RequestParam(name = "file", required = false) MultipartFile file,
                                                   @RequestParam(name = "questionValue") String questionValue,
                                                   @RequestParam(name = "contestId") Long contestId) {
        return questionService.addQuestion(QuestionDTO.builder().questionValue(questionValue).contestId(contestId).build(), file);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN' , 'ROLE_ADMIN' , 'ROLE_TEACHER')")
    @PutMapping("/updateQuestion")
    public ResponseEntity<ApiResponse> updateQuestion(@RequestParam UUID questionId,
                                                      @RequestParam(name = "file", required = false) MultipartFile file,
                                                      @RequestParam(name = "questionValue") String questionValue,
                                                      @RequestParam(name = "contestId") Long contestId) {
        return questionService.updateQuestion(QuestionDTO.builder().questionValue(questionValue).contestId(contestId).build(), questionId, file);
    }

    @GetMapping("/getAllQuestionByContest/{contestId}")
    public List<QuestionDTO> getQuestion(@PathVariable Long contestId) {
        return questionService.getQuestionByContest(contestId);
    }

    @GetMapping("/getQuestionById")
    public QuestionResponseDTO getQuestionById(@RequestParam UUID questionId) {
        return questionService.getQuestionById(questionId);
    }
}
