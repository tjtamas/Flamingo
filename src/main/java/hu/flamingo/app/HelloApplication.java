package hu.flamingo.app;
import hu.flamingo.app.config.Config;
import hu.flamingo.app.db.DatabaseManager;
import hu.flamingo.app.db.seed.ProductSeederCSV;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        DatabaseManager.initializeDatabase();

        // --- egyszeri CSV-seed (csak ha szükséges) ---
        boolean seedNeeded = false; // Állítsd false-ra ha már feltöltötted!
        if (seedNeeded) {
            String csvPath = "data/csv/Product.csv";
            new ProductSeederCSV().importFromCSV(csvPath);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Config.MAIN_VIEW));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(Config.APP_TITLE);
        stage.setScene(scene);

        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
    }

    public static void main(String[] args) {


        launch(args);
    }


}
