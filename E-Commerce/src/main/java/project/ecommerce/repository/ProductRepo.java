package project.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.ecommerce.model.Product;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Integer> {
    @Query("SELECT product FROM Product product WHERE " +
            "(product.title LIKE CONCAT('%', :filterValue, '%') " +
            "OR product.description LIKE CONCAT('%', :filterValue, '%'))")
    List<Product> filterPostByTitleAndDescription(String filterValue);

}
