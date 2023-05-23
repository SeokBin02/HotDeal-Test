package challenge18.hotdeal.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column
    private String productName;

    @Column
    private Long price;

    @Column
    private String categoryA;

    @Column
    private String categoryB;

    @Column
    private Long amount;

    public Product(){

    }
}
