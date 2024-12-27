package online.job.onlinejobnew.Repository.users;

import online.job.onlinejobnew.Entity.Users.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    boolean existsByUsername(String username);

    Optional<Employee> findByUsername(String username);

    boolean existsByEmail(String email);

    Optional<Employee> findByEmail(String email);
}
