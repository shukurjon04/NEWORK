package online.job.onlinejobnew.Entity.Ability;

import jakarta.persistence.*;
import online.job.onlinejobnew.Entity.Category.CategoryType;

import java.math.BigInteger;
import java.util.UUID;

@Entity
public class Skills {
    @Id
    @GeneratedValue
    private BigInteger id;
    @Column(nullable = false)
    private String name;

    public Skills(BigInteger id, String name) {
        this.id = id;
        this.name = name;
    }

    public Skills() {
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
