package hu.flamingo.app.dao;

import hu.flamingo.app.db.DatabaseManager;
import hu.flamingo.app.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) osztály.
 * Felelőssége: Csak a USERS tábla adatbázis-műveletei (lekérdezés, mentés, stb.)
 */
public class UserDao {


    public List<User> getAllActiveUsers() {

        List<User> users = new ArrayList<>();

        String sql = "SELECT user_id, last_name, first_name, is_active FROM USERS "
                + "WHERE is_active = 1 "
                + "ORDER BY last_name, first_name";


        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                int id = rs.getInt("user_id");
                String lastName = rs.getString("last_name");
                String firstName = rs.getString("first_name");
                boolean isActive = rs.getBoolean("is_active");

                User user = new User(id, lastName, firstName, isActive);


                users.add(user);
            }

        } catch (SQLException e) {
            System.err.println("Hiba a felhasználók lekérdezésekor: " + e.getMessage());
        }


        return users;
    }

}