package it.live.educationtest.repository;

import it.live.educationtest.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> findAllByContestId(Long contest_id);
}
