import java.util.HashMap;
import java.util.Map;

public class Main {



    public static void main(String[] args) {

        int time = 100;

        CellsGenerator cg = new CellsGenerator();
        Cell[][] cells = cg.generateCells(100,10,50);
        Cell[][][] cellsTD = cg.generateCellsTD(50,5,40);



        LifeCells lc = new LifeCells( 100, cells, Rule.FREDKIN, true);
        for (int i = 0; i <= lc.finalTime; i++) {
            OvitoGen.saveDynamicFile(i,lc.aliveCells.get(i), lc.lifeCells,"D:\\OV\\");
        //    System.out.println("time " + i + " maxdist " + lc.maxDistance.get(i) + " aliveCells " + lc.aliveCells.get(i) );

        }



        LifeCells lctd = new LifeCells( 100, cellsTD, Rule.FREDKIN, true);
        for (int i = 0; i <= lctd.finalTime; i++) {
           OvitoGen.saveDynamicFileTD(i, lctd.aliveCellsTD.get(i), lctd.lifeCellsTD,"D:\\OVTD\\");
          // System.out.println("time " + i + " maxdist " + lctd.maxDistanceTD.get(i) + " aliveCells " + lctd.aliveCellsTD.get(i) );

        }


    }






}
