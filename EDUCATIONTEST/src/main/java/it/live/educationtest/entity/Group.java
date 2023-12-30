package it.live.educationtest.entity;

import it.live.educationtest.entity.temp.AbsLongEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "groups")
public class Group extends AbsLongEntity {
    @Column(nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Status status;
    @Column(nullable = false)
    private Boolean isValid;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User teacher;
}
