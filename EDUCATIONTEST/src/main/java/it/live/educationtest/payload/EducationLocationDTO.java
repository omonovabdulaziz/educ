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
public class EducationLocationDTO {
    @NotNull(message = "Education uchun nom kiritilishi kerak")
    private String name;
    private Boolean IsValid;
}
