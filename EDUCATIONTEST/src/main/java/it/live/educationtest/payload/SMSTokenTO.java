package it.live.educationtest.payload;

import lombok.Builder;
import lombok.Data;;

@Data
@Builder
public class SMSTokenTO {
    private String message;
    private SMSTokenDataTO data;
}