package it.live.educationtest.repository;

import it.live.educationtest.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Boolean existsByEducationLocationIdAndId(Long educationLocation_id, Long id);

    List<Status> findAllByEducationLocationId(Long educationLocation_id);
}
