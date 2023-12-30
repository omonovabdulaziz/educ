package it.live.educationtest.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDTO {
    @Null(message = "qoshishda kerak emas")
    private UUID id;
    @NotNull(message = "Question Value kiritlishi kerak")
    private String questionValue;
    @NotNull(message = "Contest kiriting")
    private Long contestId;
}
