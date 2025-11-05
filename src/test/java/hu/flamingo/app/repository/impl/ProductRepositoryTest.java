package hu.flamingo.app.repository.impl;

import hu.flamingo.app.model.Product;
import hu.flamingo.app.model.Segment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    private ProductRepository repo;

    @BeforeEach
    void setup() {
        repo = new ProductRepository();

        var em = repo.getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Product").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    @Test
    void testSaveAndFindAll() {
        // Előkészítés
        Product p1 = Product.builder()
                .name("Flamingo Basic")
                .vbsValue(12.5)
                .category("Lakossági termék")
                .segment(Segment.LAKOSSAGI)
                .build();

        Product p2 = Product.builder()
                .name("Flamingo Pro")
                .vbsValue(25.0)
                .category("Üzleti szolgáltatás")
                .segment(Segment.UZLETI)
                .build();

        repo.save(p1);
        repo.save(p2);

        // Tesztelés
        List<Product> all = repo.findAll();
        assertFalse(all.isEmpty(), "A findAll() nem adhat üres listát.");
        assertEquals(2, all.size(), "Két terméknek kell lennie az adatbázisban.");
    }

    @Test
    void testFindBySegment() {
        Product lak = Product.builder()
                .name("Flamingo Light")
                .vbsValue(9.99)
                .category("Lakossági csomag")
                .segment(Segment.LAKOSSAGI)
                .build();

        Product uzl = Product.builder()
                .name("Flamingo Business")
                .vbsValue(29.99)
                .category("Üzleti csomag")
                .segment(Segment.UZLETI)
                .build();

        repo.save(lak);
        repo.save(uzl);

        List<Product> lakossagiak = repo.findBySegment(Segment.LAKOSSAGI);

        assertEquals(1, lakossagiak.size(), "Csak egy lakossági terméknek kell lennie.");
        assertEquals("Flamingo Light", lakossagiak.get(0).getName());
    }
}
