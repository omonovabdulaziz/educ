package it.live.educationtest.repository;

import it.live.educationtest.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Boolean existsByNameAndStatusId(String name, Long status_id);

    Page<Group> findAllByStatusId(Long status_id, Pageable pageable);

    List<Group> findAllByTeacherId(Long teacher_id);
}
