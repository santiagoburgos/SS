public class Particle {
    private int number;
    private double x;
    private double y;

    public Particle(int number, double x, double y) {
        this.number = number;
        this.x = x;
        this.y = y;
    }

    public double getNumber() { return number; }

    public double getX() { return x; }

    public double getY() { return y; }
}
