package hu.flamingo.app.db.seed;

import hu.flamingo.app.model.User;
import hu.flamingo.app.repository.impl.UserRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

public class UserSeederCSV {

    private final UserRepository repo = new UserRepository();

    public void importFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line;
            boolean first = true;
            int count = 0;

            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }

                // Split pontosvessző alapján (;)
                String[] cols = line.split(";", -1);
                if (cols.length < 3) continue;

                // CSV oszlopok: last_name ; first_name ; is_active
                String lastName = cols[0].trim();
                String firstName = cols[1].trim();
                String activeCol = cols[2].trim();

                boolean isActive = activeCol.equalsIgnoreCase("true")
                        || activeCol.equalsIgnoreCase("1")
                        || activeCol.equalsIgnoreCase("igen");

                // Csak ha van vezetéknév és keresztnév
                if (lastName.isEmpty() || firstName.isEmpty()) continue;

                repo.save(User.builder()
                        .lastName(lastName)
                        .firstName(firstName)
                        .isActive(isActive)
                        .build());

                count++;
            }

            System.out.println("✅ " + count + " felhasználó importálva a CSV-ből.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
