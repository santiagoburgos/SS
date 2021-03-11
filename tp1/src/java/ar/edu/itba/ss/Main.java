package ar.edu.itba.ss;

public class Main {

    public static void main(String[] args) {
        CellIndex cellIndex = new CellIndex(10, 10, 10, 0.5);
        cellIndex.printParticles();
        cellIndex.setNeighbour();
        cellIndex.printNeighbours();
    }
}
