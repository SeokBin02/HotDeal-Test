package challenge18.hotdeal.domain.product.service;

import challenge18.hotdeal.common.util.Message;
import challenge18.hotdeal.domain.product.dto.ConditionDto;
import challenge18.hotdeal.domain.product.dto.ProductResponseDto;
import challenge18.hotdeal.domain.product.entity.Product;
import challenge18.hotdeal.domain.product.repository.ProductRepository;
import challenge18.hotdeal.domain.purchase.entity.Purchase;
import challenge18.hotdeal.domain.purchase.repository.PurchaseRepository;
import challenge18.hotdeal.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;

    // 상품 전체 조회 (필터링)
    public List<ProductResponseDto> allProduct(ConditionDto condition) throws IllegalAccessException {

        boolean allNull = true;

        // condition의 모들 필드 값을 체크하여 전부 null일 경우 allNull = true
        for (Field f : condition.getClass().getFields()) {
            if (f.get(condition) != null) {
                allNull = false; break;
            }
        }

        // 아무 조건도 제공되지 않을 경우, Top 90 계산하여 제공한다.
        if (allNull) {
            System.out.println("조건 없음");
            return null;
        }

        // 동적 쿼리
        return productRepository.findAllByPriceAndCategory(condition)
                .stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

    //상품 상세 조회
    public ProductResponseDto selectProduct(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
        return new ProductResponseDto(product);
    }

    // 상품 구매
    @Transactional(readOnly = false)
    public ResponseEntity<Message> buyProduct(Long productId, int quantity, User user) {
        if (user == null) {
            return new ResponseEntity<>(new Message("로그인이 필요합니다."), HttpStatus.BAD_REQUEST);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        if (product.getAmount() <= 0) {
            throw new IllegalArgumentException("상품 재고가 없습니다.");
        } else if ((product.getAmount() > 0) && (product.getAmount() < quantity)) {
            throw new IllegalArgumentException("주문하신 상품의 재고가 " + quantity + "개 남았습니다.");
        }

        product.buy(quantity);
        purchaseRepository.save(new Purchase(quantity, user, product, null));
        return new ResponseEntity<>(new Message("상품 구매 성공"), HttpStatus.OK);
    }


}
