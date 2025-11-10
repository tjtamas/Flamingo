package hu.flamingo.app.service;

import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class FXMLLoaderService {
    public static FXMLLoader load(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLLoaderService.class.getResource(fxmlPath));
        return loader;
    }
}
