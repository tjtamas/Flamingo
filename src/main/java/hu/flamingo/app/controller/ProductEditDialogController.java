package hu.flamingo.app.controller;

import hu.flamingo.app.model.Product;
import hu.flamingo.app.model.Segment;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ProductEditDialogController {

    @FXML private TextField txtName;
    @FXML private ComboBox<String> cmbCategory;
    @FXML private ComboBox<Segment> cmbSegment;
    @FXML private TextField txtVbsValue;

    private Product product;

    @FXML
    public void initialize() {
        cmbCategory.getItems().addAll("FIX_TV", "FIX_NET", "FIX_VOICE", "MOBIL_VOICE", "MOBIL_NET");
        cmbSegment.getItems().addAll(Segment.LAKOSSAGI, Segment.UZLETI);
    }

    public Product createNewProduct() {
        String name = txtName.getText().trim();
        String category = cmbCategory.getValue();
        Segment segment = cmbSegment.getValue();

        double vbs = 0;
        try {
            vbs = Double.parseDouble(txtVbsValue.getText().trim());
        } catch (Exception ignored) {}

        return new Product(name, vbs, category, segment);
    }


    public void setProduct(Product product) {
        this.product = product;

        txtName.setText(product.getName());
        cmbCategory.setValue(product.getCategory());
        cmbSegment.setValue(product.getSegment());
        txtVbsValue.setText(String.valueOf((int) product.getVbsValue()));
    }

    public Product getUpdatedProduct() {
        if (product == null) return null;

        product.setName(txtName.getText().trim());
        product.setCategory(cmbCategory.getValue());
        product.setSegment(cmbSegment.getValue());

        try {
            product.setVbsValue(Double.parseDouble(txtVbsValue.getText().replace(" ", "")));
        } catch (NumberFormatException e) {
            product.setVbsValue(0);
        }

        return product;
    }
}
