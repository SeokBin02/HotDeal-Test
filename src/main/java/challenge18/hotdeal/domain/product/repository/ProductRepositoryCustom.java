package challenge18.hotdeal.domain.product.repository;

import challenge18.hotdeal.domain.product.dto.ConditionDto;
import challenge18.hotdeal.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findAllByPriceAndCategory(ConditionDto condition);

    List<Product> findPorpularTop90();

    List<Product> findByKeyword(String keyword);
}
