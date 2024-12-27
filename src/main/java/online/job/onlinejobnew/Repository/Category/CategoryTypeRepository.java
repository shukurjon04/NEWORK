package online.job.onlinejobnew.Repository.Category;

import online.job.onlinejobnew.Entity.Category.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryTypeRepository extends JpaRepository<CategoryType,Long> {

    Optional<CategoryType> findByName(String name);
}
