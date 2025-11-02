package hu.flamingo.app.flamingo;

import hu.flamingo.app.flamingo.db.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Flamingo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("Adatbázis kapcsolat tesztelése...");
        try (Connection conn = DatabaseManager.getConnection()) {
            if (conn != null) {
                System.out.println("--- SIKER! ---");
                System.out.println("A 'flamingo_data.sqlite' fájl sikeresen létrehozva és a kapcsolat él.");
                System.out.println("A fájlt a 'data' mappában találja a projekt gyökerében.");
            }
        } catch (SQLException e) {
            System.err.println("--- HIBA! ---");
            System.err.println("Nem sikerült csatlakozni az adatbázishoz:");
            e.printStackTrace();
        }
        launch(args);
    }


}
