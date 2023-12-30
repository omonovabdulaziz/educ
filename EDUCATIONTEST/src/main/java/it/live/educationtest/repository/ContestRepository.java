package it.live.educationtest.repository;

import it.live.educationtest.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepository extends JpaRepository<Contest , Long> {
    Boolean existsByTitleAndGroupId(String title, Long group_id);
}
