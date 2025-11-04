package hu.flamingo.app.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hu.flamingo.app.model.User;
import hu.flamingo.app.repository.interfaces.IUserRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

import static hu.flamingo.app.model.QUser.user;

public class UserRepository extends GenericRepository<User> implements IUserRepository {

    public UserRepository() {
        super(User.class);
    }

    @Override
    public List<User> findAllActive() {
        EntityManager em = getEntityManager();
        try {
            JPAQueryFactory queryFactory = getQueryFactory(em);

            return queryFactory
                    .selectFrom(user)
                    .where(user.isActive.isTrue())
                    .orderBy(user.lastName.asc(), user.firstName.asc())
                    .fetch();

        } finally {
            em.close();
        }
    }
}
