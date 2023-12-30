package it.live.educationtest.payload;

import it.live.educationtest.entity.enums.SystemRoleName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDTO {
    @NotNull(message = "ismi bo'lishi kerak")
    private String name;
    @NotNull(message = "familiya bo'lishi kerak")
    private String surname;
    @NotNull(message = "telefon raqam bo'lishi kerak")
    private String phoneNumber;
    @NotNull(message = "parol bo'lishi kerak")
    private String password;
    private SystemRoleName systemRoleName;
    private Long educationId;
    private Long statusId;
}
