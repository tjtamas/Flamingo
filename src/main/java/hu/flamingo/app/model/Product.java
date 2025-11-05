package hu.flamingo.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name", nullable = false, length = 100)
    private String name;

    @Column(name = "vbs_value", nullable = false)
    private double vbsValue;

    @Column(name = "category", length = 50)
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(name = "segment", nullable = false, length = 20)
    private Segment segment;

    // ----- Constructors -----
    public Product() {
    }

    public Product(Long id, String name, double vbsValue, String category, Segment segment) {
        this.id = id;
        this.name = name;
        this.vbsValue = vbsValue;
        this.category = category;
        this.segment = segment;
    }

    public Product(String name, double vbsValue, String category, Segment segment) {
        this.name = name;
        this.vbsValue = vbsValue;
        this.category = category;
        this.segment = segment;
    }

    // ----- Getters & Setters -----
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVbsValue() {
        return vbsValue;
    }

    public void setVbsValue(double vbsValue) {
        this.vbsValue = vbsValue;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    // ----- toString -----
    @Override
    public String toString() {
        return name + " - " + (segment != null ? segment.getDisplayName() : "n/a");
    }
}
