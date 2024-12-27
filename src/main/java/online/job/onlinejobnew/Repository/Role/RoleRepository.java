package online.job.onlinejobnew.Repository.Role;

import online.job.onlinejobnew.Entity.RoleEntiy.Role;
import online.job.onlinejobnew.Entity.RoleEntiy.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleName(RoleName roleName);
}
