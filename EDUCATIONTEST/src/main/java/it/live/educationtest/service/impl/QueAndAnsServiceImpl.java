package it.live.educationtest.service.impl;

import it.live.educationtest.config.SecurityConfiguration;
import it.live.educationtest.entity.FinishedProccess;
import it.live.educationtest.entity.Question;
import it.live.educationtest.entity.User;
import it.live.educationtest.exception.AnswerFalseException;
import it.live.educationtest.exception.MainException;
import it.live.educationtest.exception.NotFoundException;
import it.live.educationtest.mapper.QueAnsMapper;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.QueAnsDataDTO;
import it.live.educationtest.payload.ResponseQueAnsData;
import it.live.educationtest.repository.*;
import it.live.educationtest.service.QueAndAnsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class QueAndAnsServiceImpl implements QueAndAnsService {
    private final QueAndAnsRepository queAnsRepository;
    private final QueAnsMapper queAnsMapper;
    private final FinishedProccessRepository finishedProccessRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ApiResponse> addQueAnsData(QueAnsDataDTO queAnsDataDTO) {
        User systemUser = SecurityConfiguration.getOwnSecurityInformation();
        Question question = questionRepository.findById(queAnsDataDTO.getQuestionId()).orElseThrow(() -> new NotFoundException("Bunday savol mavjud emas"));
        if (finishedProccessRepository.existsByContestIdAndUserId(question.getContest().getId(), systemUser.getId()))
            throw new MainException("Siz ushbu contestda qatnashgansiz");
        if (!answerRepository.existsByQuestionIdAndId(queAnsDataDTO.getQuestionId(), queAnsDataDTO.getAnswerId()))
            throw new MainException("Ushbu javob ushbu savolga tegishli emas");
        if (queAnsRepository.existsByUserIdAndQuestionId(systemUser.getId(), question.getId()))
            throw new MainException("Siz ushbu savolga javob bergansiz");
        int sizeOfQuestions = questionRepository.findAllByContestId(question.getContest().getId()).size();
        int size = queAnsRepository.findAllByUserIdAndQuestion_ContestId(systemUser.getId(), question.getContest().getId()).size();
        if (sizeOfQuestions - size == 1) {
            finishedProccessRepository.save(FinishedProccess.builder().contest(question.getContest()).user(systemUser).build());
            return functionIdk(queAnsDataDTO, systemUser , true);
        }
        return functionIdk(queAnsDataDTO, systemUser ,false);
    }

    private ResponseEntity<ApiResponse> functionIdk(QueAnsDataDTO queAnsDataDTO, User systemUser , boolean istrue) {
        queAnsRepository.save(queAnsMapper.toEntity(queAnsDataDTO, systemUser.getId()));
        if (!answerRepository.findById(queAnsDataDTO.getAnswerId()).get().getIsTrue())
            return ResponseEntity.ok(ApiResponse.builder().message(istrue ? "Siz notog'ri jovob berdingiz va test yakunlandi" : "Siz notog'ri javob berdingiz").object(false).status(200).build());
        int i = systemUser.getBall() + 1;
        systemUser.setBall(i);
        userRepository.save(systemUser);
        return ResponseEntity.ok(ApiResponse.builder().message( istrue ? "Siz to'g'ri javob berdingiz , va test yakunlandi" : "Siz to'g'ri javob berdingiz").object(true).status(200).build());
    }

    @Override
    public List<ResponseQueAnsData> getQueAnsData(Long userId, Long contestId) {
        System.out.println(queAnsRepository.findAllByUserIdAndQuestion_ContestId(userId, contestId).stream().map(queAnsMapper::toDTO).collect(Collectors.toList()));
        return queAnsRepository.findAllByUserIdAndQuestion_ContestId(userId, contestId).stream().map(queAnsMapper::toDTO).collect(Collectors.toList());
    }
}
