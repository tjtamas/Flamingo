package hu.flamingo.app.repository.interfaces;

import hu.flamingo.app.model.Sale;

import java.time.LocalDate;
import java.util.List;

public interface ISaleRepository extends IGenericRepository<Sale> {

    List<Sale> findByUser(Long userId);

    List<Sale> findByDate(LocalDate date);

    List<Sale> findByDateRange(LocalDate from, LocalDate to);

    List<Sale> findByUserAndDateRange(Long userId, LocalDate from, LocalDate to);

    List<Sale> findFilteredPaged(Long userId, LocalDate from, LocalDate to, int limit, int offset);

    long countFiltered(Long userId, LocalDate from, LocalDate to);



}
