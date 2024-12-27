package online.job.onlinejobnew.Dto.Register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import online.job.onlinejobnew.Aop.UniqueUsername;

public class LoginDto {
    @NotBlank
    @UniqueUsername
    private String username;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\\\d)(?=.*[@!#$%^&*()])[A-Za-z\\\\d@!#$%^&*()]{6,20}$",
            message = "siz zaif parol kiritdingiz "
    )
    private String password;

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginDto() {
    }

    public @NotBlank String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public @NotBlank @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\\\d)(?=.*[@!#$%^&*()])[A-Za-z\\\\d@!#$%^&*()]{6,20}$",
            message = "siz zaif parol kiritdingiz "
    ) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\\\d)(?=.*[@!#$%^&*()])[A-Za-z\\\\d@!#$%^&*()]{6,20}$",
            message = "siz zaif parol kiritdingiz "
    ) String password) {
        this.password = password;
    }
}
