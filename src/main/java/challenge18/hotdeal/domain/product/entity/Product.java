package challenge18.hotdeal.domain.product.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="products")
@Getter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column
    private String productName;

    @Column
    private int price;

    @Column
    private String categoryA;

    @Column
    private String categoryB;

    @Column
    private int amount;

    public void buy(int quantity) {
        this.amount -= quantity;
    }
}
