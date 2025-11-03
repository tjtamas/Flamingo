package hu.flamingo.app.flamingo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    public static final String DB_PATH = "data/";
    private static final String DB_FILE_NAME = "flamingo_data.sqlite";
    private static final String DB_URL = "jdbc:sqlite:" + DB_PATH + DB_FILE_NAME;

    public static Connection getConnection() throws SQLException {

        java.io.File dbPathDir = new java.io.File(DB_PATH);
        if (!dbPathDir.exists()) {
            boolean created = dbPathDir.mkdirs();
            if (created) {
                System.out.println("A 'data' mappa sikeresen létrehozva.");
            } else {
                System.err.println("HIBA: Nem sikerült létrehozni a 'data' mappát!");
            }
        }
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Nem található az SQLite JDBC driver: " + e.getMessage());
            throw new SQLException("SQLite driver hiba. Ellenőrizze a pom.xml-t!");
        }

        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {

        // --- 1. TÁBLA: USERS
        String sqlUsers = "CREATE TABLE IF NOT EXISTS USERS (\n"
                + " user_id INTEGER PRIMARY KEY,\n"
                + " last_name TEXT NOT NULL,\n"
                + " first_name TEXT NOT NULL,\n"
                + " is_active BOOLEAN DEFAULT 1\n"
                + ");";

        // --- 2. TÁBLA: SEGMENTS
        String sqlSegments = "CREATE TABLE IF NOT EXISTS SEGMENTS (\n"
                + " segment_id INTEGER PRIMARY KEY,\n"
                + " segment_name TEXT NOT NULL UNIQUE\n"
                + ");";

        // --- 3. TÁBLA: PRODUCTS
        String sqlProducts = "CREATE TABLE IF NOT EXISTS PRODUCTS (\n"
                + " product_id INTEGER PRIMARY KEY,\n"
                + " product_name TEXT NOT NULL,\n"
                + " vbs_value REAL NOT NULL,\n"
                + " category TEXT NOT NULL,\n"
                + " segment_id INTEGER NOT NULL,\n"
                + " FOREIGN KEY (segment_id) REFERENCES SEGMENTS(segment_id)\n"
                + ");";

        // --- 4. TÁBLA: TRANSACTIONS
        String sqlTransactions = "CREATE TABLE IF NOT EXISTS TRANSACTIONS (\n"
                + " transaction_id INTEGER PRIMARY KEY,\n"
                + " user_id INTEGER NOT NULL,\n"
                + " transaction_date TEXT NOT NULL,\n"
                + " mt_id TEXT,\n"
                + " msisdn TEXT,\n"
                + " segment_id INTEGER NOT NULL,\n"
                + " loyalty_value INTEGER NOT NULL DEFAULT 0,\n"
                + " telekom_basket BOOLEAN DEFAULT 0,\n"
                + " credit_card BOOLEAN DEFAULT 0,\n"
                + " score_digital INTEGER DEFAULT 0,\n"
                + " score_noncore INTEGER DEFAULT 0,\n"
                + " score_quality INTEGER DEFAULT 0,\n"
                + " FOREIGN KEY (user_id) REFERENCES USERS(user_id),\n"
                + " FOREIGN KEY (segment_id) REFERENCES SEGMENTS(segment_id)\n"
                + ");";

        // --- 5. TÁBLA: TRANSACTION_ITEMS
        String sqlTransactionItems = "CREATE TABLE IF NOT EXISTS TRANSACTION_ITEMS (\n"
                + " item_id INTEGER PRIMARY KEY,\n"
                + " transaction_id INTEGER NOT NULL,\n"
                + " product_id INTEGER NOT NULL,\n"
                + " FOREIGN KEY (transaction_id) REFERENCES TRANSACTIONS(transaction_id),\n"
                + " FOREIGN KEY (product_id) REFERENCES PRODUCTS(product_id)\n"
                + ");";


        String sqlFillSegments = "INSERT OR IGNORE INTO SEGMENTS (segment_id, segment_name) VALUES "
                + "(1, 'Lakossági'), "
                + "(2, 'Üzleti');";



        try (java.sql.Connection conn = getConnection();
             java.sql.Statement stmt = conn.createStatement()) {


            stmt.execute(sqlUsers);
            stmt.execute(sqlSegments);
            stmt.execute(sqlProducts);
            stmt.execute(sqlTransactions);
            stmt.execute(sqlTransactionItems);


            stmt.execute(sqlFillSegments);

            System.out.println("ADATBÁZIS SÉMA (5 tábla, VÉGLEGES) sikeresen inicializálva és feltöltve!");

        } catch (java.sql.SQLException e) {
            System.err.println("Adatbázis séma hiba: " + e.getMessage());
        }
    }

    public static void seedSampleData() {
        System.out.println("Minta adatok feltöltése (Users, Products)...");

        // --- 1. USERS FELTÖLTÉSE
        String sqlFillUsers = "INSERT OR IGNORE INTO USERS (last_name, first_name) VALUES "
                + "('Apor', 'Zsuzsanna'), "
                + "('Balogh', 'Máté'), "
                + "('Dobrocsi', 'Roland'), "
                + "('Fekete', 'Gergely'), "
                + "('Görög', 'Mihály Attila'), "
                + "('Hanczikné Faggyas', 'Alexandra'), "
                + "('Hucka', 'Dominik'), "
                + "('Huszti', 'Szabolcs Károly'), "
                + "('Lajtai', 'Emilia'), "
                + "('Morvai', 'Richárd'), "
                + "('Német', 'Márk'), "
                + "('Szilágyi', 'Flóra'), "
                + "('Tóth', 'Gábor'), "
                + "('Valaczkai', 'János'), "
                + "('Virsinger', 'Zsolt');";



        try (java.sql.Connection conn = getConnection();
             java.sql.Statement stmt = conn.createStatement()) {


            stmt.execute(sqlFillUsers);


            System.out.println("Minta adatok (Users) sikeresen feltöltve!");

        } catch (java.sql.SQLException e) {
            System.err.println("Minta adat feltöltési hiba: " + e.getMessage());
        }
    }
}