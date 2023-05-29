package challenge18.hotdeal.domain.product.repository;

import challenge18.hotdeal.domain.product.dto.ConditionDto;
import challenge18.hotdeal.domain.product.entity.Product;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static challenge18.hotdeal.domain.product.entity.QProduct.product;
import static challenge18.hotdeal.domain.purchase.entity.QPurchase.purchase;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> findAllByPriceAndCategory(ConditionDto condition) {
        // mainCategory = "상의"
        // subCategory = "반소매 티셔츠"
        // minPrice = "1000"
        // maxPrice = "1351000" (131만 1천원)
        return queryFactory
                .selectFrom(product)
                .where(
                        goeMinPrice(condition.getMinPrice()),
                        loeMaxPrice(condition.getMaxPrice()),
                        eqMainCategory(condition.getMainCategory()),
                        eqSubCategory(condition.getSubCategory())
                )
                .fetch();
    }
//    product.id, product.id.count().as("sold_cnt")
    @Override
    public List<Product> findPorpularTop90() {
        List<Long> popularProductIds = queryFactory
                .select(product.id)
                .from(product)
                .leftJoin(purchase)
                .on(product.id.eq(purchase.product.id))
                .groupBy(product.id)
                .orderBy(product.id.count().desc())
                .limit(90)
                .fetch();

        return queryFactory
                .selectFrom(product)
                .where(product.id.in(popularProductIds))
                .fetch();
//        return queryFactory
//                .select(product.id)
//                .from(product)
//                .leftJoin(purchase)
//                .on(product.id.eq(purchase.product.id))
//                .groupBy(product.id)
//                .orderBy(product.id.count().desc())
//                .limit(90)
//                .fetch();
//        return null;
    }

    @Override
    public List<Product> findByKeyword(String keyword) {
        return queryFactory.select(product).from(product)
                .where(product.productName.like(keyword)).fetch();
    }


    // 대분류 검색
    private BooleanExpression eqMainCategory(String searchMainCategory) {
        return searchMainCategory == null ? null : product.categoryA.eq(searchMainCategory);
    }

    // 중분류 검색
    private BooleanExpression eqSubCategory(String searchSubCategory) {
        return searchSubCategory == null ? null : product.categoryB.eq(searchSubCategory);
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
