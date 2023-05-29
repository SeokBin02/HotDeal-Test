package challenge18.hotdeal.domain.product.repository;

import challenge18.hotdeal.domain.product.dto.AllProductResponseDto;
import challenge18.hotdeal.domain.product.dto.ProductSearchCondition;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.net.URLDecoder;
import java.util.List;

import static challenge18.hotdeal.domain.product.entity.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<AllProductResponseDto> findAllByPriceAndCategory(ProductSearchCondition condition
                                                                 //, Pageable pageable
    ) {
        // mainCategory = "상의"
        // subCategory = "반소매 티셔츠"
        // minPrice = "1000"
        // maxPrice = "1351000" (131만 1천원)
        List<AllProductResponseDto> content = queryFactory
                .select(Projections.constructor(AllProductResponseDto.class,
                        product.productName,
                        product.price))
                .from(product)
                .where(
                        searchPriceCategory(condition.getMinPrice(), condition.getMaxPrice()),
                        goeMinPrice(condition.getMinPrice()),
                        loeMaxPrice(condition.getMaxPrice()),
                        eqMainCategory(URLDecoder.decode(condition.getMainCategory())),
                        eqSubCategory(URLDecoder.decode(condition.getSubCategory()))
                )
                //.offset(pageable.getOffset()) // 페이지 번호
                //.limit(pageable.getPageSize()) // 페이지 사이즈
                .fetch();

//        Long count = queryFactory
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

        // return new PageImpl<>(content, pageable, 90);
        return content;
    }

    // 대분류 검색
    private BooleanExpression eqMainCategory(String searchMainCategory) {
        return searchMainCategory == null ? null : product.categoryA.eq(searchMainCategory);
    }

    // 중분류 검색
    private BooleanExpression eqSubCategory(String searchSubCategory) {
        return searchSubCategory == null ? null : product.categoryB.eq(searchSubCategory);
    }

    // 가격대 구간 검색 (priceCategory)
    private BooleanExpression searchPriceCategory(Integer minPrice, Integer maxPrice) {
        if (minPrice == null && maxPrice == null) {
            return null;
        }

        // 최고가가 주어졌는데
        if (maxPrice != null) {
            int maxPriceCategory = maxPrice / 10000;
            // 10000원 미만일 경우
            if (maxPriceCategory == 0) {
                // where price_category in (0)
                return product.priceCategory.eq(0);
            }

            // 최저가도 주어진 경우
            if (minPrice != null) {
                int minPriceCategory = minPrice / 10000;
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
            int minPriceCategory = minPrice / 10000;
            return product.priceCategory.goe(minPriceCategory);
        }
    }

    //  최저가 검색
    private BooleanExpression goeMinPrice(Integer minPrice) {
        return minPrice == null ? null : product.price.goe(minPrice);
    }

    // 최고가 검색
    private BooleanExpression loeMaxPrice(Integer maxPrice) {
        return maxPrice != null ? product.price.loe(maxPrice) : null;
    }

    // 가격대 검색
    private BooleanExpression betweenPrice(Integer minPrice, Integer maxPrice) {
        return goeMinPrice(minPrice).and(loeMaxPrice(maxPrice));
    }
}
