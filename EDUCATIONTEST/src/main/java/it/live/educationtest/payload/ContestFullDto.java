package it.live.educationtest.payload;

import it.live.educationtest.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContestFullDto {
    private Long id;
    private String title;
    private Integer timeExpired;
    private Long groupId;
    private String secretKey;
    private Boolean isValid;
}
