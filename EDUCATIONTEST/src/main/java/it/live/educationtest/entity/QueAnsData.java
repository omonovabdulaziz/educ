package it.live.educationtest.entity;

import it.live.educationtest.entity.temp.AbsUUIDEntity;
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
public class QueAnsData extends AbsUUIDEntity {
    @JoinColumn(nullable = false)
    @ManyToOne
    private Question question;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Answer answer;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
}
