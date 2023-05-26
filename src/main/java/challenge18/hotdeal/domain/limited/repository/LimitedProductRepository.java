package challenge18.hotdeal.domain.limited.repository;

import challenge18.hotdeal.domain.limited.entity.LimitedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LimitedProductRepository extends JpaRepository<LimitedProduct, Long> {

    @Query(value = "select * from limited_products where id = :id for update",nativeQuery = true)
    Optional<LimitedProduct> findWithId(@Param("id") Long id);
}
