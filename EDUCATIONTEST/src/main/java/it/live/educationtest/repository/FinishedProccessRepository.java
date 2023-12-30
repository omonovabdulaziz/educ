package it.live.educationtest.repository;

import it.live.educationtest.entity.FinishedProccess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinishedProccessRepository extends JpaRepository<FinishedProccess , Long> {
    Boolean existsByContestIdAndUserId(Long contest_id, Long user_id);
}
