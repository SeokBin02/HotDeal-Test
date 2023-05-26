package challenge18.hotdeal.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConditionDto {
    private Integer minPrice;
    private Integer maxPrice;
    private String mainCategory;
    private String subCategory;

    public ConditionDto(Integer minPrice, Integer maxPrice, String mainCategory, String subCategory) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
    }
}
