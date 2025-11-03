package hu.flamingo.app.flamingo;

import hu.flamingo.app.flamingo.dao.UserDao;
import hu.flamingo.app.flamingo.db.DatabaseManager;
import hu.flamingo.app.flamingo.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
        System.out.println("Alkalmazás indítása... Adatbázis séma ellenőrzése...");
        DatabaseManager.initializeDatabase();
        System.out.println("Adatbázis készen áll.");
        System.out.println("--- AKTÍV FELHASZNÁLÓK LEKÉRDEZÉSE (DAO TESZT) ---");
        UserDao userDao = new UserDao();
        List<User> aktivFelhasznalok = userDao.getAllActiveUsers();
        if (aktivFelhasznalok.isEmpty()) {
            System.out.println("A USERS tábla üres. (Ha ez hiba, futtasd a seedSampleData() metódust a feltöltéshez!)");
        } else {
            System.out.println("Sikeresen lekérdezve " + aktivFelhasznalok.size() + " aktív felhasználó:");

            for (User user : aktivFelhasznalok) {

                System.out.println(user);
            }
        }

        launch(args);
    }


}
