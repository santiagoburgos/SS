package ar.edu.itba.ss;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class OvitoGen {
    public static void saveDynamicFile(int frame, List<Particle> particles,double d, String path) {

        try(FileWriter fw = new FileWriter(path + "frame-" + frame + ".xyz", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {

            /*
            // esto dibuja la pared pero OJO esta alterando la lista de particulas
            double w1 = 10-(d/2);
            while (w1 >0){
                particles.add(new Particle(w1,0,0,0,0.05));
                w1-=0.05;
            }
            double w2 = 10+(d/2);
            while (w2 <20){
                particles.add(new Particle(w2,0,0,0,0.05));
                w2+=0.05;
            }

             */




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
