package challenge18.hotdeal.domain.limited.entity;

import challenge18.hotdeal.domain.limited.dto.LimitedProductRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "limited_products")
public class LimitedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private int price;

    private String categoryA;

    private String categoryB;

    private int amount;

    public LimitedProduct(LimitedProductRequestDto requestDto) {
        this.productName = requestDto.getProductName();
        this.price = requestDto.getPrice();
        this.categoryA = requestDto.getCategoryA();
        this.categoryB = requestDto.getCategoryB();
        this.amount = requestDto.getAmount();
    }

    public void buy() {
        this.amount -= 1;
    }
}
