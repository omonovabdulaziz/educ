package it.live.educationtest.entity;

import it.live.educationtest.entity.temp.AbsLongEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class FinishedProccess extends AbsLongEntity {
    @JoinColumn(nullable = false)
    @ManyToOne
    private User user;
    @JoinColumn(nullable = false)
    @ManyToOne
    private Contest contest;
}
