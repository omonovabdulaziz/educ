package it.live.educationtest.payload;

import it.live.educationtest.entity.Group;
import it.live.educationtest.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupResponseDTO {
    private Long id;
    private String name;
    private Boolean isValid;
    private Status status;
}
