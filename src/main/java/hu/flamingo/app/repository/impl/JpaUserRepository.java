package hu.flamingo.app.repository.impl;

import hu.flamingo.app.model.User;
import hu.flamingo.app.repository.interfaces.IUserRepository;
import jakarta.persistence.EntityManager;
import java.util.List;

public class JpaUserRepository extends GenericJpaRepository<User> implements IUserRepository {

    public JpaUserRepository() {
        super(User.class);
    }

    @Override
    public List<User> findAllActive() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT u FROM User u WHERE u.isActive = true ORDER BY u.lastName, u.firstName",
                    User.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    private EntityManager getEntityManager() {
        return jakarta.persistence.Persistence.createEntityManagerFactory("flamingoPU").createEntityManager();
    }
}
