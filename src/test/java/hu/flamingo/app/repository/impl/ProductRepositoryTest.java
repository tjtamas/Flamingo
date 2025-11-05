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
        Product p1 = new Product("Flamingo Basic", 12.5,
                "Lakossági termék", Segment.LAKOSSAGI);

        Product p2 = new Product("Flamingo Pro", 25.0,
                "Üzleti szolgáltatás", Segment.UZLETI);

        repo.save(p1);
        repo.save(p2);

        // Tesztelés
        List<Product> all = repo.findAll();
        assertFalse(all.isEmpty(), "A findAll() nem adhat üres listát.");
        assertEquals(2, all.size(), "Két terméknek kell lennie az adatbázisban.");
    }

    @Test
    void testFindBySegment() {
        Product lak = new Product("Flamingo Light", 9.99,
                "Lakossági csomag", Segment.LAKOSSAGI);

        Product uzl = new Product("Flamingo Business", 29.99,
                "Üzleti csomag", Segment.UZLETI);

        repo.save(lak);
        repo.save(uzl);

        List<Product> lakossagiak = repo.findBySegment(Segment.LAKOSSAGI);

        assertEquals(1, lakossagiak.size(), "Csak egy lakossági terméknek kell lennie.");
        assertEquals("Flamingo Light", lakossagiak.get(0).getName());
    }
}
