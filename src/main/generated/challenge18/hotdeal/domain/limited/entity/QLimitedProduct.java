package challenge18.hotdeal.domain.limited.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QLimitedProduct is a Querydsl query type for LimitedProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLimitedProduct extends EntityPathBase<LimitedProduct> {

    private static final long serialVersionUID = 2003989179L;

    public static final QLimitedProduct limitedProduct = new QLimitedProduct("limitedProduct");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final StringPath categoryA = createString("categoryA");

    public final StringPath categoryB = createString("categoryB");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath productName = createString("productName");

    public QLimitedProduct(String variable) {
        super(LimitedProduct.class, forVariable(variable));
    }

    public QLimitedProduct(Path<? extends LimitedProduct> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLimitedProduct(PathMetadata metadata) {
        super(LimitedProduct.class, metadata);
    }

}

