package hu.flamingo.app.repository.impl;

import hu.flamingo.app.model.QSale;
import hu.flamingo.app.model.Sale;
import hu.flamingo.app.repository.interfaces.ISaleRepository;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

public class SaleRepository extends GenericRepository<Sale> implements ISaleRepository {

    private static final QSale qSale = QSale.sale;

    public SaleRepository() {
        super(Sale.class);
    }

    @Override
    public List<Sale> findByUser(Long userId) {
        EntityManager em = getEntityManager();
        try {
            var query = getQueryFactory(em)
                    .selectFrom(qSale)
                    .where(qSale.user.userId.eq(userId));

            return query.fetch();
        } finally {
            em.close();
        }
    }



    @Override
    public List<Sale> findByDate(LocalDate date) {
        EntityManager em = getEntityManager();
        try {
            var query = getQueryFactory(em)
                    .selectFrom(qSale)
                    .where(qSale.date.eq(date));

            return query.fetch();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Sale> findByDateRange(LocalDate from, LocalDate to) {
        EntityManager em = getEntityManager();
        try {
            var query = getQueryFactory(em)
                    .selectFrom(qSale)
                    .where(qSale.date.between(from, to));

            return query.fetch();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Sale> findByUserAndDateRange(Long userId, LocalDate from, LocalDate to) {
        EntityManager em = getEntityManager();
        try {
            var query = getQueryFactory(em)
                    .selectFrom(qSale)
                    .where(
                            qSale.user.userId.eq(userId)
                                    .and(qSale.date.between(from, to))
                    );

            return query.fetch();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Sale> findFilteredPaged(Long userId, LocalDate from, LocalDate to, int limit, int offset) {

        EntityManager em = getEntityManager();
        try {
            var query = getQueryFactory(em)
                    .selectFrom(qSale);

            if (userId != null) {
                query.where(qSale.user.userId.eq(userId));
            }

            if (from != null && to != null) {
                query.where(qSale.date.between(from, to));
            }

            return query.orderBy(qSale.date.desc(), qSale.id.desc())
                    .limit(limit)
                    .offset(offset)
                    .fetch();
        } finally {
            em.close();
        }
    }

    @Override
    public long countFiltered(Long userId, LocalDate from, LocalDate to) {

        EntityManager em = getEntityManager();
        try {
            var query = getQueryFactory(em)
                    .select(qSale.count())
                    .from(qSale);

            if (userId != null) {
                query.where(qSale.user.userId.eq(userId));
            }

            if (from != null && to != null) {
                query.where(qSale.date.between(from, to));
            }

            return query.fetchOne();
        } finally {
            em.close();
        }
    }



}
