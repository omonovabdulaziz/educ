package it.live.educationtest.service;

import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.QueAnsDataDTO;
import it.live.educationtest.payload.ResponseQueAnsData;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QueAndAnsService {
    ResponseEntity<ApiResponse> addQueAnsData(QueAnsDataDTO queAnsDataDTO);

    List<ResponseQueAnsData> getQueAnsData(Long userId , Long contestId);

}
