package hu.flamingo.app.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hu.flamingo.app.repository.interfaces.IGenericRepository;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;


public abstract class GenericRepository<T> implements IGenericRepository<T> {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("flamingoPU");

    private final Class<T> entityClass;

    protected GenericRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    protected JPAQueryFactory getQueryFactory(EntityManager em) {
        Supplier<EntityManager> supplier = () -> em;
        return new JPAQueryFactory(supplier);
    }



    @Override
    public void save(T entity) {
        executeInsideTransaction(em -> em.persist(entity));
    }

    @Override
    public Optional<T> findById(int id) {
        EntityManager em = getEntityManager();
        try {
            return Optional.ofNullable(em.find(entityClass, id));
        } finally {
            em.close();
        }
    }


    @Override
    public List<T> findAll() {
        EntityManager em = getEntityManager();
        JPAQueryFactory queryFactory = getQueryFactory(em);

        try {

            return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(T entity) {
        executeInsideTransaction(em -> em.merge(entity));
    }

    @Override
    public void delete(int id) {
        executeInsideTransaction(em -> {
            T entity = em.find(entityClass, id);
            if (entity != null) em.remove(entity);
        });
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            action.accept(em);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
