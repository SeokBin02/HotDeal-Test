package challenge18.hotdeal.dto;

import challenge18.hotdeal.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@AllArgsConstructor
public class ProductResponseDto {
    private Long productId;
    private String productName;
    private int price;
    private String categoryA;
    private String categoryB;
    private int amount;

    public ProductResponseDto(Product product){
        this.productId = product.getId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.categoryA = product.getCategoryA();
        this.categoryB = product.getCategoryB();
        this.amount = product.getAmount();
    }
}
