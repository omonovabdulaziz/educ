package it.live.educationtest.entity;

import it.live.educationtest.entity.temp.AbsLongEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Status extends AbsLongEntity {
    @Column(nullable = false)
    private String name;
    @JoinColumn(nullable = false)
    @ManyToOne
    private EducationLocation educationLocation;
    @Column(nullable = false)
    private Boolean isValid;
}
