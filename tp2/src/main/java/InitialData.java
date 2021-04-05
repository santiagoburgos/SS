public class InitialData {
    private int time = 100;
    private int dimensions = 2;
    private int size = 10;
    private float percentage = 50;
    private int center;

    public InitialData(int dimensions, int size, float percentage, int center, int time) {
        this.dimensions = dimensions;
        this.size = size;
        this.percentage = percentage;
        this.center = center;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public int getSize() {
        return size;
    }

    public float getPercentage() {
        return percentage;
    }

    public int getCenter() {
        return center;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public void setCenter(int center) {
        this.center = center;
    }

    public int getDimensions() {
        return dimensions;
    }

    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }
}
