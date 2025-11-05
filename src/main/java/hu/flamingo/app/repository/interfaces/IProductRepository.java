package hu.flamingo.app.repository.interfaces;

import hu.flamingo.app.model.Product;
import hu.flamingo.app.model.Segment;
import java.util.List;

public interface IProductRepository  extends IGenericRepository<Product> {

    List<Product> findBySegment(Segment segment);

}
