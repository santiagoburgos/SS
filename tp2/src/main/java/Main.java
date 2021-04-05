import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

        if (percentage > 100) {
            System.out.println("Percentage cannot be bigger than 100");
            return;
        }


        if (dimensions !=  2 && dimensions != 3) {
            System.out.println("It can only be in 2D or 3D");
            return;
        }

        CellsGenerator cg = new CellsGenerator();



        if (dimensions == 2) {
            Cell[][] cells = cg.generateCells(size,center, percentage);

            LifeCells lc = new LifeCells( time, cells, Rule.FREDKIN, true);

            try {
                createFile("2dstats.cvs", lc.maxDistance, lc.aliveCells, lc.finalTime);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int i = 0; i <= lc.finalTime; i++) {
                OvitoGen.saveDynamicFile(i,lc.aliveCells.get(i), lc.lifeCells,"D:\\OV\\");
            }

        } else {
            Cell[][][] cellsTD = cg.generateCellsTD(size, center, percentage);


            LifeCells lctd = new LifeCells(time, cellsTD, Rule.FREDKIN, true);

            try {
                createFile("3dstats.cvs", lctd.maxDistanceTD, lctd.aliveCellsTD, lctd.finalTime);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int i = 0; i <= lctd.finalTime; i++) {
                OvitoGen.saveDynamicFileTD(i, lctd.aliveCellsTD.get(i), lctd.lifeCellsTD, "D:\\OVTD\\");
            }
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
