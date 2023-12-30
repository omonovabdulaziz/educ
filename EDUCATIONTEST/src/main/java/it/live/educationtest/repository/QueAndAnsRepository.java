package it.live.educationtest.repository;

import it.live.educationtest.entity.QueAnsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface QueAndAnsRepository extends JpaRepository<QueAnsData, UUID> {
    List<QueAnsData> findAllByUserIdAndQuestion_ContestId(Long user_id, Long question_contest_id);

    Boolean existsByUserIdAndQuestionId(Long user_id, UUID question_id);

    @Query(nativeQuery = true, value = "SELECT qad.* \n" +
            "FROM que_ans_data qad \n" +
            "         JOIN question q ON qad.question_id = q.id \n" +
            "WHERE qad.user_id =:userId AND q.contest_id =:contestId \n" +
            "ORDER BY qad.created_at DESC; \n")
    List<QueAnsData> findAllByUserIdAndQuestion_ContestIdOrderByCreatedAtDesc(@Param("userId") Long userId, @Param("contestId") Long contestId);
}
