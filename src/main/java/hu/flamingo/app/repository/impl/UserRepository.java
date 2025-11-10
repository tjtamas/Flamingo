package hu.flamingo.app.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hu.flamingo.app.model.QUser;
import hu.flamingo.app.model.User;
import hu.flamingo.app.repository.interfaces.IUserRepository;
import jakarta.persistence.EntityManager;
import java.util.List;

public class UserRepository extends GenericRepository<User> implements IUserRepository {

    private static final QUser U = QUser.user;

    public UserRepository() {
        super(User.class);
    }

    @Override
    public List<User> findAllActive() {
        EntityManager em = getEntityManager();
        try {
            return createQueryFactory(em)
                    .selectFrom(U)
                    .where(U.isActive.isTrue())
                    .orderBy(U.lastName.asc(), U.firstName.asc())
                    .fetch();
        } finally {
            em.close();
        }
    }

    @Override
    public List<User> findAll() {
        EntityManager em = getEntityManager();
        try {
            return createQueryFactory(em)
                    .selectFrom(U)
                    .orderBy(U.lastName.asc(), U.firstName.asc())
                    .fetch();
        } finally {
            em.close();
        }
    }


    private JPAQueryFactory createQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }
}
