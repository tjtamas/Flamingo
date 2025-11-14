package hu.flamingo.app.repository.impl;

import hu.flamingo.app.model.Product;
import hu.flamingo.app.model.Sale;
import hu.flamingo.app.model.User;
import hu.flamingo.app.model.Segment;
import hu.flamingo.app.repository.interfaces.ISaleRepository;

import hu.flamingo.app.service.SaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SaleServiceTest {

    private ISaleRepository repo;
    private SaleService service;

    @BeforeEach
    void setup() {
        repo = mock(ISaleRepository.class);
        service = new SaleService();
    }

    @Test
    void testSaveSale_calculatesVbsCorrectly() {
        // --- Előkészítés ---
        Sale sale = new Sale();
        sale.setDate(LocalDate.now());
        sale.setUser(new User());
        sale.setMtIdentifier("12345678");
        sale.setSegment(Segment.LAKOSSAGI);

        Product mobilAdat = new Product();
        mobilAdat.setVbsValue(1500);

        Product tv = new Product();
        tv.setVbsValue(2500);

        sale.setMobilAdat(mobilAdat);
        sale.setVezetekesTv(tv);
        sale.setDigitalis(2);
        sale.setNonCore(1);

        // --- Teszt ---
        service.saveSale(sale);

        // --- Ellenőrzés ---
        assertEquals(1500 + 2500 + 2 + 1, sale.getTotalVbs());

        // Repository mentés: egyszer hívódik
        verify(repo, times(1)).save(sale);
    }

    @Test
    void testValidationFails_whenMissingIdentifiers() {
        Sale sale = new Sale();
        sale.setDate(LocalDate.now());
        sale.setUser(new User());
        sale.setSegment(Segment.LAKOSSAGI);

        Exception ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.saveSale(sale)
        );

        assertEquals("Legalább MT vagy MSISDN kötelező.", ex.getMessage());
    }

    @Test
    void testValidationFails_whenDateMissing() {
        Sale sale = new Sale();
        sale.setUser(new User());
        sale.setMtIdentifier("123");
        sale.setSegment(Segment.LAKOSSAGI);

        Exception ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.saveSale(sale)
        );

        assertEquals("A dátum nem lehet üres.", ex.getMessage());
    }

    @Test
    void testValidationFails_whenUserMissing() {
        Sale sale = new Sale();
        sale.setDate(LocalDate.now());
        sale.setMtIdentifier("123");
        sale.setSegment(Segment.LAKOSSAGI);

        Exception ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.saveSale(sale)
        );

        assertEquals("A dolgozó nincs megadva.", ex.getMessage());
    }
}
