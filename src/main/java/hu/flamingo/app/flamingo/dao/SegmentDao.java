package hu.flamingo.app.flamingo.dao;

import hu.flamingo.app.flamingo.db.DatabaseManager;
import hu.flamingo.app.flamingo.model.Segment; // <-- A Segment MODEL importálása

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SegmentDao {

    public List<Segment> getAllSegments() {
        List<Segment> segments = new ArrayList<>();

        String sql = "SELECT segment_id, segment_name FROM SEGMENTS ORDER BY segment_name";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("segment_id");
                String name = rs.getString("segment_name");
                Segment segment = new Segment(id, name);
                segments.add(segment);
            }

        } catch (SQLException e) {
            System.err.println("Hiba a szegmensek lekérdezésekor: " + e.getMessage());
        }

        return segments;
    }
}