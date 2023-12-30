package it.live.educationtest.mapper;

import it.live.educationtest.entity.Answer;
import it.live.educationtest.entity.Contest;
import it.live.educationtest.entity.Question;
import it.live.educationtest.payload.QuestionDTO;
import it.live.educationtest.payload.QuestionResponseDTO;
import it.live.educationtest.repository.ContestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionMapper {

    public Question toEntity(QuestionDTO questionDTO, Contest contest) {
        return Question.builder()
                .contest(contest)
                .description(questionDTO.getQuestionValue())
                .build();
    }

    public Question toEntity(QuestionDTO questionDTO, Contest contest, Question question) {
        question.setContest(contest);
        question.setDescription(questionDTO.getQuestionValue());
        return question;
    }

    public QuestionResponseDTO toDTO(Question question, List<Answer> answers) {
        return QuestionResponseDTO.builder()
                .pathUrl(question.getPathUrl())
                .questionValue(question.getDescription())
                .contestId(question.getContest().getId())
                .answers(answers).build();
    }

    public QuestionDTO toDto(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .questionValue(question.getDescription())
                .contestId(question.getContest().getId())
                .build();
    }
}
