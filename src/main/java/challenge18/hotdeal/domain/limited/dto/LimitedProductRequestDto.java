package challenge18.hotdeal.domain.limited.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class LimitedProductRequestDto {
    private String productName;
    private int price;
    private String categoryA;
    private String categoryB;
    private int amount;
}
