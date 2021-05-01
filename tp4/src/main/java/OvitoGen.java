import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class OvitoGen {

    public static void saveDynamicFile(int frame, List<Particle> particles, String path) {

        try(FileWriter fw = new FileWriter(path + "frame-" + frame + ".xyz", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {

            int size = particles.size();

            out.println(size);
            out.println("");

            for (Particle p: particles) {
                out.println(p.getXPos() + " " + p.getYPos() + " " + 0 + " " + p.getRadius() );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
