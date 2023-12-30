package it.live.educationtest.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.live.educationtest.entity.Contest;
import it.live.educationtest.entity.User;
import it.live.educationtest.entity.enums.SystemRoleName;
import it.live.educationtest.exception.MainException;
import it.live.educationtest.payload.AnswerDTO;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.QuestionDTO;
import it.live.educationtest.service.AnswerService;
import it.live.educationtest.service.FileService;
import it.live.educationtest.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final QuestionService questionService;
    private final AnswerService answerService;
    @Value("${fast.url}")
    private String fastUrl;

    @Override
    public ResponseEntity<ApiResponse> uploadFile(MultipartFile excel, Long contestId) throws IOException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new org.springframework.core.io.ByteArrayResource(excel.getBytes()) {
            @Override
            public String getFilename() {
                return excel.getOriginalFilename();
            }
        });

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        RequestCallback requestCallback = restTemplate.httpEntityCallback(body);
        ResponseExtractor<ResponseEntity<String>> responseExtractor = restTemplate.responseEntityExtractor(String.class);

        String fastApiUrl = fastUrl + "readWord";
        ResponseEntity<String> responseEntity = restTemplate.execute(fastApiUrl, HttpMethod.POST, requestCallback, responseExtractor, body);
        String jsonString = responseEntity.getBody();
        try {
            List<Map<String, Object>> resultList = objectMapper.readValue(jsonString, new TypeReference<List<Map<String, Object>>>() {
            });

            for (Map<String, Object> question : resultList) {
                String questionText = Objects.requireNonNull(question.get("question")).toString();

                UUID questionId = (UUID) Objects.requireNonNull(questionService.addQuestion(
                        QuestionDTO.builder()
                                .questionValue(questionText)
                                .contestId(contestId)
                                .build(), null
                ).getBody()).getObject();

                List<AnswerDTO> answerDTOList = new ArrayList<>();
                Map<String, Object> answersMap = (Map<String, Object>) Objects.requireNonNull(question.get("answers"));
                for (Map.Entry<String, Object> entry : answersMap.entrySet()) {
                    String answerText = entry.getValue().toString();
                    boolean isCorrect = entry.getKey().equalsIgnoreCase("answer4");

                    AnswerDTO answerDTO = AnswerDTO.builder()
                            .questionId(questionId)
                            .description(answerText)
                            .isTrue(isCorrect)
                            .build();

                    answerDTOList.add(answerDTO);
                }

                answerService.addAnswer(answerDTOList);
            }
            return ResponseEntity.ok(ApiResponse.builder().message("Ushbu fayldagi testlar qo'shildi").status(201).build());
        } catch (Exception e) {
            throw new MainException("Notog'ri ko'rinishdagi excel fayl tashlandi");
        }
    }

    public void checkRoleUser(User systemUser, Contest contest) {
        if (systemUser.getSystemRoleName() == SystemRoleName.ROLE_TEACHER)
            if (!Objects.equals(systemUser.getId(), contest.getGroup().getTeacher().getId()))
                throw new MainException("Sizga ushbu guruhga contest qo'shishga qo'shishga ruxsat yoq");
        if (systemUser.getSystemRoleName() == SystemRoleName.ROLE_ADMIN)
            if (!Objects.equals(systemUser.getEducationLocation().getId(), contest.getGroup().getStatus().getEducationLocation().getId()))
                throw new MainException("Ushbu  guruh sizning educationizga tegishli emas");
    }
}
