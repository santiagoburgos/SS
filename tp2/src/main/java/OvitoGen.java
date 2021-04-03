import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class OvitoGen {


    public static void saveDynamicFile(int frame, int aliveCells, Map<Integer, Cell[][]> lifeCells, String path) {
        try(FileWriter fw = new FileWriter(path + "frame-" + frame + ".xyz", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {

            int size = lifeCells.get(0).length;

            out.println(aliveCells);
            out.println("");


            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    // Se crea en cada particula con su (posX, PosY, PosZ, color)
                    if(lifeCells.get(frame)[i][j].alive)
                    out.println(lifeCells.get(frame)[i][j].getX() + " " + lifeCells.get(frame)[i][j].getY() + " " + lifeCells.get(frame)[i][j].getZ() + " " + (lifeCells.get(frame)[i][j].alive? 1:2) );
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static void saveDynamicFileTD(int frame, int aliveCells, Map<Integer, Cell[][][]> lifeCells, String path) {
        try(FileWriter fw = new FileWriter(path + "frame-" + frame + ".xyz", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            int size = lifeCells.get(0).length;


             out.println(aliveCells);
             out.println("");
            //

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    for (int z = 0; z < size; z++) {
                        // Se crea en cada particula con su (posX, PosY, PosZ, color)
                        if(lifeCells.get(frame)[i][j][z].alive)
                        out.println(lifeCells.get(frame)[i][j][z].getX() + " " + lifeCells.get(frame)[i][j][z].getY() + " " + lifeCells.get(frame)[i][j][z].getZ() + " " + (lifeCells.get(frame)[i][j][z].alive ? 1 : 2));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
