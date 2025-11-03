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

        System.out.println("--- ÜZLETI TERMÉKEK LEKÉRDEZÉSE (DAO TESZT) ---");
        System.out.println("Kérem az összes 'MOBIL_HANG' terméket az 'Üzleti' (ID=2) szegmensből:");

        ProductDao productDaoTeszter = new ProductDao();


        List<Product> uzletiTermekek = productDaoTeszter.getProductsByCategoryAndSegment("MOBIL_HANG", 2);

        if (uzletiTermekek.isEmpty()) {
            System.out.println("HIBA: Nem található 'MOBIL_HANG' termék az 'Üzleti' szegmensben!");
        } else {
            System.out.println("Sikeresen lekérdezve " + uzletiTermekek.size() + " 'MOBIL_HANG' (Üzleti) termék:");
            for (Product product : uzletiTermekek) {
                System.out.println(product);
            }
        }



        launch(args);
    }


}
