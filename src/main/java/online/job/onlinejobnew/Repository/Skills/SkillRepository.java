package online.job.onlinejobnew.Repository.Skills;

import online.job.onlinejobnew.Entity.Ability.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SkillRepository extends JpaRepository<Skills, UUID> {
}
