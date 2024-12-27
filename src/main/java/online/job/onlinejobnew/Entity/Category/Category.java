package online.job.onlinejobnew.Entity.Category;

import jakarta.persistence.*;
import online.job.onlinejobnew.Entity.Category.Enums.CategoryName;

import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryName name;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<CategoryType> categoryType;

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<CategoryType> getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Set<CategoryType> categoryType) {
        this.categoryType = categoryType;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CategoryName getName() {
        return name;
    }

    public void setName(CategoryName name) {
        this.name = name;
    }
}
