package it.live.educationtest.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueAnsDataDTO {
    private UUID questionId;
    private UUID answerId;
    private Long userId;
}
