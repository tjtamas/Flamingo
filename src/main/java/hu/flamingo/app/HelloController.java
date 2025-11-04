package hu.flamingo.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;


public class HelloController {

    @FXML
    private Button navButtonErtesites;
    @FXML
    private Button navButtonRiport;
    @FXML
    private Button navButtonModositas;


    @FXML
    private AnchorPane mainContentArea;
    @FXML
    public void initialize() {

        navButtonErtesites.setOnAction(event -> {
            System.out.println("Értékesítés nézet betöltése...");

        });


        navButtonRiport.setOnAction(event -> {
            System.out.println("Riport nézet betöltése...");

        });


        navButtonModositas.setOnAction(event -> {
            System.out.println("Adatkezelés nézet betöltése...");

        });
    }


    private void loadView(String fxmlFileName) {
        try {

            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlFileName));

            BorderPane view = loader.load();

            mainContentArea.getChildren().clear();

            mainContentArea.getChildren().add(view);

            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

        } catch (IOException e) {
            System.err.println("Hiba a nézet betöltésekor: " + fxmlFileName);
            e.printStackTrace();
        }
    }
}