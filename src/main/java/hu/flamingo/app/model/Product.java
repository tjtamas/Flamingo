package hu.flamingo.app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Override
    public String toString() {
        return name + " - " + segment.getDisplayName();
    }
}
