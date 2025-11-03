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
        System.out.println("Minta adatok feltöltése (Products)...");

        // --- 1. PRODUCTS FELTÖLTÉSE (A TE KÓDOD INNEN) ---
        String sqlFillProducts = "INSERT OR IGNORE INTO PRODUCTS (product_name, vbs_value, category, segment_id) VALUES "
                // --- 1. ADAG (Lakossági Vezetékes Net - 11 sor) ---
                + "('Net_Plusz', 5992, 'FIX_NET', 1), "
                + "('Net_250', 6264, 'FIX_NET', 1), "
                + "('Net_500', 7736, 'FIX_NET', 1), "
                + "('Net_1000', 8390, 'FIX_NET', 1), "
                + "('Net_2000_Pro', 9690, 'FIX_NET', 1), "
                + "('Net_4000', 14990, 'FIX_NET', 1), "
                + "('Spec_Net_Plusz', 4160, 'FIX_NET', 1), "
                + "('Spec_Net_250', 5024, 'FIX_NET', 1), "
                + "('Spec_Net_500', 5928, 'FIX_NET', 1), "
                + "('Spec_Net_1000', 7290, 'FIX_NET', 1), "
                + "('Spec_Net_2000', 8690, 'FIX_NET', 1), "
                // --- 2. ADAG (Lakossági TV - 14 sor) ---
                + "('TTV_IPTV_S', 4600, 'FIX_TV', 1), "
                + "('TTV_IPTV_M', 6990, 'FIX_TV', 1), "
                + "('TTV_IPTV_L', 7700, 'FIX_TV', 1), "
                + "('TTV_IPTV_L_Streaming', 10000, 'FIX_TV', 1), "
                + "('Spec_TTV_IPTV_M', 4800, 'FIX_TV', 1), "
                + "('Spec_TTV_IPTV_L', 5510, 'FIX_TV', 1), "
                + "('Spec_TTV_IPTV_L_Streaming', 7810, 'FIX_TV', 1), "
                + "('SOLO_TTV_IPTV_S', 4600, 'FIX_TV', 1), "
                + "('SOLO_TTV_IPTV_M', 6990, 'FIX_TV', 1), "
                + "('SOLO_TTV_IPTV_L', 7700, 'FIX_TV', 1), "
                + "('SOLO_TTV_IPTV_L_Streaming', 10000, 'FIX_TV', 1), "
                + "('Spec_SOLO_TTV_M', 4800, 'FIX_TV', 1), "
                + "('Spec_SOLO_TTV_L', 5510, 'FIX_TV', 1), "
                + "('Spec_SOLO_TTV_L_Streaming', 7810, 'FIX_TV', 1), "
                // --- 3. ADAG (Lakossági Vezetékes Hang - 3 sor) ---
                + "('Alap', 1992, 'FIX_HANG', 1), "
                + "('Hoppá Plusz', 4832, 'FIX_HANG', 1), "
                + "('Spec_Alap', 896, 'FIX_HANG', 1), "
                // --- 4. ADAG (Lakossági Mobil Hang - 3 sor) ---
                + "('Mobil_S', 2830, 'MOBIL_HANG', 1), "
                + "('Mobil_M', 4730, 'MOBIL_HANG', 1), "
                + "('Mobil_L', 7730, 'MOBIL_HANG', 1), "
                // --- 5. ADAG (Lakossági Mobil Adat - 5 sor) ---
                + "('Net_5GB', 2990, 'MOBIL_ADAT', 1), "
                + "('Net_30GB', 6990, 'MOBIL_ADAT', 1), "
                + "('Net_L', 11990, 'MOBIL_ADAT', 1), "
                + "('Net_Mobilnet_20GB', 6990, 'MOBIL_ADAT', 1), "
                + "('Net_Mobilnet_300GB', 12990, 'MOBIL_ADAT', 1)"; // <-- Most már ide kell a ;

        // --- EZ A BLOKK HIÁNYZOTT TELJESEN ---
        // (Lefuttatja a fenti 'sqlFillProducts' parancsot)
        try (java.sql.Connection conn = getConnection();
             java.sql.Statement stmt = conn.createStatement()) {

            // CSAK A TERMÉKEKET futtatjuk
            stmt.execute(sqlFillProducts);

            System.out.println("Minta adatok (Products) sikeresen feltöltve!");

        } catch (java.sql.SQLException e) {
            System.err.println("Minta adat feltöltési hiba: " + e.getMessage());
        }
    }
}