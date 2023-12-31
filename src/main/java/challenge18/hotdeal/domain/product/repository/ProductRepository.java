package challenge18.hotdeal.domain.product.repository;

import challenge18.hotdeal.domain.product.entity.Product;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import static challenge18.hotdeal.common.config.Redis.RedisCacheKey.PRODUCT;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    @Cacheable(value=PRODUCT, cacheManager = "redisCacheManager")
    Optional<Product> findById(Long productId);
}
