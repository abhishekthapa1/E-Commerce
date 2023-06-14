package project.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ecommerce.model.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
