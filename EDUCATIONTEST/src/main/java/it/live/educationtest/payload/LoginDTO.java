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
public class LoginDTO {
    @NotNull(message = "Telefon raqami kiriting")
    private String phoneNumber;
    @NotNull(message = "Parolni kiriting")
    private String password;
}
