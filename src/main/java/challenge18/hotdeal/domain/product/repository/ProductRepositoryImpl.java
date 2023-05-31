package challenge18.hotdeal.domain.product.repository;

import challenge18.hotdeal.domain.product.dto.AllProductResponseDto;
import challenge18.hotdeal.domain.product.dto.ProductSearchCondition;
import challenge18.hotdeal.domain.product.entity.Product;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import static challenge18.hotdeal.domain.product.entity.QProduct.product;
import static challenge18.hotdeal.domain.purchase.entity.QPurchase.purchase;
import static java.lang.Long.valueOf;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override

    public Page<AllProductResponseDto> findAllByPriceAndCategory(ProductSearchCondition condition
            , Pageable pageable
    ) {
        List<AllProductResponseDto> content = new ArrayList<>();

        //석빈 쿼리 튜닝
        List<Long> content_id = queryFactory
                .select(product.id)
                .from(product)
                .where(
                        searchPriceCategory(condition.getMinPrice(), condition.getMaxPrice()),
//                        goeMinPrice(condition.getMinPrice()),
//                        loeMaxPrice(condition.getMaxPrice()),
                        eqMainCategory(URLDecoder.decode(condition.getMainCategory())),
                        eqSubCategory(URLDecoder.decode(condition.getSubCategory()))
                )
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        if(CollectionUtils.isEmpty(content_id)){
            return new PageImpl(content, pageable, content.size());
        }

        content =  queryFactory
                .select(Projections.constructor(AllProductResponseDto.class,
                        product.productName,
                        product.price))
                .from(product)
                .where(product.id.in(content_id))
                .orderBy(product.id.desc())
                .fetch();

        return new PageImpl<>(content, pageable, content.size());

        // mainCategory = "상의"
        // subCategory = "반소매 티셔츠"
        // minPrice = "1000"
        // maxPrice = "1351000" (131만 1천원)
//        List<AllProductResponseDto> content = queryFactory
//                .select(Projections.constructor(AllProductResponseDto.class,
//                        product.productName,
//                        product.price))
//                .from(product)
//                .where(
//                        searchPriceCategory(condition.getMinPrice(), condition.getMaxPrice()),
//                        goeMinPrice(condition.getMinPrice()),
//                        loeMaxPrice(condition.getMaxPrice()),
//                        eqMainCategory(URLDecoder.decode(condition.getMainCategory())),
//                        eqSubCategory(URLDecoder.decode(condition.getSubCategory()))
//                )
//
//                .offset(pageable.getOffset()) // 페이지 번호
//                .limit(pageable.getPageSize()) // 페이지 사이즈
//                .fetch();

//        Long total = queryFactory
//                .select(product.id.count())
//                .from(product)
//                .where(
//                        searchPriceCategory(condition.getMinPrice(), condition.getMaxPrice()),
//                        goeMinPrice(condition.getMinPrice()),
//                        loeMaxPrice(condition.getMaxPrice()),
//                        eqMainCategory(condition.getMainCategory()),
//                        eqSubCategory(condition.getSubCategory())
//                )
//                .fetchOne();
//
//         return new PageImpl<>(content, pageable, total);
//         return content;
    }
//    product.id, product.id.count().as("sold_cnt")



    // 대분류 검색
    private BooleanExpression eqMainCategory(String searchMainCategory) {
        return searchMainCategory == null ? null : product.categoryA.eq(searchMainCategory);
    }

    // 중분류 검색
    private BooleanExpression eqSubCategory(String searchSubCategory) {
        return searchSubCategory == null ? null : product.categoryB.eq(searchSubCategory);
    }

    // 가격대 구간 검색 (priceCategory)
    private BooleanExpression searchPriceCategory(Long minPrice, Long maxPrice) {
        // 최고가, 최저가가 주어지지 않았을 때
        if (minPrice == null && maxPrice == null) {
            return null;
        }

        // 최고가가 주어졌는데
        if (maxPrice != null) {
            int maxPriceCategory = maxPrice.intValue() / 10000;
            // 10000원 미만일 경우
            if (maxPriceCategory == 0) {
                // where price_category in (0)
                return product.priceCategory.eq(0);
            }

            // 최저가도 주어진 경우
            if (minPrice != null) {
                int minPriceCategory = minPrice.intValue() / 10000;
                // 최고가와 최저가의 priceCategory가 같을 경우
                System.out.println("minPriceCategory = " + minPriceCategory);
                System.out.println("maxPriceCategory = " + maxPriceCategory);
                if (maxPriceCategory == minPriceCategory) {
                    // where price_category in (10)
                    return product.priceCategory.eq(minPriceCategory);
                } else {
                    // where price_category >= 0
                    // and price_category <= 10
                    return product.priceCategory.goe(minPriceCategory).and(product.priceCategory.loe(maxPriceCategory));
                }
            }

            // 최저가가 주어지지 않을 경우, 최고가만 주어진 경우
            else {
                return product.priceCategory.loe(maxPriceCategory);
            }
        }

        // 최고가가 주어지지 않은 경우, 최저가만 주어진 경우
        else {
            int minPriceCategory = minPrice.intValue() / 10000;
            return product.priceCategory.goe(minPriceCategory);
        }
    }

    //  최저가 검색
    private BooleanExpression goeMinPrice(Long minPrice) {
        return minPrice == null ? null : product.price.goe(minPrice);
    }

    // 최고가 검색
    private BooleanExpression loeMaxPrice(Long maxPrice) {
        return maxPrice != null ? product.price.loe(maxPrice) : null;
    }

    // 가격대 검색
    private BooleanExpression betweenPrice(Long minPrice, Long maxPrice) {
        return goeMinPrice(minPrice).and(loeMaxPrice(maxPrice));
    }
}
