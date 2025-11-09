package hu.flamingo.app;
import hu.flamingo.app.db.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DatabaseManager.initializeDatabase();

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/view/UserView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        stage.setTitle("Flamingo 🦩 – Dolgozók");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {


        launch(args);
    }


}
