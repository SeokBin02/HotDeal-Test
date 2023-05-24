package challenge18.hotdeal.domain.limited.dto;

import challenge18.hotdeal.domain.limited.entity.LimitedProduct;
import lombok.Getter;

@Getter
public class LimitedProductResponseDto {
    private Long limitedProductId;
    private String productName;
    private int price;
    private String categoryA;
    private String categoryB;
    private int amount;

    public LimitedProductResponseDto(LimitedProduct limitedProduct) {
        this.limitedProductId = limitedProduct.getId();
        this.productName = limitedProduct.getProductName();
        this.price = limitedProduct.getPrice();
        this.categoryA = limitedProduct.getCategoryA();
        this.categoryB = limitedProduct.getCategoryB();
        this.amount = limitedProduct.getAmount();
    }
}
