package hu.flamingo.app;

import hu.flamingo.app.config.Config;
import hu.flamingo.app.service.FXMLLoaderService;
import hu.flamingo.app.service.NavigationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import java.io.IOException;

public class HelloController {

    @FXML private BorderPane root;
    @FXML private Button navButtonDolgozok;
    private final NavigationService navService = new NavigationService();


    @FXML
    public void initialize() {
        navButtonDolgozok.getStyleClass().add("nav-button");

        navButtonDolgozok.setOnAction(e -> {
            loadView(Config.USER_VIEW);
            navService.setActive(navButtonDolgozok);
        });
    }


    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = FXMLLoaderService.load(fxmlPath);
            Parent content = loader.load();
            root.setCenter(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
