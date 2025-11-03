package hu.flamingo.app.flamingo;

import hu.flamingo.app.flamingo.dao.ProductDao;
import hu.flamingo.app.flamingo.dao.SegmentDao;
import hu.flamingo.app.flamingo.dao.UserDao;
import hu.flamingo.app.flamingo.db.DatabaseManager;
import hu.flamingo.app.flamingo.model.Product;
import hu.flamingo.app.flamingo.model.Segment;
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
        System.out.println("--- SZEGMENSEK LEKÉRDEZÉSE (DAO TESZT) ---");
        SegmentDao segmentDao = new SegmentDao();
        List<Segment> szegmensek = segmentDao.getAllSegments();

        if (szegmensek.isEmpty()) {
            System.out.println("HIBA: A SEGMENTS tábla üres!");
        } else {
            System.out.println("Sikeresen lekérdezve " + szegmensek.size() + " szegmens:");
            for (Segment segment : szegmensek) {
                System.out.println(segment);
            }
        }


        System.out.println("--- NYERS TERMÉK ADATBÁZIS TESZT (DEBUG) ---");
        System.out.println("Az összes termék a 'PRODUCTS' táblából, szűrés nélkül:");

        String sqlNyers = "SELECT product_name, category, segment_id FROM PRODUCTS";

        try (java.sql.Connection conn = DatabaseManager.getConnection();
             java.sql.Statement stmt = conn.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(sqlNyers)) {

            int count = 0;
            while (rs.next()) {
                // Kiírjuk a három legfontosabb oszlopot
                System.out.println(
                        "NÉV: " + rs.getString("product_name") +
                                " | KATEGÓRIA: " + rs.getString("category") +
                                " | SZEGMENS_ID: " + rs.getInt("segment_id")
                );
                count++;
            }
            System.out.println("Összesen " + count + " termék található a táblában.");

        } catch (java.sql.SQLException e) {
            System.err.println("Hiba a nyers lekérdezés közben: " + e.getMessage());
        }
        // --- DEBUG TESZT VÉGE ---


        // 6. LÉPÉS: Alkalmazás indítása

        launch(args);
    }


}
