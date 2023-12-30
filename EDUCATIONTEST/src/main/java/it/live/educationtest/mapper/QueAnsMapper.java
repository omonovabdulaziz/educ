package it.live.educationtest.mapper;

import it.live.educationtest.entity.QueAnsData;
import it.live.educationtest.exception.NotFoundException;
import it.live.educationtest.payload.QueAnsDataDTO;
import it.live.educationtest.payload.ResponseQueAnsData;
import it.live.educationtest.repository.AnswerRepository;
import it.live.educationtest.repository.QuestionRepository;
import it.live.educationtest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueAnsMapper {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public QueAnsData toEntity(QueAnsDataDTO queAnsDataDTO, Long id) {
        return QueAnsData.builder()
                .answer(answerRepository.findById(queAnsDataDTO.getAnswerId()).orElseThrow(() -> new NotFoundException("Bunday answer topilmadi")))
                .question(questionRepository.findById(queAnsDataDTO.getQuestionId()).orElseThrow(() -> new NotFoundException("Bunday question topilmadi")))
                .user(userRepository.findById(id).get()).build();
    }

    public ResponseQueAnsData toDTO(QueAnsData queAnsData) {
        return ResponseQueAnsData.builder()
                .questionValue(queAnsData.getQuestion().getDescription())
                .answerValue(queAnsData.getAnswer().getDescription())
                .isTrue(queAnsData.getAnswer().getIsTrue()).build();
    }
}
