package online.job.onlinejobnew.Repository.users;

import online.job.onlinejobnew.Entity.Users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {

    boolean existsByUsername(String username);

    Optional<Admin> findByUsername(String username);

    boolean existsByEmail(String email);

    Optional<Admin> findByEmail(String email);
}
