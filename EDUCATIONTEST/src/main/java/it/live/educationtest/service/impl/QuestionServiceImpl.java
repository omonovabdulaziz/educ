package it.live.educationtest.service.impl;

import it.live.educationtest.config.SecurityConfiguration;
import it.live.educationtest.entity.Contest;
import it.live.educationtest.entity.QueAnsData;
import it.live.educationtest.entity.Question;
import it.live.educationtest.entity.User;
import it.live.educationtest.entity.enums.SystemRoleName;
import it.live.educationtest.exception.MainException;
import it.live.educationtest.exception.NotFoundException;
import it.live.educationtest.mapper.QuestionMapper;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.QuestionDTO;
import it.live.educationtest.payload.QuestionResponseDTO;
import it.live.educationtest.repository.AnswerRepository;
import it.live.educationtest.repository.ContestRepository;
import it.live.educationtest.repository.QueAndAnsRepository;
import it.live.educationtest.repository.QuestionRepository;
import it.live.educationtest.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final ContestRepository contestRepository;
    private final QuestionMapper questionMapper;
    private final AnswerRepository answerRepository;
    private final QueAndAnsRepository queAndAnsRepository;

    @Override
    public ResponseEntity<ApiResponse> addQuestion(QuestionDTO questionDTO, MultipartFile file) {
        Contest contest = contestRepository.findById(questionDTO.getContestId())
                .orElseThrow(() -> new NotFoundException("Contest not found"));
        User systemUser = SecurityConfiguration.getOwnSecurityInformation();
        checkRoleUser(systemUser, contest);
        Question question = questionMapper.toEntity(questionDTO, contest);
        fileMethod(file, question);
        Question savedQuestion = questionRepository.save(question);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Question muvaffaqiytli qo'shildi")
                .object(savedQuestion.getId())
                .status(201)
                .build());
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1);
    }


    @Override
    public List<QuestionDTO> getQuestionByContest(Long contestId) {
        User systemUser = SecurityConfiguration.getOwnSecurityInformation();

        List<Question> allQuestions = questionRepository.findAllByContestId(contestId);

        if (systemUser.getSystemRoleName() != SystemRoleName.ROLE_USER) {
            return allQuestions.stream()
                    .map(questionMapper::toDto)
                    .collect(Collectors.toList());
        }

        List<QueAnsData> list = queAndAnsRepository
                .findAllByUserIdAndQuestion_ContestIdOrderByCreatedAtDesc(systemUser.getId(), contestId);

        return allQuestions.stream()
                .filter(question ->
                        list.stream()
                                .noneMatch(queAnsData ->
                                        queAnsData.getId() != null && queAnsData.getQuestion().getId().equals(question.getId())))
                .map(questionMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public ResponseEntity<ApiResponse> updateQuestion(QuestionDTO questionDTO, UUID questionId, MultipartFile file) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException("Bunday question topilmadi"));
        Contest contest = contestRepository.findById(questionDTO.getContestId()).orElseThrow(() -> new NotFoundException("Bunday Contest topilmadi"));
        User systemUser = SecurityConfiguration.getOwnSecurityInformation();
        checkRoleUser(systemUser, contest);
        fileMethod(file, question);
        questionRepository.save(questionMapper.toEntity(questionDTO, contest, question));
        return ResponseEntity.ok(ApiResponse.builder().message("Question yangilandi").status(200).build());
    }

    private void fileMethod(MultipartFile file, Question question) {
        if (file != null && !file.isEmpty()) {
            try {
                question.setContentType(file.getContentType());
                String uniqueFileName = UUID.randomUUID() + "." + getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
                question.setPathUrl("documents" + File.separator + uniqueFileName);
                Path filePath = Paths.get(System.getProperty("user.dir"), "documents", uniqueFileName);
                Path directoryPath = filePath.getParent();
                if (Files.notExists(directoryPath)) {
                    Files.createDirectories(directoryPath);
                }
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                throw new MainException("Error uploading file: " + e.getMessage());
            }
        }
    }

    @Override
    public QuestionResponseDTO getQuestionById(UUID questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException("Bunday question topilmadi"));
        return questionMapper.toDTO(question, answerRepository.findAllByQuestionId(questionId));
    }

    public void checkRoleUser(User systemUser, Contest contest) {
        if (systemUser.getSystemRoleName() == SystemRoleName.ROLE_TEACHER)
            if (!Objects.equals(systemUser.getId(), contest.getGroup().getTeacher().getId()))
                throw new MainException("Ushbu contest qo'shilyotgan guruh sizga tegishli emas");
        if (systemUser.getSystemRoleName() == SystemRoleName.ROLE_ADMIN)
            if (!Objects.equals(systemUser.getEducationLocation().getId(), contest.getGroup().getStatus().getEducationLocation().getId()))
                throw new MainException("Ushbu contest qo'shilyotgan guruh sizga tegishli emas");
    }
}
