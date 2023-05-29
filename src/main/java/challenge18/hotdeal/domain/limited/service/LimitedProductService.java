package challenge18.hotdeal.domain.limited.service;

import challenge18.hotdeal.common.Enum.UserRole;
import challenge18.hotdeal.common.util.Message;
import challenge18.hotdeal.domain.limited.dto.LimitedProductRequestDto;
import challenge18.hotdeal.domain.limited.dto.LimitedProductResponseDto;
import challenge18.hotdeal.domain.limited.entity.LimitedProduct;
import challenge18.hotdeal.domain.limited.repository.LimitedProductRepository;
import challenge18.hotdeal.domain.purchase.entity.Purchase;
import challenge18.hotdeal.domain.purchase.repository.PurchaseRepository;
import challenge18.hotdeal.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LimitedProductService {

    private final LimitedProductRepository limitedProductRepository;
    private final PurchaseRepository purchaseRepository;

    // 한정판 상품 등록
    @Transactional(readOnly = false)
    public ResponseEntity<Message> registrationLimitedProduct(LimitedProductRequestDto requestDto, User user) {
        if (user.getRole() == UserRole.ROLE_USER) {
            return new ResponseEntity<>(new Message("관리자가 아닙니다."), HttpStatus.BAD_REQUEST);
        }
        limitedProductRepository.save(new LimitedProduct(requestDto));
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
    @Transactional(readOnly = false)
    public ResponseEntity<Message> buyLimitedProduct(Long limitedProductId, User user) {
        if (user == null) {
            return new ResponseEntity<>(new Message("로그인이 필요합니다."), HttpStatus.BAD_REQUEST);
        }

        LimitedProduct limitedProduct = limitedProductRepository.findWithId(limitedProductId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));


        if (limitedProduct.getAmount() <= 0) {
            throw new IllegalArgumentException("상품 재고가 없습니다.");
        }

        limitedProduct.buy();
        purchaseRepository.save(new Purchase(1, user, null, limitedProduct));
        return new ResponseEntity<>(new Message("한정판 상품 구매 성공"), HttpStatus.OK);
    }
}
