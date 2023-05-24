package challenge18.hotdeal.domain.product.controller;

import challenge18.hotdeal.common.security.UserDetailsImpl;
import challenge18.hotdeal.common.util.Message;
import challenge18.hotdeal.domain.product.dto.ProductResponseDto;
import challenge18.hotdeal.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 상품 상세 조회
    @GetMapping("/products/{productId}")
    public ProductResponseDto selectProduct(@PathVariable Long productId){
        return productService.selectProduct(productId);
    }

    // 상품 구매
    @PostMapping("/products/{productId}")
    public ResponseEntity<Message> buyLimitedProduct(@PathVariable Long productId,
                                                     @RequestBody int quantity,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.buyProduct(productId, quantity, userDetails.getUser());
    }
}
