package it.live.educationtest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.live.educationtest.entity.temp.AbsUUIDEntity;
import jakarta.persistence.Column;
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
public class Answer extends AbsUUIDEntity {
    private String pathUrlImage;
    private String contentType;
    @JoinColumn(nullable = false)
    @ManyToOne
    @JsonIgnore
    private Question question;
    @Column(nullable = false)
    private String description;
    private Boolean isTrue;
}
