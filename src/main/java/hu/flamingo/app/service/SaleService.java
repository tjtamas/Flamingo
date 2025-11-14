package hu.flamingo.app.service;

import hu.flamingo.app.model.Sale;
import hu.flamingo.app.model.Product;
import hu.flamingo.app.repository.impl.SaleRepository;
import hu.flamingo.app.repository.interfaces.ISaleRepository;

import java.time.LocalDate;
import java.util.List;

public class SaleService {

    private final ISaleRepository saleRepository = new SaleRepository();

    public SaleService() {}



    // ---------- Mentés előtti fő metódus ----------
    public void saveSale(Sale sale) {
        validate(sale);
        calculateTotalVbs(sale);
        saleRepository.save(sale);
    }


    private void validate(Sale sale) {

        if (sale.getDate() == null) {
            throw new IllegalArgumentException("A dátum nem lehet üres.");
        }

        if (sale.getUser() == null) {
            throw new IllegalArgumentException("A dolgozó nincs megadva.");
        }


        if ((sale.getMtIdentifier() == null || sale.getMtIdentifier().isBlank()) &&
                (sale.getMsisdn() == null || sale.getMsisdn().isBlank())) {
            throw new IllegalArgumentException("Legalább MT vagy MSISDN kötelező.");
        }

        if (sale.getSegment() == null) {
            throw new IllegalArgumentException("A szegmens kötelező.");
        }


    }

    public List<Sale> findFilteredPaged(Long userId, LocalDate from, LocalDate to, int limit, int offset) {
        return saleRepository.findFilteredPaged(userId, from, to, limit, offset);
    }

    public long countFiltered(Long userId, LocalDate from, LocalDate to) {
        return saleRepository.countFiltered(userId, from, to);
    }


    // ---------- VBS számítás ----------
    private void calculateTotalVbs(Sale sale) {
        int total = 0;

        total += extractVbs(sale.getMobilHang());
        total += extractVbs(sale.getMobilAdat());
        total += extractVbs(sale.getVezetekesNet());
        total += extractVbs(sale.getVezetekesTv());
        total += extractVbs(sale.getVezetekesHang());

        // Digitális + NonCore direkt érték Excelből
        if (sale.getDigitalis() != null) {
            total += sale.getDigitalis();
        }

        if (sale.getNonCore() != null) {
            total += sale.getNonCore();
        }

        sale.setTotalVbs(total);
    }

    private int extractVbs(Product product) {
        if (product == null) return 0;
        return (int) product.getVbsValue();
    }


}
