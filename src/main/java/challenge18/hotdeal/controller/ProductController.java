package challenge18.hotdeal.controller;

import challenge18.hotdeal.common.util.Message;
import challenge18.hotdeal.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/{productId}")
    public ResponseEntity<Message> selectProduct(@PathVariable(name = "product_id")Long id){
        return productService.selectProduct(id);
    }
}
