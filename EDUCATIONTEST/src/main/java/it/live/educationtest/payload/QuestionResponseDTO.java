package it.live.educationtest.payload;


import it.live.educationtest.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionResponseDTO {
    private String questionValue;
    private Long contestId;
    private List<Answer> answers;
    private String pathUrl;
}
