package online.job.onlinejobnew.Dto.Register;

import jakarta.validation.constraints.NotBlank;
import online.job.onlinejobnew.Entity.Category.Enums.CategoryName;

public class EmployeeDto extends UserDto {

    @NotBlank
    private CategoryName categoryName;

    public EmployeeDto(String firstName, String lastName, String email, String password, String phone, String username) {
        super(firstName, lastName, email, password, phone, username);
    }

    public @NotBlank CategoryName getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(@NotBlank CategoryName categoryType) {
        this.categoryName = categoryType;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "categoryName=" + categoryName +
                '}';
    }
}
