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

    /**
     * FELTÖLTÉS MINTA TERMÉKEKKEL (SOLID)
     * Felelőssége: Csak a PRODUCTS tábla feltöltése minta adatokkal.
     */
    public static void seedSampleData() {
        System.out.println("Minta adatok feltöltése (Csak Üzleti Products)...");

        String sqlFillProducts = "INSERT OR IGNORE INTO PRODUCTS (product_name, vbs_value, category, segment_id) VALUES "


                + "('ÜzletiMobil_S', 3937, 'MOBIL_HANG', 2), "
                + "('ÜzletiMobil_M', 4826, 'MOBIL_HANG', 2), "
                + "('ÜzletiMobil_L', 10541, 'MOBIL_HANG', 2), "
                + "('ÜzletiMobil_XL', 15367, 'MOBIL_HANG', 2), "

                + "('Üzleti Adat 3GB', 2100, 'MOBIL_ADAT', 2), "
                + "('Üzleti Adat 8GB', 4625, 'MOBIL_ADAT', 2), "
                + "('Üzleti Adat 25', 7875, 'MOBIL_ADAT', 2), "
                + "('Üzleti Korlátlan Plusz', 14700, 'MOBIL_ADAT', 2), "
                + "('Üzleti Korlátlan Prémium', 16800, 'MOBIL_ADAT', 2), "
                + "('Üzleti Adat 5GB', 4694, 'MOBIL_ADAT', 2), "
                + "('Üzleti Adat 10GB', 7560, 'MOBIL_ADAT', 2), "
                + "('Üzleti Adat 50GB', 15036, 'MOBIL_ADAT', 2), "
                + "('Üzleti Adat 250GB', 23153, 'MOBIL_ADAT', 2), "


                + "('Üzleti Net Plusz', 10731, 'FIX_NET', 2), "
                + "('Üzleti Net 300', 11099, 'FIX_NET', 2), "
                + "('Üzleti Net 1000', 16107, 'FIX_NET', 2), "
                + "('Üzleti Net 1000 Nonstop', 17199, 'FIX_NET', 2), "
                + "('Üzleti Net 2000 Nonstop', 19268, 'FIX_NET', 2), "
                + "('Üzleti Net Max', 74939, 'FIX_NET', 2), "
                + "('Spec. Üzleti Net Plusz', 6426, 'FIX_NET', 2), "
                + "('Spec. Üzleti Net 300', 6794, 'FIX_NET', 2), "
                + "('Spec. Üzleti Net 1000', 11802, 'FIX_NET', 2), "
                + "('Spec. Üzleti Net 1000 Nonstop', 12894, 'FIX_NET', 2), "
                + "('Spec. Üzleti Net 2000 Nonstop', 14963, 'FIX_NET', 2), "
                + "('Spec. Üzleti Net Max', 70634, 'FIX_NET', 2), "


                + "('IPTV_Alap', 6883, 'FIX_TV', 2), "
                + "('IPTV_Extra', 12103, 'FIX_TV', 2), "
                + "('IPTV_S', 4600, 'FIX_TV', 2), "
                + "('IPTV_M', 6990, 'FIX_TV', 2), "
                + "('IPTV_L', 7990, 'FIX_TV', 2), "
                + "('Spec_IPTV_M', 4800, 'FIX_TV', 2), "
                + "('Spec_IPTV_L', 5800, 'FIX_TV', 2), "


                + "('Üzlet_300_Perc', 5347, 'FIX_HANG', 2), "
                + "('NOLIMIT_Belföld', 9335, 'FIX_HANG', 2), "
                + "('NOLIMIT_Belföld_Nemzetközi', 11062, 'FIX_HANG', 2), "
                + "('Dual_Üzlet_300_Perc', 10122, 'FIX_HANG', 2), "
                + "('Dual_NOLIMIT_Belföld', 14097, 'FIX_HANG', 2), "
                + "('Közigazgatási', 17577, 'FIX_HANG', 2)";


        try (java.sql.Connection conn = getConnection();
             java.sql.Statement stmt = conn.createStatement()) {


            stmt.execute(sqlFillProducts);

            System.out.println("Minta adatok (Üzleti Products) sikeresen feltöltve!");

        } catch (java.sql.SQLException e) {
            System.err.println("Minta adat feltöltési hiba: " + e.getMessage());
        }


    }
}