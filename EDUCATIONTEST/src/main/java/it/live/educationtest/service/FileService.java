package it.live.educationtest.service;

import it.live.educationtest.payload.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    ResponseEntity<ApiResponse> uploadFile( MultipartFile excel, Long contestId) throws IOException;

}
