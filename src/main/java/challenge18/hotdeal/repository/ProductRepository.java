package challenge18.hotdeal.repository;

import challenge18.hotdeal.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Long> {
}
