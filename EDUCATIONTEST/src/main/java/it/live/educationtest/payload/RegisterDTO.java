package it.live.educationtest.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    @NotNull(message = "Ism kiritilishi kerak")
    private String name;
    @NotNull(message = "Familiya kiritilishi kerak")
    private String surname;
    @NotNull(message = "Parol kiritilishi kerak")
    private String password;
    @NotNull(message = "Telefon raqam kiritilishi kerak")
    private String phoneNumber;
    @NotNull(message = "Gruppa tanlanishi kerak")
    private Long groupId;
    @NotNull(message = "Status tanlanishi kerak")
    private Long statusId;
    @NotNull(message = "Education tanlanishi kerak")
    private Long educationId;
}