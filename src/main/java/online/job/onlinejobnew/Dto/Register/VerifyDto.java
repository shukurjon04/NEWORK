package online.job.onlinejobnew.Dto.Register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class VerifyDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String code;

    public VerifyDto(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public VerifyDto() {
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public @NotBlank String getCode() {
        return code;
    }

    public void setCode(@NotBlank String code) {
        this.code = code;
    }
}
