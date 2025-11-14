package hu.flamingo.app.service;

import hu.flamingo.app.model.Product;
import hu.flamingo.app.model.Segment;
import hu.flamingo.app.repository.impl.ProductRepository;

import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;

    // ----- Constructor -----
    public ProductService() {
        this.productRepository = new ProductRepository();
    }

    // ----- CRUD műveletek -----

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("A termék nem lehet null.");
        }
        productRepository.save(product);
    }

    public void updateProduct(Product product) {
        if (product == null || product.getId() == null) {
            throw new IllegalArgumentException("Érvénytelen termék módosítás.");
        }
        productRepository.update(product);
    }

    public void deleteProduct(Product product) {
        if (product == null || product.getId() == null) {
            throw new IllegalArgumentException("Érvénytelen termék törlés.");
        }
        productRepository.delete(product.getId());
    }


    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> findBySegment(Segment segment) {
        if (segment == null) {
            throw new IllegalArgumentException("A szegmens nem lehet null.");
        }
        return productRepository.findBySegment(segment);
    }

    public Product findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Az ID nem lehet null.");
        }
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }


}
