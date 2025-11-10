package hu.flamingo.app.controller;

import hu.flamingo.app.model.User;
import hu.flamingo.app.repository.impl.UserRepository;
import hu.flamingo.app.service.FXMLLoaderService;
import hu.flamingo.app.config.Config;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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

    @FXML
    public void initialize() {
        setupColumns();
        loadData();
        setupButtons();
    }

    /** Táblázat oszlopok beállítása */
    private void setupColumns() {
        colName.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getLastName() + " " + data.getValue().getFirstName()));
    }

    /** Adatok betöltése az adatbázisból */
    private void loadData() {
        userTable.setItems(FXCollections.observableArrayList(repo.findAll()));
    }

    /** Gombok eseménykezelése */
    private void setupButtons() {
        btnAdd.setOnAction(e -> showAddDialog());
        btnRemove.setOnAction(e -> deleteSelectedUser());
        btnRefresh.setOnAction(e -> loadData());
    }

    /** Új dolgozó felvétele (szolgáltatás használata) */
    private void showAddDialog() {
        try {
            FXMLLoader loader = FXMLLoaderService.loadUserAddDialog();
            DialogPane pane = loader.load();

            UserAddDialogController dialogController = loader.getController();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.setTitle("Új dolgozó");

            dialog.showAndWait().ifPresent(result -> {
                if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    User newUser = dialogController.getNewUser();
                    if (newUser != null) {
                        userTable.getItems().add(newUser);
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** Kijelölt dolgozó törlése */
    private void deleteSelectedUser() {
        User selected = userTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            repo.delete(selected.getUserId());
            userTable.getItems().remove(selected);
        } else {
            new Alert(Alert.AlertType.WARNING, "Nincs kijelölt dolgozó!").showAndWait();
        }
    }
}
