package challenge18.hotdeal.service;

import challenge18.hotdeal.common.util.Message;
import challenge18.hotdeal.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    //상품 상세 조회
    @Transactional
    public ResponseEntity<Message> selectProduct(Long id){
        productRepository.findById(id);
        return null;
    }
}
