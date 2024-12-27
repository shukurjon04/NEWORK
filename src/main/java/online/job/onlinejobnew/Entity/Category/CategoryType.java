package online.job.onlinejobnew.Entity.Category;

import jakarta.persistence.*;
import online.job.onlinejobnew.Entity.Ability.Skills;

import java.util.List;

@Entity
public class CategoryType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true , nullable=false)
    private String name;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Skills> skills;

    public CategoryType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryType(Long id, String name, List<Skills> skills) {
        this.id = id;
        this.name = name;
        this.skills = skills;
    }

    public List<Skills> getSkills() {
        return skills;
    }

    public void setSkills(List<Skills> skills) {
        this.skills = skills;
    }

    public CategoryType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
