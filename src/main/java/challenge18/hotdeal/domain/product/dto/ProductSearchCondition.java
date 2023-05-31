package challenge18.hotdeal.domain.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductSearchCondition {
    private Long minPrice;
    private Long maxPrice;
    private String mainCategory;
    private String subCategory;

    public ProductSearchCondition(Long minPrice, Long maxPrice, String mainCategory, String subCategory) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
    }

    public void setCondition(ProductSearchCondition condition){
        this.minPrice = condition.getMinPrice();
        this.maxPrice = condition.getMaxPrice();
        this.mainCategory = condition.getMainCategory();
        this.subCategory = condition.getSubCategory();
    }
}
