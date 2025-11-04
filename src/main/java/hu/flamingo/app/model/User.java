package hu.flamingo.app.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @NonNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NonNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Builder(toBuilder = true)
    public User(String lastName, String firstName, boolean isActive) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.isActive = isActive;
    }


}
