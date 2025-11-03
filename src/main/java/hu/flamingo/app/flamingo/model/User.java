package hu.flamingo.app.flamingo.model;

public class User {
    private int userId;
    private String lastName;
    private String firstName;
    private boolean isActive;


    public User(int userId, String lastName, String firstName, boolean isActive) {
        this.userId = userId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.isActive = isActive;
    }


    public int getUserId() {
        return userId;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean isActive() {
        return isActive;
    }


    @Override
    public String toString() {
        return lastName + " " + firstName;
    }
}
