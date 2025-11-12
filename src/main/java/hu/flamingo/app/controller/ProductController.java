package hu.flamingo.app.controller;

import hu.flamingo.app.config.Config;
import hu.flamingo.app.model.Product;
import hu.flamingo.app.model.Segment;
import hu.flamingo.app.service.ProductService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductController {

    // ---- GUI elemek ----
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, String> colName;
    @FXML private TableColumn<Product, String> colCategory;
    @FXML private TableColumn<Product, Segment> colSegment;
    @FXML private TableColumn<Product, Double> colVbsValue;

    @FXML private Button btnAdd;
    @FXML private Button btnRemove;
    @FXML private Button btnRefresh;
    @FXML private Button btnEdit;

    // Szűrőgombok (FXML-ben létrehozva lesznek)
    @FXML private Button btnLakossagi;
    @FXML private Button btnUzleti;
    @FXML private Button btnMindSegment;
    @FXML private Button btnTv;
    @FXML private Button btnNet;
    @FXML private Button btnTelefon;
    @FXML private Button btnMobilHang;
    @FXML private Button btnMobilAdat;
    @FXML private Button btnMindCategory;

    // ---- Szerviz és adat ----
    private final ProductService service = new ProductService();
    private final ObservableList<Product> products = FXCollections.observableArrayList();

    // Aktív szűrők
    private Segment selectedSegment = null; // null = Mind
    private final Set<String> selectedCategories = new HashSet<>();

    // ---- Inicializálás ----
    @FXML
    public void initialize() {
        setupColumns();
        setupButtons();
        setupFilters();
        loadProducts();
        setupTableSelection();
    }

    // ---- Táblázat oszlopok ----
    private void setupColumns() {
        colName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        colCategory.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategory()));
        colSegment.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getSegment()));
        colVbsValue.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getVbsValue()));

        colVbsValue.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                } else {
                    setText(String.format("%,.0f Ft", value).replace(",", " "));
                    setStyle("-fx-alignment: CENTER-RIGHT; -fx-font-weight: bold;");
                }
            }
        });

        productTable.setItems(products);
    }

    // ---- CRUD gombok ----
    private void setupButtons() {
        btnAdd.setOnAction(e -> showAddDialog());
        btnRemove.setOnAction(e -> deleteSelectedProduct());
        btnRefresh.setOnAction(e -> resetFiltersAndReload());
        btnEdit.setOnAction(e -> showEditDialog());
    }

    // ---- Szűrőgombok beállítása ----
    private void setupFilters() {
        // Szegmens gombok
        btnLakossagi.setOnAction(e -> toggleSegment(Segment.LAKOSSAGI, btnLakossagi));
        btnUzleti.setOnAction(e -> toggleSegment(Segment.UZLETI, btnUzleti));
        btnMindSegment.setOnAction(e -> resetSegmentButtons());

        // Kategória gombok
        Map<Button, String> categoryMap = Map.of(
                btnTv, "Fix_TV",
                btnNet, "Fix_Net",
                btnTelefon, "Fix_Voice",
                btnMobilHang, "Mobil_Voice",
                btnMobilAdat, "Mobil_Net"
        );
        categoryMap.forEach((btn, cat) -> btn.setOnAction(e -> toggleCategory(cat, btn)));

        btnMindCategory.setOnAction(e -> resetCategoryButtons());
    }

    // ---- Szegmens logika ----
    private void toggleSegment(Segment seg, Button button) {
        if (selectedSegment == seg) {
            selectedSegment = null;
            button.setStyle(null);
        } else {
            selectedSegment = seg;
            btnLakossagi.setStyle(null);
            btnUzleti.setStyle(null);
            button.setStyle("-fx-background-color: #90ee90;");
        }
        filterProducts();
    }

    private void resetSegmentButtons() {
        selectedSegment = null;
        btnLakossagi.setStyle(null);
        btnUzleti.setStyle(null);
        filterProducts();
    }

    // ---- Kategória logika ----
    private void toggleCategory(String category, Button button) {
        if (selectedCategories.contains(category)) {
            selectedCategories.remove(category);
            button.setStyle(null);
        } else {
            selectedCategories.add(category);
            button.setStyle("-fx-background-color: #90ee90;");
        }
        filterProducts();
    }

    private void resetCategoryButtons() {
        selectedCategories.clear();
        btnTv.setStyle(null);
        btnNet.setStyle(null);
        btnTelefon.setStyle(null);
        btnMobilHang.setStyle(null);
        btnMobilAdat.setStyle(null);
        filterProducts();
    }

    // ---- Termékek betöltése + szűrés ----
    private void loadProducts() {
        List<Product> all = service.findAllProducts();
        products.setAll(all);
    }

    private void filterProducts() {
        List<Product> all = service.findAllProducts();

        Stream<Product> stream = all.stream();
        if (selectedSegment != null) {
            stream = stream.filter(p -> p.getSegment() == selectedSegment);
        }
        if (!selectedCategories.isEmpty()) {
            stream = stream.filter(p -> selectedCategories.contains(p.getCategory()));
        }

        products.setAll(stream.collect(Collectors.toList()));
    }

    private void resetFiltersAndReload() {
        resetSegmentButtons();
        resetCategoryButtons();
        loadProducts();
    }

    // ---- CRUD funkciók ----
    private void showAddDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Config.PRODUCT_EDIT_DIALOG));
            DialogPane pane = loader.load();

            ProductEditDialogController dialogController = loader.getController();


            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.setTitle("Új termék hozzáadása");

            dialog.showAndWait().ifPresent(result -> {
                if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    Product newProduct = dialogController.createNewProduct();
                    if (newProduct != null) {
                        service.addProduct(newProduct);
                        productTable.getItems().add(newProduct);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void deleteSelectedProduct() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.deleteProduct(selected);
            filterProducts();
        } else {
            new Alert(Alert.AlertType.WARNING, "Nincs kijelölt termék!").showAndWait();
        }
    }

    private void showEditDialog() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Válassz ki egy terméket a módosításhoz!").showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Config.PRODUCT_EDIT_DIALOG));
            DialogPane pane = loader.load();

            ProductEditDialogController dialogController = loader.getController();
            dialogController.setProduct(selected);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.setTitle("Termék módosítása");

            dialog.showAndWait().ifPresent(result -> {
                if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    Product updated = dialogController.getUpdatedProduct();
                    if (updated != null) {
                        service.updateProduct(updated);
                        productTable.refresh();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // ---- Gombok engedélyezése kijelölés szerint ----
    private void setupTableSelection() {
        btnRemove.setDisable(true);
        btnEdit.setDisable(true);

        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            boolean hasSelection = newSel != null;
            btnRemove.setDisable(!hasSelection);
            btnEdit.setDisable(!hasSelection);
        });
    }
}
