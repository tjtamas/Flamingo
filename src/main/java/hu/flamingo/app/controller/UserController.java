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
    @FXML private Button btnEdit;

    private final UserRepository repo = new UserRepository();

    @FXML
    public void initialize() {
        setupColumns();
        loadData();
        setupButtons();
    }

    private void setupColumns() {
        colName.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getLastName() + " " + data.getValue().getFirstName()));
    }

    private void loadData() {
        userTable.setItems(FXCollections.observableArrayList(repo.findAll()));
    }

    private void setupButtons() {
        btnAdd.setOnAction(e -> showAddDialog());
        btnRemove.setOnAction(e -> deleteSelectedUser());
        btnRefresh.setOnAction(e -> loadData());
        btnEdit.setOnAction(e -> showEditDialog());
    }

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

    private void deleteSelectedUser() {
        User selected = userTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            repo.delete(selected.getUserId());
            userTable.getItems().remove(selected);
        } else {
            new Alert(Alert.AlertType.WARNING, "Nincs kijelölt dolgozó!").showAndWait();
        }
    }

    private void showEditDialog() {
        User selected = userTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Válassz ki egy dolgozót a módosításhoz!").showAndWait();
            return;
        }

        TextInputDialog dialog = new TextInputDialog(selected.getLastName() + " " + selected.getFirstName());
        dialog.setTitle("Dolgozó módosítása");
        dialog.setHeaderText("Szerkeszd a dolgozó nevét (vezetéknév keresztnév):");
        dialog.setContentText("Új név:");

        dialog.showAndWait().ifPresent(fullName -> {
            String[] parts = fullName.trim().split(" ");
            if (parts.length >= 2) {
                selected.setLastName(parts[0]);
                selected.setFirstName(parts[1]);
                repo.save(selected); // update in DB
                loadData();
            } else {
                new Alert(Alert.AlertType.WARNING, "Kérlek, add meg a teljes nevet (vezetéknév és keresztnév)!").showAndWait();
            }
        });
    }

}
