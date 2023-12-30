package it.live.educationtest.entity;

import ch.qos.logback.core.util.ContentTypeUtil;
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
public class Question extends AbsUUIDEntity {
    private String pathUrl;
    private String contentType;
    @Column(nullable = false)
    private String description;
    @JoinColumn(nullable = false)
    @ManyToOne
    private Contest contest;
}
