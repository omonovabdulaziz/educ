package it.live.educationtest.service;

import it.live.educationtest.entity.Status;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.StatusDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface StatusService {
    ResponseEntity<ApiResponse> addStatus(StatusDTO statusDTO);


    ResponseEntity<ApiResponse> updateStatus(Long statusId, StatusDTO statusDTO);

    List<Status> getAllStatusByEducationId(Long educationId);

}
