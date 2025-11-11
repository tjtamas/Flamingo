package hu.flamingo.app.service;

import javafx.scene.control.Button;

public class NavigationService {

    private Button activeButton;

    public void setActive(Button button) {
        if (activeButton != null) {
            activeButton.getStyleClass().remove("active");
        }
        button.getStyleClass().add("active");
        activeButton = button;
    }
}
