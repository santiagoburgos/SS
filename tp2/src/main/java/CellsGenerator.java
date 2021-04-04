import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CellsGenerator {


    public CellsGenerator() {
    }


    public Cell[][][] generateCellsTD(int size, int center, double percentage){

        Cell state[][][] = new Cell[size][size][size];
        List<Cell> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int z = 0; z < size; z++) {

                    Cell cell;

                    if( i < size/2+center/2 &&  i >= size/2-center/2 && j < size/2+center/2 &&  j >= size/2-center/2 && z < size/2+center/2 &&  z >= size/2-center/2){
                        cell = new Cell(i, j, z, false);
                        list.add(cell);
                    }
                    else{
                        cell = new Cell(i, j, z, false);
                    }
                    state[i][j][z] = new Cell(cell.x, cell.y, cell.z, cell.alive);
                }
            }
        }
        Collections.shuffle(list);
        double centerSize = center * center * center;
        int liveCells = (int)((percentage*centerSize)/100);

        if(list.size() < liveCells)
            liveCells -=1;

        for (int i = 0; i < liveCells; i++) {
            Cell c = list.get(i);
            c.alive = true;
            state[c.x][c.y][c.z].alive = true;
        }
        return state;
    }

    public Cell[][] generateCells(int size, int center, double percentage){


        Cell state[][] = new Cell[size][size];
        List<Cell> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                Cell cell;
                if( i < size/2+center/2 &&  i >= size/2-center/2 && j < size/2+center/2 &&  j >= size/2-center/2){
                    cell = new Cell(i, j, 0, false);
                    list.add(cell);
                }
                else{
                    cell = new Cell(i, j, 0, false);
                }
                state[i][j] = new Cell(cell.x, cell.y, cell.z, cell.alive);
            }
        }

        Collections.shuffle(list);
        double centerSize = center * center;
        int liveCells = (int)((percentage*centerSize)/100);

        if(list.size() < liveCells)
            liveCells -=1;


        for (int i = 0; i < liveCells; i++) {
            Cell c = list.get(i);
            c.alive = true;
            state[c.x][c.y].alive = true;
        }
        return state;
    }




}
