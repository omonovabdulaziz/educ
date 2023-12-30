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
public class StatusDTO {
    @NotNull(message = "Status nomi kiritilishi kerak")
    private String name;
    @NotNull(message = "Education tanlanishi kerak")
    private Long educationId;
    private Boolean isValid;
}
