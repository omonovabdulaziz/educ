package it.live.educationtest.mapper;

import it.live.educationtest.entity.Answer;
import it.live.educationtest.exception.NotFoundException;
import it.live.educationtest.payload.AnswerDTO;
import it.live.educationtest.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerMapper {
    private final QuestionRepository questionRepository;

    public Answer toEntity(AnswerDTO answerDTO) {
        return Answer.builder()
                .isTrue(answerDTO.getIsTrue())
                .description(answerDTO.getDescription())
                .question(questionRepository.findById(answerDTO.getQuestionId()).orElseThrow(() -> new NotFoundException("Bunday question topilmadi")))
                .build();
    }

    public Answer toEntity(AnswerDTO answerDTO, Answer answer) {
        answer.setDescription(answerDTO.getDescription());
        answer.setIsTrue(answerDTO.getIsTrue());
        answer.setQuestion(questionRepository.findById(answerDTO.getQuestionId()).orElseThrow(() -> new NotFoundException("Bunday question topilmadi")));
        return answer;
    }
}
