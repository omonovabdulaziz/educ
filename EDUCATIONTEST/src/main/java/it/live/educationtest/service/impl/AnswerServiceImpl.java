package it.live.educationtest.service.impl;

import it.live.educationtest.config.SecurityConfiguration;
import it.live.educationtest.entity.Answer;
import it.live.educationtest.entity.Question;
import it.live.educationtest.entity.User;
import it.live.educationtest.entity.enums.SystemRoleName;
import it.live.educationtest.exception.MainException;
import it.live.educationtest.exception.NotFoundException;
import it.live.educationtest.mapper.AnswerMapper;
import it.live.educationtest.payload.AnswerDTO;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.repository.AnswerRepository;
import it.live.educationtest.repository.QuestionRepository;
import it.live.educationtest.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerMapper answerMapper;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Override
    public ResponseEntity<ApiResponse> addAnswer(List<AnswerDTO> answerDTOs) {
        User systemUser = SecurityConfiguration.getOwnSecurityInformation();
        for (AnswerDTO answerDTO : answerDTOs) {
            checkRoleUser(systemUser, answerDTO);
            answerRepository.save(answerMapper.toEntity(answerDTO));
        }
        return ResponseEntity.ok(ApiResponse.builder().message("Answer lar qo'shildi").status(201).build());
    }

    @Override
    public ResponseEntity<ApiResponse> updateAnswer(AnswerDTO answerDTO, UUID answerId) {
        User systemUser = SecurityConfiguration.getOwnSecurityInformation();
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new NotFoundException("Bunday answer topilmadi"));
        checkRoleUser(systemUser, answerDTO);
        answerRepository.save(answerMapper.toEntity(answerDTO, answer));
        return ResponseEntity.ok(ApiResponse.builder().message("Answer Yangilandi").status(200).build());
    }

    private void checkRoleUser(User systemUser, AnswerDTO answerDTO) {
        Question question = questionRepository.findById(answerDTO.getQuestionId()).orElseThrow(() -> new NotFoundException("Bunday question topilmadi"));
        if (systemUser.getSystemRoleName() == SystemRoleName.ROLE_TEACHER)
            if (!Objects.equals(question.getContest().getGroup().getTeacher().getId(), systemUser.getId()))
                throw new MainException("Sizni ushbu guruhga savoliga javob qo'sha olmaysiz");
        if (systemUser.getSystemRoleName() == SystemRoleName.ROLE_ADMIN)
            if (!Objects.equals(systemUser.getEducationLocation().getId(), question.getContest().getGroup().getStatus().getEducationLocation().getId()))
                throw new MainException("Sizni ushbu guruhga savoliga javob qo'sha olmaysiz");
    }
}
