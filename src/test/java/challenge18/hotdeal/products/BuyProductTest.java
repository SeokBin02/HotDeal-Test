//package challenge18.hotdeal.products;
//
//import challenge18.hotdeal.domain.product.entity.Product;
//import challenge18.hotdeal.domain.product.repository.ProductRepository;
//import challenge18.hotdeal.domain.product.service.ProductService;
//import challenge18.hotdeal.domain.purchase.repository.PurchaseRepository;
//import challenge18.hotdeal.domain.user.entity.User;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.parameters.P;
//
//@SpringBootTest(properties = "spring.config.location = classpath:application-test.yml")
//public class BuyProductTest {
//    Logger log = LoggerFactory.getLogger(AllProductTest.class);
//    @Autowired
//    ProductService productService;
//    @Autowired
//    ProductRepository productRepository;
//    @Autowired
//    PurchaseRepository purchaseRepository;
//
//    @Test
//    @DisplayName("로그인 유무 테스트")
//    void test1(){
//        //given
//        int quantity = 13;
//        Product product = new Product("ABC", 35000, "바지", "레깅스", 300);
//
//        //when
//        productService.buyProduct()
//
//    }
//
//}
