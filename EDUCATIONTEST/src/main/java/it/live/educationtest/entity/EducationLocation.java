package it.live.educationtest.entity;

import it.live.educationtest.entity.temp.AbsLongEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class EducationLocation extends AbsLongEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private Boolean IsValid;
}
