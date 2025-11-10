package hu.flamingo.app.controller;

import hu.flamingo.app.model.User;
import hu.flamingo.app.repository.impl.UserRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

public class UserController {

    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> colName;
    @FXML private Button btnAdd;
    @FXML private Button btnRemove;
    @FXML private Button btnRefresh;

    private final UserRepository repo = new UserRepository();
    private final ObservableList<User> users = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupColumns();
        loadData();
        setupButtons();
    }

    private void setupColumns() {
        colName.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getLastName() + " " + data.getValue().getFirstName()
                ));
    }


    private void loadData() {
        users.setAll(repo.findAll());
        userTable.setItems(users);
    }

    private void setupButtons() {
        btnAdd.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/hu/flamingo/app/view/UserAddDialog.fxml"));
                DialogPane pane = loader.load();

                UserAddDialogController dialogController = loader.getController();
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(pane);
                dialog.setTitle("Új dolgozó");

                dialog.showAndWait().ifPresent(result -> {
                    if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                        User newUser = dialogController.getNewUser();
                        if (newUser != null) {
                            users.add(newUser);
                        }
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnRemove.setOnAction(e -> deleteSelectedUser());
        btnRefresh.setOnAction(e -> loadData());
    }


    private void showAddDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Új dolgozó");
        dialog.setHeaderText("Adj meg egy nevet (Vezetéknév Keresztnév):");
        dialog.setContentText("Név:");

        dialog.showAndWait().ifPresent(fullName -> {
            String[] parts = fullName.trim().split(" ");
            if (parts.length >= 2) {
                User user = new User();
                user.setLastName(parts[0]);
                user.setFirstName(parts[1]);
                user.setActive(true);
                repo.save(user);
                loadData();
            } else {
                new Alert(Alert.AlertType.WARNING, "Adj meg legalább két nevet!").showAndWait();
            }
        });
    }


    private void deleteSelectedUser() {
        User selected = userTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            repo.delete(selected.getUserId());
            users.remove(selected);
        } else {
            new Alert(Alert.AlertType.WARNING, "Nincs kijelölt dolgozó!").showAndWait();
        }
    }
}
