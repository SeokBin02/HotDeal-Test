package challenge18.hotdeal.service;

import challenge18.hotdeal.dto.ProductResponseDto;
import challenge18.hotdeal.entity.Product;
import challenge18.hotdeal.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JmeterTestService {

    private final ProductRepository productRepository;

    @Transactional
    public List<ProductResponseDto> test() {
        List<ProductResponseDto> list = new ArrayList<>();
        List<Product> productList = productRepository.findAll();
        for(Product product : productList){
            list.add(new ProductResponseDto(product));
        }
        return list;
    }
}
