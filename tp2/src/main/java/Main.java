import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {



    public static void main(String[] args) {

        int time = 100;

        CellsGenerator cg = new CellsGenerator();
        Cell[][] cells = cg.generateCells(100,10,50);
        Cell[][][] cellsTD = cg.generateCellsTD(50,5,40);



        LifeCells lc = new LifeCells( 100, cells, Rule.FREDKIN, true);

        try {
            createFile("2dstats.cvs", lc.maxDistance, lc.aliveCells, lc.finalTime);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i <= lc.finalTime; i++) {
            OvitoGen.saveDynamicFile(i,lc.aliveCells.get(i), lc.lifeCells,"D:\\OV\\");
        }



        LifeCells lctd = new LifeCells( 100, cellsTD, Rule.FREDKIN, true);

        try {
            createFile("3dstats.cvs", lctd.maxDistanceTD, lctd.aliveCellsTD, lctd.finalTime);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i <= lctd.finalTime; i++) {
           OvitoGen.saveDynamicFileTD(i, lctd.aliveCellsTD.get(i), lctd.lifeCellsTD,"D:\\OVTD\\");
        }


    }

    public static void createFile(String fileName, Map<Integer, Integer> maxDistance, Map<Integer, Integer> aliveCells, int time)
            throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        writer.write("time,maxdistance,alivecells\n");
        for (int i = 0; i <= time; i++) {
            writer.write(i+","+maxDistance.get(i)+","+aliveCells.get(i)+"\n");
        }


        writer.close();
    }






}
