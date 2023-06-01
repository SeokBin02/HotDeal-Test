package challenge18.hotdeal.products;


import challenge18.hotdeal.domain.product.dto.AllProductResponseDto;
import challenge18.hotdeal.domain.product.dto.ProductSearchCondition;
import challenge18.hotdeal.domain.product.entity.Product;
import challenge18.hotdeal.domain.product.repository.ProductRepository;
import challenge18.hotdeal.domain.product.service.ProductService;
import challenge18.hotdeal.domain.purchase.repository.PurchaseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.config.location = classpath:application-test.yml")
public class AllProductTest {
    Logger log = LoggerFactory.getLogger(AllProductTest.class);
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    PurchaseRepository purchaseRepository;

    // http://localhost:8080/products
    @Test
    @DisplayName("Product 데이터 가져오기 테스트")
    void allProduct(){
//        product_id, amount, categorya, categoryb, price, price_category, product_name
        // given
        Random rand = new Random(System.currentTimeMillis());

        String productName = "하우스 메이드 포켓 반팔티 블랙";
        String name_index;
        final String categoryA = "상의";
        final String categoryB = "반소매 티셔츠";
        int amount;
        int price;

        for(int i=1; i<=1000; i++){
            name_index = Integer.toString(i);
            price = (rand.nextInt(35)+1) * 1000;
            amount = rand.nextInt(1000)+1;
            productRepository.saveAndFlush(new Product(productName+name_index, price, categoryA, categoryB, amount));
        }

        ProductSearchCondition condition = new ProductSearchCondition(1000L,35000L,"상의","반소매 티셔츠");
        Pageable pageable = PageRequest.of(1,10);
        ProductService productService = new ProductService(productRepository, purchaseRepository);

        //when
        Page<AllProductResponseDto> responseDtoList = productService.allProduct(condition, pageable);

        //then
        for(AllProductResponseDto responseDto : responseDtoList){
            log.info("가격 출력 로그 : " + responseDto.getPrice());
            assertTrue((responseDto.getPrice()>=1000 && responseDto.getPrice()<=35000));
        }
    }

    @Nested
    @DisplayName("상품 전체 조회 테스트")
    class SearchProductsTest{
        @Test
        @DisplayName("condition이 null일 경우")
        void nullCondition(){
            //given
            ProductSearchCondition condition = new ProductSearchCondition(null, null, "",  "");
            ProductService productService = new ProductService(productRepository, purchaseRepository);

            //when,then
            assertTrue(productService.checkConditionNull(condition));
        }

        @Test
        @DisplayName("condition이 null이 아닐 경우")
        void notNullCondition(){
            //given
            ProductSearchCondition condition = new ProductSearchCondition(null, null, "신발",  "로퍼");
            ProductService productService = new ProductService(productRepository, purchaseRepository);

            //when,then
            assertFalse(productService.checkConditionNull(condition));
        }

        @Test
        @DisplayName("category가 null값이 입력된 경우")
        void inputCategoryIsNull(){
            //given
            ProductSearchCondition condition = new ProductSearchCondition(null, null, null,  null);
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
            ProductSearchCondition condition = new ProductSearchCondition(100000L, 20000L, "신발","로퍼");
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
            ProductSearchCondition condition = new ProductSearchCondition(-1L, 20000L, "신발","로퍼");
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
            ProductSearchCondition condition = new ProductSearchCondition(10000L, -1L, "신발","로퍼");
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
            ProductSearchCondition condition = new ProductSearchCondition(1000000000000L, 0L, "신발","로퍼");
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
            ProductSearchCondition condition = new ProductSearchCondition(10000L, 1000000000000L, "신발","로퍼");
            ProductService productService = new ProductService(productRepository, purchaseRepository);

            //when
            ProductSearchCondition afterCheckCondition = productService.validateInput(condition);

            //then
            assertEquals(999999999, afterCheckCondition.getMaxPrice());
            assertEquals(10000, afterCheckCondition.getMinPrice());
        }
    }
}
