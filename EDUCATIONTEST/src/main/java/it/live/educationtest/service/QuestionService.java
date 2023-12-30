package it.live.educationtest.service;

import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.QuestionDTO;
import it.live.educationtest.payload.QuestionResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface QuestionService {
    ResponseEntity<ApiResponse> addQuestion(QuestionDTO questionDTO , MultipartFile file);

    List<QuestionDTO> getQuestionByContest(Long contestId);

    ResponseEntity<ApiResponse> updateQuestion(QuestionDTO questionDTO , UUID questionId , MultipartFile file);

    QuestionResponseDTO getQuestionById(UUID questionId);
}
