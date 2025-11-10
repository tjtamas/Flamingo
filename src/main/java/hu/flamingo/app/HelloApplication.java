package hu.flamingo.app;
import hu.flamingo.app.config.Config;
import hu.flamingo.app.db.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        DatabaseManager.initializeDatabase();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Config.MAIN_VIEW));
        Scene scene = new Scene(fxmlLoader.load(), Config.MAIN_WINDOW_WIDTH, Config.MAIN_WINDOW_HEIGHT);
        stage.setTitle(Config.APP_TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {


        launch(args);
    }


}
