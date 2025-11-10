package hu.flamingo.app.controller;

import hu.flamingo.app.model.User;
import hu.flamingo.app.repository.impl.UserRepository;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UserController {

    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> colName;
    @FXML private TableColumn<User, Boolean> colActive;

    @FXML private Button btnAdd;
    @FXML private Button btnRemove;
    @FXML private Button btnRefresh;

    private final UserRepository repo = new UserRepository();
    private final ObservableList<User> users = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        colName.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getFirstName() + " " + data.getValue().getLastName()));
        colActive.setCellValueFactory(data ->
                new SimpleBooleanProperty(data.getValue().isActive()));


        loadData();
    }

    private void loadData() {
        users.setAll(repo.findAll());
        userTable.setItems(users);
    }


}
