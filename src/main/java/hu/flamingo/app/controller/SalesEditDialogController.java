package hu.flamingo.app.controller;

import hu.flamingo.app.model.*;
import hu.flamingo.app.service.ProductService;
import hu.flamingo.app.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class SalesEditDialogController {

    @FXML private DatePicker dpDate;
    @FXML private ComboBox<User> cbUser;

    @FXML private TextField txtMt;
    @FXML private TextField txtMsisdn;

    @FXML private ComboBox<Segment> cbSegment;

    @FXML private ComboBox<Product> cbMobilHang;
    @FXML private ComboBox<Product> cbMobilAdat;
    @FXML private ComboBox<Product> cbVezNet;
    @FXML private ComboBox<Product> cbVezTv;
    @FXML private ComboBox<Product> cbVezHang;

    @FXML private TextField txtDigitalis;
    @FXML private TextField txtNonCore;
    @FXML private TextField txtQuality;

    private final UserService userService = new UserService();
    private final ProductService productService = new ProductService();

    private Sale editingSale = null;

    @FXML
    public void initialize() {
        loadUsers();
        loadSegments();
        loadProducts();
    }

    private void loadUsers() {
        cbUser.getItems().addAll(userService.findAllUsers());
    }

    private void loadSegments() {
        cbSegment.getItems().addAll(Segment.values());
    }

    /** Betöltjük a termékeket kategória szerint */
    private void loadProducts() {
        cbMobilHang.getItems().addAll(productService.findByCategory("Mobil_Voice"));
        cbMobilAdat.getItems().addAll(productService.findByCategory("Mobil_Net"));
        cbVezNet.getItems().addAll(productService.findByCategory("Fix_Net"));
        cbVezTv.getItems().addAll(productService.findByCategory("Fix_TV"));
        cbVezHang.getItems().addAll(productService.findByCategory("Fix_Voice"));
    }

    /** Ha módosítunk, előre kitöltjük az adatokat */
    public void setSale(Sale sale) {
        editingSale = sale;

        dpDate.setValue(sale.getDate());
        cbUser.setValue(sale.getUser());

        txtMt.setText(sale.getMtIdentifier());
        txtMsisdn.setText(sale.getMsisdn());
        cbSegment.setValue(sale.getSegment());

        cbMobilHang.setValue(sale.getMobilHang());
        cbMobilAdat.setValue(sale.getMobilAdat());
        cbVezNet.setValue(sale.getVezetekesNet());
        cbVezTv.setValue(sale.getVezetekesTv());
        cbVezHang.setValue(sale.getVezetekesHang());

        txtDigitalis.setText(sale.getDigitalis() != null ? sale.getDigitalis().toString() : "");
        txtNonCore.setText(sale.getNonCore() != null ? sale.getNonCore().toString() : "");
        txtQuality.setText(sale.getMinoseg() != null ? sale.getMinoseg().toString() : "");
    }

    /** Mentés: a controller visszaadja az új vagy módosított Sale-t */
    public Sale getResult() {
        if (editingSale == null) {
            editingSale = new Sale();
        }

        editingSale.setDate(dpDate.getValue());
        editingSale.setUser(cbUser.getValue());

        editingSale.setMtIdentifier(txtMt.getText());
        editingSale.setMsisdn(txtMsisdn.getText());
        editingSale.setSegment(cbSegment.getValue());

        editingSale.setMobilHang(cbMobilHang.getValue());
        editingSale.setMobilAdat(cbMobilAdat.getValue());
        editingSale.setVezetekesNet(cbVezNet.getValue());
        editingSale.setVezetekesTv(cbVezTv.getValue());
        editingSale.setVezetekesHang(cbVezHang.getValue());

        editingSale.setDigitalis(parseIntOrNull(txtDigitalis.getText()));
        editingSale.setNonCore(parseIntOrNull(txtNonCore.getText()));
        editingSale.setMinoseg(parseIntOrNull(txtQuality.getText()));

        return editingSale;
    }

    private Integer parseIntOrNull(String text) {
        try {
            if (text == null || text.isBlank()) return null;
            return Integer.parseInt(text);
        } catch (Exception e) {
            return null;
        }
    }
}
