package it.live.educationtest.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContestDTO {
    private Long id;
    @NotNull(message = "Title kiritish zarur")
    private String title;
    @NotNull(message = "TimeExpired kiritish zarur")
    private Integer timeExpired;
    @NotNull(message = "GroupId kiritish zarur")
    private Long groupId;
    @NotNull(message = "secretKey kiritish zarur")
    private String secretKey;
    @NotNull(message = "isValid kiritish zarur")
    private Boolean isValid;
}
