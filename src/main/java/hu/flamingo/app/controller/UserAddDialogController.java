package hu.flamingo.app.controller;

import hu.flamingo.app.model.User;
import hu.flamingo.app.repository.impl.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UserAddDialogController {

    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;

    private final UserRepository repo = new UserRepository();

    public User getNewUser() {
        String first = txtFirstName.getText().trim();
        String last = txtLastName.getText().trim();

        if (first.isEmpty() || last.isEmpty()) return null;

        User user = new User();
        user.setFirstName(first);
        user.setLastName(last);
        user.setActive(true);

        repo.save(user);
        return user;
    }
}
