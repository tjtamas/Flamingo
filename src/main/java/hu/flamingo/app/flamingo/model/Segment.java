package hu.flamingo.app.flamingo.model;

public class Segment {

    private int segmentId;
    private String segmentName;

    public Segment(int segmentId, String segmentName) {
        this.segmentId = segmentId;
        this.segmentName = segmentName;
    }

    public int getSegmentId() {
        return segmentId;
    }

    public String getSegmentName() {
        return segmentName;
    }


    @Override
    public String toString() {
        return segmentName;
    }
}