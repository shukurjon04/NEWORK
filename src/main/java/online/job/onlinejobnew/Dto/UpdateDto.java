package online.job.onlinejobnew.Dto;

import jakarta.validation.constraints.NotBlank;
import online.job.onlinejobnew.Dto.Register.EmployeeDto;


import java.util.UUID;

public class UpdateDto extends EmployeeDto {
    @NotBlank
    private UUID userId;

    public UpdateDto(String firstName, String lastName, String email, String password, String phone, String username, UUID userId) {
        super(firstName, lastName, email, password, phone, username);
        this.userId = userId;
    }

    public @NotBlank UUID getUserId() {
        return userId;
    }

    public void setUserId(@NotBlank UUID userId) {
        this.userId = userId;
    }
}
