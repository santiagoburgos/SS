import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Particle {
    private double position[] = new double[2];
    public int index;

    private List<Particle> neighbour;
    public Particle(double x, double y, int index){
        this.position[0] = x;
        this.position[1] = y;
        this.index = index;
        this.neighbour = new ArrayList<>();
    }

    public double getX(){
        return position[0];
    }

    public double getY(){
        return position[1];
    }

    public void setNeighbour(List<Particle> neighbour) {
        if (neighbour != null)
        this.neighbour.addAll(neighbour);
    }

    public List<Particle> getNeighbour() {

        return neighbour;
    }
}
