import java.util.HashMap;
import java.util.Map;

public class Main {



    public static void main(String[] args) {

        FileGenerator fg = new FileGenerator();
        InitialData initial = fg.readStaticFile("Static.txt");
        int dimensions = initial.getDimensions();
        int size = initial.getSize();
        float percentage = initial.getPercentage();
        int center = initial.getCenter();
        int time = initial.getTime();

        if (initial.getIsSize()) {
            System.out.println("To be implemented");
            return;
        }

        if (percentage > 100) {
            System.out.println("Percentage bigger than 100%");
            return;
        }

        if (dimensions !=  2 && dimensions != 3) {
            System.out.println("It can only be in 2D or 3D");
            return;
        }

        CellsGenerator cg = new CellsGenerator();

        if (dimensions == 2) {
            Cell[][] cells = cg.generateCells(size,center, percentage);

            LifeCells lc = new LifeCells( 100, cells, Rule.FREDKIN, true);
            for (int i = 0; i <= lc.finalTime; i++) {
                OvitoGen.saveDynamicFile(i,lc.aliveCells.get(i), lc.lifeCells,"D:\\OV\\");
                //    System.out.println("time " + i + " maxdist " + lc.maxDistance.get(i) + " aliveCells " + lc.aliveCells.get(i) );

            }

        } else {
            Cell[][][] cellsTD = cg.generateCellsTD(size,center,percentage);

            LifeCells lctd = new LifeCells( 100, cellsTD, Rule.FREDKIN, true);
            for (int i = 0; i <= lctd.finalTime; i++) {
                OvitoGen.saveDynamicFileTD(i, lctd.aliveCellsTD.get(i), lctd.lifeCellsTD, "D:\\OVTD\\");
                // System.out.println("time " + i + " maxdist " + lctd.maxDistanceTD.get(i) + " aliveCells " + lctd.aliveCellsTD.get(i) );
            }

        }

    }






}
