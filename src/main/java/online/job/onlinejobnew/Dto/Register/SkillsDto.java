package online.job.onlinejobnew.Dto.Register;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public class SkillsDto {
    @NotBlank
    private UUID id;
    @NotBlank
    private List<String> skills;

    public SkillsDto(UUID id, List<String> skills) {
        this.id = id;
        this.skills = skills;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
