import java.util.ArrayList;
import java.util.List;

public class Particle {
    private int number;
    private double x, y;
    private double radius = 0;

    private ArrayList<Particle> neighbour;

    public Particle( double x, double y, int number) {
        this.number = number;
        this.x = x;
        this.y = y;
        this.neighbour = new ArrayList<>();

    }

    public Particle( double x, double y, double radius, int number) {
        this.number = number;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.neighbour = new ArrayList<>();

    }

    public double getNumber() { return number; }

    public double getX() { return x; }

    public double getY() { return y; }

    public void setNeighbour(List<Particle> neighbour) {
        if (neighbour != null)
            this.neighbour.addAll(neighbour);
    }


    public void addNeighbour(Particle neighbour) {
        if (neighbour != null && !this.neighbour.contains(neighbour))
            this.neighbour.add(neighbour);
    }


    public ArrayList<Particle> getNeighbour() {
        return neighbour;
    }
}
