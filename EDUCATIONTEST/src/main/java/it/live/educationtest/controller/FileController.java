package it.live.educationtest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.live.educationtest.entity.Question;
import it.live.educationtest.exception.MainException;
import it.live.educationtest.exception.NotFoundException;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.repository.QuestionRepository;
import it.live.educationtest.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
@Tag(name = "File", description = "Savollarni excel qilib yuklash uchun kerak")
public class FileController {
    private final FileService fileService;
    private final QuestionRepository questionRepository;

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN' , 'ROLE_ADMIN' , 'ROLE_TEACHER')")
    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> uploadFile(@RequestParam(required = false) MultipartFile excel, @RequestParam Long contestId) throws IOException {
        return fileService.uploadFile(excel, contestId);
    }

    @GetMapping("/viewOneFile")
    public ResponseEntity<?> viewOneFile(@RequestParam UUID questionId) throws MalformedURLException {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException("Topilmadi"));
        if (question.getPathUrl() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName=\"" + URLEncoder.encode(question.getPathUrl(), StandardCharsets.UTF_8)).
                    body(new FileUrlResource(String.format(question.getPathUrl())));
        }
        throw new MainException("Ushbu questionning rasmi mavjud emas");
    }
}
