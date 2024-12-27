package online.job.onlinejobnew.Entity.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import online.job.onlinejobnew.Entity.Ability.Skills;
import online.job.onlinejobnew.Entity.Category.Category;
import online.job.onlinejobnew.Entity.Users.MainCloneUser.MainUser;

import java.util.List;
import java.util.Set;

@Entity
public class Employee extends MainUser {
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    public Employee(Category category) {
        this.category = category;
    }

    public Employee() {
    }
    
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
