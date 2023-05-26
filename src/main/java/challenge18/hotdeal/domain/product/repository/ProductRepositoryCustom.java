package challenge18.hotdeal.domain.product.repository;

import challenge18.hotdeal.domain.product.dto.ConditionDto;
import challenge18.hotdeal.domain.product.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findAllByPriceAndCategory(ConditionDto condition);
}
