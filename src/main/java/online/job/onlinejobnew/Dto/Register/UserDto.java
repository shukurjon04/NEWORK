package online.job.onlinejobnew.Dto.Register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import online.job.onlinejobnew.Aop.UniqueUsername;

public class UserDto {

    @NotBlank
    private String firstName;
    private String lastName;
    @UniqueUsername
    @NotBlank
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\\\d)(?=.*[@!#$%^&*()])[A-Za-z\\\\d@!#$%^&*()]{6,20}$",
            message = "siz zaif parol kiritdingiz "
    )
    private String password;
    @UniqueUsername
    @NotBlank
    @Pattern(
            regexp = "^(\\+\\d{1,3}[- ]?)?(\\(?\\d{1,4}\\)?[- ]?)?\\d{1,4}([- ]?\\d{1,4}){1,3}$",
            message = "Telefon raqami noto‘g‘ri formatda"
    )
    private String phone;
    @UniqueUsername
    @NotBlank
    private String username;

    public UserDto(String firstName, String lastName, String email, String password, String phone, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.username = username;
    }

    public @NotBlank String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\\\d)(?=.*[@!#$%^&*()])[A-Za-z\\\\d@!#$%^&*()]{6,20}$",
            message = "siz zaif parol kiritdingiz "
    ) String getPassword() {
        return password;
    }

    public void setPassword(@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\\\d)(?=.*[@!#$%^&*()])[A-Za-z\\\\d@!#$%^&*()]{6,20}$",
            message = "siz zaif parol kiritdingiz "
    ) String password) {
        this.password = password;
    }

    public @NotBlank @Pattern(
            regexp = "^(\\+\\d{1,3}[- ]?)?(\\(?\\d{1,4}\\)?[- ]?)?\\d{1,4}([- ]?\\d{1,4}){1,3}$",
            message = "Telefon raqami noto‘g‘ri formatda"
    ) String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank @Pattern(
            regexp = "^(\\+\\d{1,3}[- ]?)?(\\(?\\d{1,4}\\)?[- ]?)?\\d{1,4}([- ]?\\d{1,4}){1,3}$",
            message = "Telefon raqami noto‘g‘ri formatda"
    ) String phone) {
        this.phone = phone;
    }

    public @NotBlank String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}