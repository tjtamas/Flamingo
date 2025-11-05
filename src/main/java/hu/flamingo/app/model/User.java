package hu.flamingo.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    // ----- Constructors -----
    public User() {
    }

    public User(int userId, String lastName, String firstName, boolean isActive) {
        this.userId = userId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.isActive = isActive;
    }

    public User(String lastName, String firstName, boolean isActive) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.isActive = isActive;
    }

    // ----- Getters & Setters -----
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // ----- toString -----
    @Override
    public String toString() {
        return firstName + " " + lastName + (isActive ? " (active)" : " (inactive)");
    }
}
