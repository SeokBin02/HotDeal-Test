package challenge18.hotdeal.domain.limited.controller;

import challenge18.hotdeal.common.security.UserDetailsImpl;
import challenge18.hotdeal.common.util.Message;
import challenge18.hotdeal.domain.limited.dto.LimitedProductRequestDto;
import challenge18.hotdeal.domain.limited.dto.LimitedProductResponseDto;
import challenge18.hotdeal.domain.limited.service.LimitedProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/limited-products")
public class LimitedProductController {

    private final LimitedProductService limitedProductService;

    // 한정판 상품 등록
//    @Secured("ROLE_ADMIN")
    @PostMapping("")
    public ResponseEntity<Message> registrationLimitedProduct(@RequestBody LimitedProductRequestDto requestDto,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return limitedProductService.registrationLimitedProduct(requestDto, userDetails.getUser());
    }

    // 한정판 상품 목록 조회
    @GetMapping
    public List<LimitedProductResponseDto> allLimitedProduct() {
        return limitedProductService.allLimitedProduct();
    }

    // 한정판 상품 상세 조회
    @GetMapping("/{limitedProductId}")
    public LimitedProductResponseDto selectLimitedProduct(@PathVariable Long limitedProductId) {
        return limitedProductService.selectLimitedProduct(limitedProductId);
    }

    // 한정판 상품 구매
    @PostMapping("/{limitedProductId}")
    public ResponseEntity<Message> buyLimitedProduct(@PathVariable Long limitedProductId,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return limitedProductService.buyLimitedProduct(limitedProductId, userDetails.getUser());
    }

}
