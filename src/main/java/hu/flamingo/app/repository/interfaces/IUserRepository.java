package hu.flamingo.app.repository.interfaces;

import hu.flamingo.app.model.User;
import java.util.List;

public interface IUserRepository extends IGenericRepository<User> {
    List<User> findAllActive();
}
