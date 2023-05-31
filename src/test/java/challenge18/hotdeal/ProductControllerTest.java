package challenge18.hotdeal;


import challenge18.hotdeal.domain.product.dto.AllProductResponseDto;
import challenge18.hotdeal.domain.product.dto.ProductSearchCondition;
import challenge18.hotdeal.domain.product.entity.Product;
import challenge18.hotdeal.domain.product.repository.ProductRepository;
import challenge18.hotdeal.domain.product.service.ProductService;
import challenge18.hotdeal.domain.purchase.repository.PurchaseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.config.location = classpath:application-test.yml")
public class ProductControllerTest {
    Logger log = (Logger) LoggerFactory.getLogger(ProductControllerTest.class);
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    PurchaseRepository purchaseRepository;

    // http://localhost:8080/products
    @Test
    @DisplayName("Product 데이터 가져오기 테스트")
    void allProduct() throws Exception{
//        product_id, amount, categorya, categoryb, price, price_category, product_name
        // given
        Random rand = new Random(System.currentTimeMillis());

        String productName = "하우스 메이드 포켓 반팔티 블랙";
        String name_index = "";
        final String categoryA = "상의";
        final String categoryB = "반소매 티셔츠";
        int amount = 0;
        int price = 0;

        for(int i=1; i<=1000; i++){
            name_index = Integer.toString(i);
            price = (int)(rand.nextInt(35)+1) * 1000;
            amount = (int)(rand.nextInt(1000)+1);
            productRepository.saveAndFlush(new Product(productName+name_index, price, categoryA, categoryB, amount));
        }

        ProductSearchCondition condition = new ProductSearchCondition(1000l,35000l,"상의","반소매 티셔츠");
        Pageable pageable = PageRequest.of(1,10);
        ProductService productService = new ProductService(productRepository, purchaseRepository);

        //when
        Page<AllProductResponseDto> responseDtoList = productService.allProduct(condition, pageable);

        //then
//        assertTrue(responseDtoList.isEmpty());
        for(AllProductResponseDto responseDto : responseDtoList){
            log.info("가격 출력 로그 : " + Integer.toString(responseDto.getPrice()));
            assertTrue((responseDto.getPrice()>=1000 && responseDto.getPrice()<=35000));
        }
    }

    @Nested
    @DisplayName("상품 전체 조회 테스트")
    class SearchProductsTest{
        @Test
        @DisplayName("condition이 null일 경우")
        void nullCondition() throws Exception{
            //given
            ProductSearchCondition condition = new ProductSearchCondition(null, null, "",  "");
            Pageable pageable = PageRequest.of(1,10);
            ProductService productService = new ProductService(productRepository, purchaseRepository);

            //when,then
            assertTrue(productService.checkConditionNull(condition));
        }

        @Test
        @DisplayName("condition이 null이 아닐 경우")
        void notNullCondition() throws Exception{
            //given
            ProductSearchCondition condition = new ProductSearchCondition(null, null, "신발",  "로퍼");
            Pageable pageable = PageRequest.of(1,10);
            ProductService productService = new ProductService(productRepository, purchaseRepository);

            //when,then
            assertFalse(productService.checkConditionNull(condition));
        }

        @Test
        @DisplayName("category가 null값이 입력된 경우")
        void inputCategoryIsNull() throws Exception{
            //given
            ProductSearchCondition condition = new ProductSearchCondition(null, null, null,  null);
            Pageable pageable = PageRequest.of(1,10);
            ProductService productService = new ProductService(productRepository, purchaseRepository);

            //when
            productService.checkConditionNull(condition);

            //then
            assertEquals("", condition.getMainCategory());
            assertEquals("", condition.getSubCategory());
        }
    }


    @Nested
    @DisplayName("입력된 조건 값의 유효성 검사")
    class vaildateInputTest{
        @Test
        @DisplayName("min > max")
        void vaildateInput1(){
            //given
            ProductSearchCondition condition = new ProductSearchCondition(100000l, 20000l, "신발","로퍼");
            Pageable pageable = PageRequest.of(1,10);
            ProductService productService = new ProductService(productRepository, purchaseRepository);

            //when
            ProductSearchCondition afterCheckCondition = productService.validateInput(condition);

            //then
            assertEquals(20000, afterCheckCondition.getMinPrice());
            assertEquals(100000, afterCheckCondition.getMaxPrice());
        }

        @Test
        @DisplayName("min < 0")
        void vaildateInput2(){
            //given
            ProductSearchCondition condition = new ProductSearchCondition(-1l, 20000l, "신발","로퍼");
            Pageable pageable = PageRequest.of(1,10);
            ProductService productService = new ProductService(productRepository, purchaseRepository);

            //when
            ProductSearchCondition afterCheckCondition = productService.validateInput(condition);

            //then
            assertEquals(0, afterCheckCondition.getMinPrice());
            assertEquals(20000, afterCheckCondition.getMaxPrice());
        }

        @Test
        @DisplayName("max < 0")
        void vaildateInput3(){
            //given
            ProductSearchCondition condition = new ProductSearchCondition(10000l, -1l, "신발","로퍼");
            Pageable pageable = PageRequest.of(1,10);
            ProductService productService = new ProductService(productRepository, purchaseRepository);

            //when
            ProductSearchCondition afterCheckCondition = productService.validateInput(condition);

            //then
            assertEquals(10000, afterCheckCondition.getMaxPrice());
            assertEquals(0, afterCheckCondition.getMinPrice());
        }

        @Test
        @DisplayName("min > 10억")
        void vaildateInput4(){
            //given
            ProductSearchCondition condition = new ProductSearchCondition(1000000000000l, 0l, "신발","로퍼");
            Pageable pageable = PageRequest.of(1,10);
            ProductService productService = new ProductService(productRepository, purchaseRepository);

            //when
            ProductSearchCondition afterCheckCondition = productService.validateInput(condition);

            //then
            assertEquals(0, afterCheckCondition.getMinPrice());
            assertEquals(999999999, afterCheckCondition.getMaxPrice());
        }

        @Test
        @DisplayName("max > 10억")
        void vaildateInput5(){
            //given
            ProductSearchCondition condition = new ProductSearchCondition(10000l, 1000000000000l, "신발","로퍼");
            Pageable pageable = PageRequest.of(1,10);
            ProductService productService = new ProductService(productRepository, purchaseRepository);

            //when
            ProductSearchCondition afterCheckCondition = productService.validateInput(condition);

            //then
            assertEquals(999999999, afterCheckCondition.getMaxPrice());
            assertEquals(10000, afterCheckCondition.getMinPrice());
        }
    }

}
