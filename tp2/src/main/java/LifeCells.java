import java.util.*;

public class LifeCells {


    private Cell cells[][];
    public Map<Integer, Cell[][]> lifeCells = new HashMap<>();

    private Cell cellsTD[][][];
    public Map<Integer, Cell[][][]> lifeCellsTD = new HashMap<>();

    private int size;

    public LifeCells( int time, Cell cellsInitial[][]) {

        this.size = cellsInitial.length;

            cells = new Cell[size][size];
        Cell state[][] = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                    Cell cell = cellsInitial[i][j];
                    cells[i][j] = cell;
                    state[i][j] = new Cell(cell.x, cell.y, 0, cell.alive);

            }
        }
        lifeCells.put(0, state);
            SetNeighbours();
            for (int i = 1; i <= time; i++) {
                timeForward(i);
            }

    }

    public LifeCells( int time, Cell cellsInitial[][][]) {
        this.size = cellsInitial.length;

            cellsTD = new Cell[size][size][size];


        Cell state[][][] = new Cell[size][size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int z = 0; z < size; z++) {
                    Cell cell = cellsInitial[i][j][z];
                    cellsTD[i][j][z] = cell;
                    state[i][j][z] = new Cell(cell.x, cell.y, cell.z, cell.alive);
                }
            }
        }
        lifeCellsTD.put(0, state);

            SetNeighboursTD();
            for (int i = 1; i <= time; i++) {
                timeForwardTD(i);
            }

    }

    public void SetNeighboursTD() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int z = 0; z < size; z++) {

                    Cell cell = cellsTD[i][j][z];

                    //agrego la L
                    //arriba
                    if (j < size - 1) {
                        cell.addNeighbour(cellsTD[i][j + 1][z]);
                        cellsTD[i][j + 1][z].addNeighbour(cell);
                    }
                    //arriba derecha
                    if (j < size - 1 && i < size - 1) {
                        cell.addNeighbour(cellsTD[i + 1][j + 1][z]);
                        cellsTD[i + 1][j + 1][z].addNeighbour(cell);
                    }
                    //derecha
                    if (i < size - 1) {
                        cell.addNeighbour(cellsTD[i + 1][j][z]);
                        cellsTD[i + 1][j][z].addNeighbour(cell);
                    }
                    // abajo derecha
                    if (i < size - 1 && j > 0) {
                        cell.addNeighbour(cellsTD[i + 1][j - 1][z]);
                        cellsTD[i + 1][j - 1][z].addNeighbour(cell);
                    }

                    //3D  agrego los 9 Z siguientes
                    //
                    if(z < size-1){
                        //arriba
                        if (j < size - 1 ) {
                            cell.addNeighbour(cellsTD[i][j + 1][z+1]);
                            cellsTD[i][j + 1][z+1].addNeighbour(cell);
                        }
                        //arriba derecha
                        if (j < size - 1 && i < size - 1) {
                            cell.addNeighbour(cellsTD[i + 1][j + 1][z+1]);
                            cellsTD[i + 1][j + 1][z+1].addNeighbour(cell);
                        }
                        //arriba izquierda
                        if (j < size - 1 && i > 0) {
                            cell.addNeighbour(cellsTD[i - 1][j + 1][z+1]);
                            cellsTD[i - 1][j + 1][z+1].addNeighbour(cell);
                        }

                        //derecha
                        if (i < size - 1) {
                            cell.addNeighbour(cellsTD[i + 1][j][z+1]);
                            cellsTD[i + 1][j][z+1].addNeighbour(cell);
                        }
                        //centro
                            cell.addNeighbour(cellsTD[i][j][z+1]);
                            cellsTD[i][j][z+1].addNeighbour(cell);
                        //izquierda
                        if (i > 0) {
                            cell.addNeighbour(cellsTD[i - 1][j][z+1]);
                            cellsTD[i - 1][j][z+1].addNeighbour(cell);
                        }

                        // abajo derecha
                        if (i < size - 1 && j > 0) {
                            cell.addNeighbour(cellsTD[i + 1][j - 1][z+1]);
                            cellsTD[i + 1][j - 1][z+1].addNeighbour(cell);
                        }
                        // abajo
                        if (j > 0) {
                            cell.addNeighbour(cellsTD[i][j - 1][z+1]);
                            cellsTD[i][j - 1][z+1].addNeighbour(cell);
                        }
                        // abajo izquierda
                        if (i > 0 && j > 0) {
                            cell.addNeighbour(cellsTD[i - 1][j - 1][z+1]);
                            cellsTD[i - 1][j - 1][z+1].addNeighbour(cell);
                        }
                    }

                }
            }
        }
    }


    public void SetNeighbours() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                Cell cell = cells[i][j];
                //arriba
                if (j < size - 1) {
                    cell.addNeighbour(cells[i][j + 1]);
                    cells[i][j + 1].addNeighbour(cell);
                }
                //arriba derecha
                if (j < size - 1 && i < size - 1) {
                    cell.addNeighbour(cells[i + 1][j + 1]);
                    cells[i + 1][j + 1].addNeighbour(cell);
                }
                //derecha
                if (i < size - 1) {
                    cell.addNeighbour(cells[i + 1][j]);
                    cells[i + 1][j].addNeighbour(cell);
                }
                // abajo derecha
                if (i < size - 1 && j > 0) {
                    cell.addNeighbour(cells[i + 1][j - 1]);
                    cells[i + 1][j - 1].addNeighbour(cell);
                }

            }
        }
    }

    public void timeForwardTD(int time){

        Cell state[][][] = new Cell[size][size][size];

        List<Cell> cellsState = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int z = 0; z < size; z++) {

                    //System.out.println(cellsTD[i][j][z].neighbours.size());

                    Cell cell = cellsTD[i][j][z];
                    int aliveN = cell.aliveNeighbours();

                    if (cell.alive) {
                        if (aliveN != 2 && aliveN != 3) {
                           // cell.setAlive(false);
                            cellsState.add(cell);
                        }
                    } else {
                        if (aliveN == 3) {
                           // cell.setAlive(true);
                            cellsState.add(cell);
                        }
                    }
                    state[i][j][z] = new Cell(cell.x, cell.y, cell.z, cell.alive);
                }
            }
        }
        lifeCellsTD.put(time, state);

        for(Cell c :cellsState){
            c.changeState();
        }
    }

    public void timeForward(int time){

        Cell state[][] = new Cell[size][size];

        List<Cell> cellsState = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                Cell cell = cells[i][j];
                int aliveN = cell.aliveNeighbours();
                if(cell.alive){
                    if(aliveN != 2 && aliveN != 3 ){
                        //cell.setAlive(false);
                        cellsState.add(cell);
                    }
                } else{
                    if(aliveN == 3){
                        //cell.setAlive(true);
                        cellsState.add(cell);
                    }
                }
                state[i][j] = new Cell(cell.x, cell.y, 0,cell.alive);
            }
        }
        lifeCells.put(time, state);

        for(Cell c :cellsState){
            c.changeState();
        }

    }




}