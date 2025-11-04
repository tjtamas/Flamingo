package hu.flamingo.app;

import hu.flamingo.app.dao.ProductDao;
import hu.flamingo.app.model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
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
