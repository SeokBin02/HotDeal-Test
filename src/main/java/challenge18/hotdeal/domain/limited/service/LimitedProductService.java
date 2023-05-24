package challenge18.hotdeal.domain.limited.service;

import challenge18.hotdeal.common.util.Message;
import challenge18.hotdeal.domain.limited.dto.LimitedProductRequestDto;
import challenge18.hotdeal.domain.limited.dto.LimitedProductResponseDto;
import challenge18.hotdeal.domain.limited.entity.LimitedProduct;
import challenge18.hotdeal.domain.limited.repository.LimitedProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LimitedProductService {

    private final LimitedProductRepository limitedProductRepository;

    // 한정판 상품 등록
    public ResponseEntity<Message> registrationLimitedProduct(LimitedProductRequestDto requestDto) {
        LimitedProduct limitedProduct = new LimitedProduct(requestDto);
        limitedProductRepository.save(limitedProduct);
        return new ResponseEntity<>(new Message("한정판 상품 등록 성공"), HttpStatus.OK);
    }

    // 한정판 상품 전체 조회
    public List<LimitedProductResponseDto> allLimitedProduct() {
        return limitedProductRepository.findAll()
                .stream()
                .map(LimitedProductResponseDto::new)
                .collect(Collectors.toList());
    }

    // 한정판 상품 상세 조회
    public LimitedProductResponseDto selectLimitedProduct(Long limitedProductId) {
        LimitedProduct limitedProduct = limitedProductRepository.findById(limitedProductId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
        return new LimitedProductResponseDto(limitedProduct);
    }

    // 한정판 상품 구매
    public ResponseEntity<Message> buyLimitedProduct(Long limitedProductId) {
        LimitedProduct limitedProduct = limitedProductRepository.findById(limitedProductId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
        if (limitedProduct.getAmount() <= 0) {
            throw new IllegalArgumentException("상품 재고가 없습니다.");
        } else {
            limitedProduct.buy();
        }
        return new ResponseEntity<>(new Message("한정판 상품 구매 성공"), HttpStatus.OK);
    }
}
