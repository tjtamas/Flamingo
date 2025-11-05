package hu.flamingo.app.db.seed;

import hu.flamingo.app.model.Product;
import hu.flamingo.app.model.Segment;
import hu.flamingo.app.repository.impl.ProductRepository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

public class ProductSeederCSV {

    private final ProductRepository repo = new ProductRepository();

    public void importFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line;
            boolean first = true;
            int count = 0;

            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }

                String[] cols = line.split(";", -1);
                if (cols.length < 4) continue;

                Segment segment = cols[0].trim().equalsIgnoreCase("Üzleti")
                        ? Segment.UZLETI : Segment.LAKOSSAGI;
                String category = cols[1].trim();
                String name = cols[2].trim();
                String vbsRaw = cols[3].replaceAll("[^0-9]", "");
                double vbsValue = vbsRaw.isEmpty() ? 0 : Double.parseDouble(vbsRaw);

                // --- új Product példány, builder helyett konstruktorral ---
                Product product = new Product(name, vbsValue, category, segment);

                repo.save(product);
                count++;
            }

            System.out.println("✅ " + count + " termék importálva a CSV-ből.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
