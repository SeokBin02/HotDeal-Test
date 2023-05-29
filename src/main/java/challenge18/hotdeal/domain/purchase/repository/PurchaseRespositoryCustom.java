package challenge18.hotdeal.domain.purchase.repository;

import challenge18.hotdeal.domain.product.dto.AllProductResponseDto;
import challenge18.hotdeal.domain.product.dto.ProductSearchCondition;

import java.util.List;

public interface PurchaseRespositoryCustom {
    List<AllProductResponseDto> findTop90();
}
