package it.live.educationtest.service;

import it.live.educationtest.payload.AnswerDTO;
import it.live.educationtest.payload.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface AnswerService {
    ResponseEntity<ApiResponse> addAnswer(List<AnswerDTO> answerDTOS);

    ResponseEntity<ApiResponse> updateAnswer(AnswerDTO answerDTO, UUID answerId);
}
