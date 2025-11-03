package hu.flamingo.app.flamingo.model;

public class Product {

    private int productId;
    private String productName;
    private double vbsValue;
    private String category;
    private int segmentId;


    public Product(int productId, String productName, double vbsValue, String category, int segmentId) {
        this.productId = productId;
        this.productName = productName;
        this.vbsValue = vbsValue;
        this.category = category;
        this.segmentId = segmentId;
    }


    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getVbsValue() {
        return vbsValue;
    }

    public String getCategory() {
        return category;
    }

    public int getSegmentId() {
        return segmentId;
    }


    @Override
    public String toString() {
        return productName;
    }
}