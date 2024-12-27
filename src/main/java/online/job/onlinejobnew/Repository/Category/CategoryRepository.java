package online.job.onlinejobnew.Repository.Category;

import online.job.onlinejobnew.Entity.Category.Enums.CategoryName;
import online.job.onlinejobnew.Entity.Category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findByName(CategoryName name);
}
