package it.live.educationtest.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseQueAnsData {
    private String questionValue;
    private String answerValue;
    private Boolean isTrue;
}
