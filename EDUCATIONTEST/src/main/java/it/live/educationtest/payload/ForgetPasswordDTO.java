package it.live.educationtest.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForgetPasswordDTO {
    private String phoneNumber;
    private String newPassword;
}