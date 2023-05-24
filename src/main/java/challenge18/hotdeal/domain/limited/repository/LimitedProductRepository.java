package challenge18.hotdeal.domain.limited.repository;

import challenge18.hotdeal.domain.limited.entity.LimitedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LimitedProductRepository extends JpaRepository<LimitedProduct, Long> {
}
