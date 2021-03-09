public class test {
    public static void main(String[] args) {
        CellIndex cellIndex = new CellIndex(10, 10, 10, 0.5);
        cellIndex.printParticles();
        cellIndex.setNeighbour();
        cellIndex.printNeighbours();
    }
}
