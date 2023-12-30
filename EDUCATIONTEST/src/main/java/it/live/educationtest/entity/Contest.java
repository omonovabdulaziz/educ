package it.live.educationtest.entity;

import it.live.educationtest.entity.temp.AbsLongEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(callSuper = true)
public class Contest extends AbsLongEntity {
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Integer timeExpired;
    @JoinColumn(nullable = false)
    @ManyToOne
    private Group group;
    @Column(nullable = false)
    private String secretKey;
    @Column(nullable = false)
    private Boolean isValid;
}
