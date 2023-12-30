package it.live.educationtest.payload;

import it.live.educationtest.entity.EducationLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private Integer ball;
    private EducationLocation educationLocation;
}
