package hu.flamingo.app.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hu.flamingo.app.model.Product;
import hu.flamingo.app.model.QProduct;
import hu.flamingo.app.model.Segment;
import hu.flamingo.app.repository.interfaces.IProductRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

import static hu.flamingo.app.model.QProduct.product;

public class ProductRepository extends GenericRepository<Product> implements IProductRepository {

    public ProductRepository() {
        super(Product.class);
    }

    @Override
    public List<Product> findBySegment(Segment segment) {
        EntityManager em = getEntityManager();
        JPAQueryFactory queryFactory = getQueryFactory(em);

        return queryFactory.selectFrom(product)
                .where(product.segment.eq(segment))
                .fetch();
    }
}
