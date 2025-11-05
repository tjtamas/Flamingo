package hu.flamingo.app.db;

import hu.flamingo.app.db.seed.ProductSeederCSV;
import hu.flamingo.app.db.seed.UserSeederCSV;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.File;

public class DatabaseManager {

    private static final String DB_PATH = "data/db/";
    private static final String DB_FILE_NAME = "flamingo.db";
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("flamingoPU");

    /**
     * Ellenőrzi, hogy a data/db mappa létezik-e, ha nem, létrehozza.
     */
    private static void ensureDataDirectory() {
        File dir = new File(DB_PATH);
        if (!dir.exists() && dir.mkdirs()) {
            System.out.println("📁 A 'data/db' mappa létrehozva.");
        }
    }

    /**
     * Inicializálja az adatbázist: létrehozza, ha hiányzik, és feltölti CSV-ből.
     */
    public static void initializeDatabase() {
        ensureDataDirectory();

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            long userCount = (long) em.createQuery("SELECT COUNT(u) FROM User u").getSingleResult();
            long productCount = (long) em.createQuery("SELECT COUNT(p) FROM Product p").getSingleResult();

            em.getTransaction().commit();

            System.out.println("📊 USERS: " + userCount + " | PRODUCTS: " + productCount);

            if (userCount == 0) {
                System.out.println("➡️  Felhasználók importálása CSV-ből...");
                new UserSeederCSV().importFromCSV("data/csv/User.csv");
            }

            if (productCount == 0) {
                System.out.println("➡️  Termékek importálása CSV-ből...");
                new ProductSeederCSV().importFromCSV("data/csv/Product.csv");
            }

            System.out.println("✅ Adatbázis inicializálása befejezve.");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void close() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
