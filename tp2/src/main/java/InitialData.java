public class InitialData {
    private boolean isTime = true;
    private int time = 100;
    private boolean isSize = false;
    private int dimensions = 2;
    private int size = 10;
    private float percentage = 50;
    private int center;

    public InitialData(int dimensions, int size, float percentage, int center) {
        this.dimensions = dimensions;
        this.isSize = true;
        this.isTime = false;
        this.size = size;
        this.percentage = percentage;
        this.center = center;
    }

    public InitialData(int dimensions, int size, float percentage, int center, int time) {
        this.dimensions = dimensions;
        this.size = size;
        this.percentage = percentage;
        this.center = center;
        this.time = time;
    }

    public boolean getIsTime() {
        return isTime;
    }

    public int getTime() {
        return time;
    }

    public boolean getIsSize() {
        return isSize;
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

    public void setIsTime(boolean isTime) {
        this.isTime = isTime;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setIsSize(boolean isSize) {
        this.isSize = isSize;
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
