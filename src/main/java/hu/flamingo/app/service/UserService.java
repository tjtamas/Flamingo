package hu.flamingo.app.service;

import hu.flamingo.app.model.User;
import hu.flamingo.app.repository.impl.UserRepository;

import java.util.List;

public class UserService {

    private final UserRepository repo = new UserRepository();

    public List<User> findAllUsers() {
        return repo.findAll();
    }

    public List<User> findAllActiveUsers() {
        return repo.findAllActive();
    }

    public void saveUser(User user) {
        repo.save(user);
    }

    public void updateUser(User user) {
        repo.update(user);
    }

    public void deleteUser(User user) {
        repo.delete(user.getUserId());
    }
}
