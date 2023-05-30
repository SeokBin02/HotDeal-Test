package challenge18.hotdeal.domain.purchase.repository;

import challenge18.hotdeal.domain.product.dto.AllProductResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static challenge18.hotdeal.domain.product.entity.QProduct.product;
import static challenge18.hotdeal.domain.purchase.entity.QPurchase.purchase;

@Repository
@RequiredArgsConstructor
public class PurchaseRepositoryImpl implements PurchaseRespositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<AllProductResponseDto> findTop90(
//            Pageable pageable
    ) {
        List<AllProductResponseDto> result = queryFactory
                .select(Projections.constructor(AllProductResponseDto.class,
                        product.productName,
                        product.price))
                .from(purchase)
                .leftJoin(purchase.product, product)
                .where(purchase.product.isNotNull())
                .groupBy(purchase.product)
                .orderBy(purchase.amount.sum().desc())
                .limit(90)
                .fetch();

//        return new PageImpl<>(result, pageable, 90);
        return result;
    }

}
