package hu.flamingo.app.model;

public enum Segment {

    LAKOSSAGI("Lakossági"),
    UZLETI("Üzleti");

    private final String displayName;

    Segment(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
