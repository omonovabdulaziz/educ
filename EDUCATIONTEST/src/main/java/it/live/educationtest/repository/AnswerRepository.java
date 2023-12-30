package it.live.educationtest.repository;

import it.live.educationtest.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AnswerRepository extends JpaRepository<Answer, UUID> {
    List<Answer> findAllByQuestionId(UUID question_id);
    Boolean existsByQuestionIdAndId(UUID question_id, UUID id);
}
