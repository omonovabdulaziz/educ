package it.live.educationtest.service;

import it.live.educationtest.entity.Contest;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.ContestDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface ContestService {
    ResponseEntity<ApiResponse> addContest(ContestDTO contestDTO);

    ResponseEntity<ApiResponse> updateContest(Long contestId, ContestDTO contestDTO);

    Page<ContestDTO> getAllContextByGroupId(Long groupId , int page , int size);

    ContestDTO getContextById(Long contextId);

}
