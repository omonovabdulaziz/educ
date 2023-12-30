package it.live.educationtest.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {
    @NotNull(message = "Guruhga nom kiritilishi kiritilishi kerak")
    private String name;
    @NotNull(message = "Guruhga status tanlanishi kerak")
    private Long statusId;
    @NotNull(message = "Guruhni yaroqliligini kiriting")
    private Boolean isValid;
    @NotNull(message = "Teacher id kiriting")
    private Long teacherId;
}
