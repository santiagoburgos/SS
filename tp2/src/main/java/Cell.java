import java.util.ArrayList;

public class Cell {

    int x;
    int y;
    int z;
    boolean alive;
    public ArrayList<Cell> neighbours;

    public Cell(int x, int y, int z, boolean alive){
        neighbours = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.z = z;
        this.alive = alive;
    }


    public int getX() { return x; }

    public int getY() { return y; }

    public int getZ() { return z; }

    public void addNeighbour(Cell neighbour) {
        if (neighbour != null && !this.neighbours.contains(neighbour))
            this.neighbours.add(neighbour);
    }

    public int aliveNeighbours(){
        int alive = 0;

        for (Cell c : neighbours){
            if(c.alive){
                alive += 1;
            }
        }


        return alive;
    }

    public int deadNeighbours(){
        int dead;
        dead = neighbours.size() - aliveNeighbours();

        return dead;
    }

    public void setAlive(boolean alive){
        this.alive = alive;
    }

    public void changeState(){
        if(alive){
            alive = false;
        }
        else{
            alive = true;
        }
    }

}
