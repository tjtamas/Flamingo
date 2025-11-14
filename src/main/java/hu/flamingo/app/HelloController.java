package hu.flamingo.app;

import hu.flamingo.app.config.Config;
import hu.flamingo.app.service.FXMLLoaderService;
import hu.flamingo.app.service.NavigationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HelloController {

    @FXML private BorderPane root;
    @FXML private AnchorPane mainContentArea;

    // --- navigációs gombok ---
    @FXML private Button navButtonErtesites;
    @FXML private Button navButtonRiport;
    @FXML private Button navButtonModositas; // ez lesz a Termékek
    @FXML private Button navButtonDolgozok;

    private final NavigationService navService = new NavigationService();

    @FXML
    public void initialize() {
        // stílusosztály minden gombhoz
        navButtonErtesites.getStyleClass().add("nav-button");
        navButtonRiport.getStyleClass().add("nav-button");
        navButtonModositas.getStyleClass().add("nav-button");
        navButtonDolgozok.getStyleClass().add("nav-button");

        // --- események ---
        navButtonDolgozok.setOnAction(e -> {
            loadView(Config.USER_VIEW);
            navService.setActive(navButtonDolgozok);
        });

        navButtonModositas.setOnAction(e -> { // "Termékek"
            loadView(Config.PRODUCT_VIEW);
            navService.setActive(navButtonModositas);
        });

        navButtonErtesites.setOnAction(e -> {
            loadView(Config.SALES_VIEW);
            navService.setActive(navButtonErtesites);
        });


        navButtonRiport.setOnAction(e -> {
            // később ide jön a riport modul
            System.out.println("Riport modul fejlesztés alatt...");
        });
    }

    // --- központi nézetbetöltő ---
    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = FXMLLoaderService.load(fxmlPath);
            Parent content = loader.load();
            mainContentArea.getChildren().setAll(content);
            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
