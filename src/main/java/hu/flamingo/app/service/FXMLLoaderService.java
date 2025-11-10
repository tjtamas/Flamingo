package hu.flamingo.app.service;

import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.net.URL;

import hu.flamingo.app.config.Config;

public class FXMLLoaderService {

    public static FXMLLoader load(String fxmlPath) throws IOException {
        URL resource = FXMLLoaderService.class.getResource(fxmlPath);
        if (resource == null) {
            throw new IOException("❌ FXML nem található: " + fxmlPath);
        }
        FXMLLoader loader = new FXMLLoader(resource);
        return loader;
    }


    public static FXMLLoader loadUserAddDialog() throws IOException {
        return load("/view/UserAddDialog.fxml");
    }
}
