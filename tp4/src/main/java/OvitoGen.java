import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class OvitoGen {

    public static void saveDynamicFile(int frame, ParticleWithCharge p, ParticleWithCharge[][] parts, String path) {

        try(FileWriter fw = new FileWriter(path + "frame-" + frame + ".xyz", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {

            int size = (parts.length * parts.length) + 1;

            out.println(size);
            out.println("");


                out.println(p.getXPos() + " " + p.getYPos() + " " + 0 + " " + p.getRadius() );
            for (int i = 0; i < parts.length; i++){
                for (int j = 0; j < parts.length; j++){

                    out.println(parts[i][j].getXPos() + " " + parts[i][j].getYPos() + " " + 0 + " " + parts[i][j].getRadius() );
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
