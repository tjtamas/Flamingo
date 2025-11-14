package hu.flamingo.app.controller;

import hu.flamingo.app.config.Config;
import hu.flamingo.app.model.Product;
import hu.flamingo.app.model.Sale;
import hu.flamingo.app.model.User;
import hu.flamingo.app.service.ProductService;
import hu.flamingo.app.service.SaleService;
import hu.flamingo.app.service.UserService;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;
import java.util.List;

public class SalesController {

    // ---- FXML elemek ----
    @FXML private TableView<Sale> salesTable;

    @FXML private TableColumn<Sale, LocalDate> colDate;
    @FXML private TableColumn<Sale, String> colUser;
    @FXML private TableColumn<Sale, String> colMt;
    @FXML private TableColumn<Sale, String> colMsisdn;
    @FXML private TableColumn<Sale, String> colSegment;

    @FXML private TableColumn<Sale, String> colMobilHang;
    @FXML private TableColumn<Sale, String> colMobilAdat;
    @FXML private TableColumn<Sale, String> colVezNet;
    @FXML private TableColumn<Sale, String> colVezTv;
    @FXML private TableColumn<Sale, String> colVezHang;

    @FXML private TableColumn<Sale, Integer> colTotalVbs;

    // Szűrők
    @FXML private DatePicker dateFrom;
    @FXML private DatePicker dateTo;
    @FXML private ComboBox<User> cbUserFilter;

    @FXML private Button btnFilter;
    @FXML private Button btnResetFilters;

    // Lapozás
    @FXML private Button btnPrevPage;
    @FXML private Button btnNextPage;
    @FXML private Label lblPageInfo;

    // CRUD
    @FXML private Button btnAddSale;
    @FXML private Button btnEditSale;
    @FXML private Button btnDeleteSale;

    // ---- Szervizek + adat ----
    private final SaleService saleService = new SaleService();
    private final UserService userService = new UserService();
    private final ProductService productService = new ProductService();

    private final ObservableList<Sale> sales = FXCollections.observableArrayList();

    // Lapozáshoz
    private static final int PAGE_SIZE = 50;
    private int currentPage = 0;
    private long totalCount = 0;

    // ---- Inicializálás ----
    @FXML
    public void initialize() {
        setupColumns();
        setupFilters();
        setupTableSelection();
        setupPagination();
        loadPage(0);
    }



    // ---- Oszlopok beállítása ----
    private void setupColumns() {
        colDate.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDate()));
        colUser.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getUser().getLastName() + " " + data.getValue().getUser().getFirstName()
        ));
        colMt.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMtIdentifier()));
        colMsisdn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMsisdn()));
        colSegment.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getSegment().name()
        ));

        // Termékoszlopok – ha nincs termék → üres string
        colMobilHang.setCellValueFactory(data ->
                new SimpleStringProperty(getProductName(data.getValue().getMobilHang()))
        );
        colMobilAdat.setCellValueFactory(data ->
                new SimpleStringProperty(getProductName(data.getValue().getMobilAdat()))
        );
        colVezNet.setCellValueFactory(data ->
                new SimpleStringProperty(getProductName(data.getValue().getVezetekesNet()))
        );
        colVezTv.setCellValueFactory(data ->
                new SimpleStringProperty(getProductName(data.getValue().getVezetekesTv()))
        );
        colVezHang.setCellValueFactory(data ->
                new SimpleStringProperty(getProductName(data.getValue().getVezetekesHang()))
        );

        colTotalVbs.setCellValueFactory(data ->
                new SimpleObjectProperty<>(data.getValue().getTotalVbs())
        );

        salesTable.setItems(sales);
    }

    private String getProductName(Product p) {
        return p == null ? "" : p.getName();
    }

    // ---- Szűrők ----
    private void setupFilters() {
        cbUserFilter.setItems(FXCollections.observableArrayList(userService.findAllUsers()));

        btnFilter.setOnAction(e -> {
            currentPage = 0;
            loadPage(currentPage);
        });

        btnResetFilters.setOnAction(e -> {
            dateFrom.setValue(null);
            dateTo.setValue(null);
            cbUserFilter.setValue(null);
            currentPage = 0;
            loadPage(0);
        });
    }

    // ---- Lapozás ----
    private void setupPagination() {
        btnPrevPage.setOnAction(e -> {
            if (currentPage > 0) {
                currentPage--;
                loadPage(currentPage);
            }
        });

        btnNextPage.setOnAction(e -> {
            int maxPage = (int) Math.ceil((double) totalCount / PAGE_SIZE) - 1;
            if (currentPage < maxPage) {
                currentPage++;
                loadPage(currentPage);
            }
        });
    }

    private void loadPage(int page) {
        LocalDate from = dateFrom.getValue();
        LocalDate to = dateTo.getValue();
        User userFilter = cbUserFilter.getValue();

        // szűrt + lapozott lekérdezés
        List<Sale> result = saleService.findFilteredPaged(
                userFilter == null ? null : userFilter.getUserId(),
                from, to,
                PAGE_SIZE,
                page * PAGE_SIZE
        );

        // Összes elem számolása szűrés után
        totalCount = saleService.countFiltered(
                userFilter == null ? null : userFilter.getUserId(),
                from, to
        );

        sales.setAll(result);

        updatePageLabel();
    }

    private void updatePageLabel() {
        int maxPage = (int) Math.ceil((double) totalCount / PAGE_SIZE);
        if (maxPage == 0) maxPage = 1;

        lblPageInfo.setText((currentPage + 1) + " / " + maxPage + " oldal");
    }

    // ---- Kijelölés ----
    private void setupTableSelection() {
        btnEditSale.setDisable(true);
        btnDeleteSale.setDisable(true);

        salesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            boolean selected = newVal != null;
            btnEditSale.setDisable(!selected);
            btnDeleteSale.setDisable(!selected);
        });

        btnAddSale.setOnAction(e -> openEditDialog(null));
        btnEditSale.setOnAction(e -> {
            Sale selected = salesTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                openEditDialog(selected);
            }
        });
    }

    // ---- Új / szerkesztés popup ----
    private void openEditDialog(Sale saleToEdit) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Config.SALES_EDIT_DIALOG));
            DialogPane pane = loader.load();

            SalesEditDialogController dialogController = loader.getController();

            if (saleToEdit != null) {
                dialogController.setSale(saleToEdit);
            }

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.setTitle(saleToEdit == null ? "Új értékesítés" : "Értékesítés módosítása");

            dialog.showAndWait().ifPresent(result -> {
                if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    Sale updated = dialogController.getResult();

                    if (updated != null) {
                        saleService.saveSale(updated);
                        loadPage(currentPage);
                    }
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
