package hu.flamingo.app.flamingo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    public static final String DB_PATH = "data/";
    private static final String DB_FILE_NAME = "flamingo_data.sqlite";
    private static final String DB_URL = "jdbc:sqlite:" + DB_PATH + DB_FILE_NAME;

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Nem található az SQLite JDBC driver: " + e.getMessage());
            throw new SQLException("SQLite driver hiba. Ellenőrizze a pom.xml-t!");
        }

        return DriverManager.getConnection(DB_URL);
    }
}