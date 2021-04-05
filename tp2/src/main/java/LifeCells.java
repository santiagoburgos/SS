import java.util.*;

public class LifeCells {


    private Cell cells[][];
    public Map<Integer, Cell[][]> lifeCells = new HashMap<>();
    public Map<Integer, Integer> aliveCells = new HashMap<>();
    public Map<Integer, Integer> maxDistance = new HashMap<>();

    private Cell cellsTD[][][];
    public Map<Integer, Cell[][][]> lifeCellsTD = new HashMap<>();
    public Map<Integer, Integer> aliveCellsTD = new HashMap<>();
    public Map<Integer, Integer> maxDistanceTD = new HashMap<>();


    int finalTime;
    Rule rule;
    boolean finishIfTouchLimit;
    private int size;

    public LifeCells( int time, Cell cellsInitial[][], Rule rule, boolean finishIfTouchLimit) {
        finalTime = time;
        this.finishIfTouchLimit = finishIfTouchLimit;
        this.rule = rule;
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


            SetNeighbours();
            for (int i = 0; i < time; i++) {
                boolean finish = timeForward(i);
                if(finish)
                    break;
            }
        stats(finalTime, false);
    }

    public LifeCells( int time, Cell cellsInitial[][][], Rule rule, boolean finishIfTouchLimit) {
        finalTime = time;
        this.finishIfTouchLimit = finishIfTouchLimit;
        this.rule = rule;
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


            SetNeighboursTD();
            for (int i = 0; i < time; i++) {
               boolean finish = timeForwardTD(i);
                if(finish)
                    break;
            }

            stats(finalTime,true);
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

    public boolean timeForwardTD(int time){
        boolean finish = false;

        Cell state[][][] = new Cell[size][size][size];

        List<Cell> cellsState = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int z = 0; z < size; z++) {


                    Cell cell = cellsTD[i][j][z];


                    if(cell.alive && finishIfTouchLimit){
                        if( i==0 || j==0 || i==(size-1) || j==(size-1) || z==0 || z==(size-1)){
                            finalTime = time;
                            finish = true;
                        }
                    }


                    if(handleRule(rule, cell)){
                        cellsState.add(cell);
                    }
                    state[i][j][z] = new Cell(cell.x, cell.y, cell.z, cell.alive);

                }
            }
        }
        lifeCellsTD.put(time, state);

        if (time > 10) {
            for (int i = time - 10; i < time; i++) {
                Cell[][][] aux  = lifeCellsTD.get(i);
                boolean flag = true;
                for (int x = 0; x < size && flag; x++) {
                    for (int y = 0; y < size && flag; y++) {
                        for (int z = 0; z < size && flag; z++) {
                            if (aux[x][y][z].alive != state[x][y][z].alive)
                                flag = false;
                        }
                    }
                }
                if (flag) {
                    finalTime = time;
                    finish = true;
                }
            }
        }


        for(Cell c :cellsState){
            c.changeState();
        }

        if(finish)
            return true;

        return false;
    }


    public boolean timeForward(int time){
        boolean finish = false;

        Cell state[][] = new Cell[size][size];

        List<Cell> cellsState = new ArrayList<>();


        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                Cell cell = cells[i][j];

                if(cell.alive && finishIfTouchLimit){
                    if( i==0 || j==0 || i==(size-1) || j==(size-1)){
                        finalTime = time;
                        finish = true;
                    }
                }


                if(handleRule(rule, cell)){
                    cellsState.add(cell);
                }
                state[i][j] = new Cell(cell.x, cell.y, 0,cell.alive);

            }
        }
        lifeCells.put(time, state);

        if (time > 10) {
            for (int i = time - 10; i < time; i++) {
                Cell[][] aux  = lifeCells.get(i);
                boolean flag = true;
                for (int x = 0; x < size && flag; x++) {
                    for (int y = 0; y < size && flag; y++) {
                        if (aux[x][y].alive != state[x][y].alive)
                            flag = false;
                    }
                }
                if (flag) {
                    System.out.println("Founded a cycle on the cells");
                    finalTime = time;
                    finish = true;
                }
            }
        }
        for(Cell c :cellsState){
            c.changeState();
        }

        if(finish)
            return true;

        return false;
    }


    public boolean handleRule(Rule rule, Cell cell){

        if(rule == Rule.CONWAYLIFE){
            int aliveN = cell.aliveNeighbours();
            if(cell.alive){
                if(aliveN != 2 && aliveN != 3 ){
                    return true;
                }
            } else{
                if(aliveN == 3){
                    return true;
                }
            }
        }
        if(rule == Rule.FREDKINMOORE){
            int aliveN = cell.aliveNeighbours();
            if(cell.alive){
                if(aliveN %2 == 0 ){
                    return true;
                }
            } else{
                if(aliveN %2 != 0){
                    return true;
                }
            }
        }
        if(rule == Rule.FREDKIN){
            int aliveN = cell.aliveNeumannNeighbours();
            if(cell.alive){
                if(aliveN %2 == 0 ){
                    return true;
                }
            } else{
                if(aliveN %2 != 0){
                    return true;
                }
            }
        }
        if(rule == Rule.CONWAYLIFENEUMANN){
            int aliveN = cell.aliveNeumannNeighbours();
            if(cell.alive){
                if(aliveN != 2 && aliveN != 3 ){
                    return true;
                }
            } else{
                if(aliveN == 3){
                    return true;
                }
            }
        }

        if (rule == Rule.HORSE) {
            int aliveSuperior = cell.aliveSuperior();
            int aliveInferior = cell.aliveInferior();
            if(cell.alive){
                if(aliveSuperior >= aliveInferior ){
                    return true;
                }
            } else{
                if(aliveSuperior < aliveInferior){
                    return true;
                }
            }
        }

        return false;
    }



    public void stats(int time, boolean td){

            if(!td){
                int size = cells[0].length;
                int aliveC = 0;
                int maxDist = 0;
                for (int t = 0; t < time; t++) {
                    for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                            Cell cell = lifeCells.get(t)[i][j];
                            if (cell.alive) {
                                aliveC += 1;
                                int iDistance = (size / 2) - i;
                                if (iDistance < 0)
                                    iDistance = iDistance * -1;
                                int jDistance = (size / 2) - j;
                                if (jDistance < 0)
                                    jDistance = jDistance * -1;
                                int distance = Math.max(iDistance, jDistance);
                                if (distance > maxDist)
                                    maxDist = distance;
                            }
                            //
                            int iDistance = (size/2) - i;
                            if (iDistance < 0)
                                iDistance = iDistance * -1;

                            int jDistance = (size/2) - j;

                            if (jDistance < 0)
                                jDistance = jDistance * -1;

                            int distance = Math.max(iDistance, jDistance);
                            cell.color = distance;
                            //
                        }
                    }
                    aliveCells.put(t,aliveC);
                    maxDistance.put(t,maxDist);
                    aliveC = 0;
                    maxDist = 0;
                }
            } else{
                int size = cellsTD[0][0].length;
                int aliveC = 0;
                int maxDist = 0;
                for (int t = 0; t < time; t++) {
                    for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                            for (int z = 0; z < size; z++) {
                                Cell cell = lifeCellsTD.get(t)[i][j][z];
                                if (cell.alive) {
                                    aliveC += 1;
                                    int iDistance = (size / 2) - i;
                                    if (iDistance < 0)
                                        iDistance = iDistance * -1;
                                    int jDistance = (size / 2) - j;
                                    if (jDistance < 0)
                                        jDistance = jDistance * -1;
                                    int zDistance = (size / 2) - z;
                                    if (zDistance < 0)
                                        zDistance = zDistance * -1;
                                    int distance = Math.max(Math.max(iDistance, jDistance),zDistance);
                                    if (distance > maxDist)
                                        maxDist = distance;
                                }
                                //
                                int iDistance = (size / 2) - i;
                                if (iDistance < 0)
                                    iDistance = iDistance * -1;
                                int jDistance = (size / 2) - j;
                                if (jDistance < 0)
                                    jDistance = jDistance * -1;
                                int zDistance = (size / 2) - z;
                                if (zDistance < 0)
                                    zDistance = zDistance * -1;
                                int distance = Math.max(Math.max(iDistance, jDistance),zDistance);
                                cell.color = distance;
                                //
                            }
                        }
                    }
                    aliveCellsTD.put(t,aliveC);
                    maxDistanceTD.put(t,maxDist);
                    aliveC = 0;
                    maxDist = 0;
                }
            }







    }


}