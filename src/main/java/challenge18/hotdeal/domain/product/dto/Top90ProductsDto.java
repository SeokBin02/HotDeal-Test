package challenge18.hotdeal.domain.product.dto;

import lombok.Data;

@Data
public class Top90ProductsDto {
    private String productName;
    private int price;
    private String categoryA;
    private String categoryB;
    private int amount;

    public Top90ProductsDto(String productName, int price, String categoryA, String categoryB, int amount) {
        this.productName = productName;
        this.price = price;
        this.categoryA = categoryA;
        this.categoryB = categoryB;
        this.amount = amount;
    }
}
