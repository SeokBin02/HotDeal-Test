package challenge18.hotdeal.domain.limited.controller;

import challenge18.hotdeal.common.util.Message;
import challenge18.hotdeal.domain.limited.dto.LimitedProductRequestDto;
import challenge18.hotdeal.domain.limited.service.LimitedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/limited-products")
public class LimitedProductController {

    private final LimitedProductService limitedProductService;

    // 한정판 상품 등록
    @PostMapping("")
    public ResponseEntity<Message> registrationLimitedProduct(@RequestBody LimitedProductRequestDto requestDto) {
        return limitedProductService.registrationLimitedProduct(requestDto);
    }

    // 한정판 상품 목록 조회
    @GetMapping("")
    public ResponseEntity<Message> allLimitedProduct() {
        return limitedProductService.allLimitedProduct();
    }

    // 한정판 상품 상세 조회
    @GetMapping("/{limitedProductId}")
    public ResponseEntity<Message> selectLimitedProduct(@PathVariable Long limitedProductId) {
        return limitedProductService.selectLimitedProduct(limitedProductId);
    }

    // 한정판 상품 구매
    @PostMapping("/{limitedProductId}")
    public ResponseEntity<Message> buyLimitedProduct(@PathVariable Long limitedProductId) {
        return limitedProductService.buyLimitedProduct(limitedProductId);
    }

}
