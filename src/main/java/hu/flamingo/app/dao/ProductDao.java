package hu.flamingo.app.dao;

import hu.flamingo.app.db.DatabaseManager;
import hu.flamingo.app.model.Product; // <-- A Product MODEL importálása

import java.sql.*; // A PreparedStatement miatt a '*' import egyszerűbb
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) osztály.
 * Felelőssége: Csak a PRODUCTS tábla adatbázis-műveletei.
 */
public class ProductDao {

    public List<Product> getProductsByCategoryAndSegment(String category, int segmentId) {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT product_id, product_name, vbs_value, category, segment_id "
                + "FROM PRODUCTS "
                + "WHERE category = ? AND segment_id = ? "
                + "ORDER BY product_name";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, category);
            pstmt.setInt(2, segmentId);


            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {

                    int id = rs.getInt("product_id");
                    String name = rs.getString("product_name");
                    double vbs = rs.getDouble("vbs_value");
                    String cat = rs.getString("category");
                    int segId = rs.getInt("segment_id");

                    Product product = new Product(id, name, vbs, cat, segId);


                    products.add(product);
                }
            }

        } catch (SQLException e) {
            System.err.println("Hiba a termékek lekérdezésekor: " + e.getMessage());
        }

        return products;
    }
}