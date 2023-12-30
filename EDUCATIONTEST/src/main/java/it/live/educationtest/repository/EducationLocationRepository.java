package it.live.educationtest.repository;

import it.live.educationtest.entity.EducationLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EducationLocationRepository extends JpaRepository<EducationLocation, Long> {
    Boolean existsByName(String name);

}
