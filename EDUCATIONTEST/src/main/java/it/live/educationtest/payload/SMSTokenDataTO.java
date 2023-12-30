package it.live.educationtest.payload;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class SMSTokenDataTO {
    private String token;
}