package challenge18.hotdeal.repository;

import challenge18.hotdeal.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
