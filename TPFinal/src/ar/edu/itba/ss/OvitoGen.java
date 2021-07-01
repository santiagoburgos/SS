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
            double w3 = 0;
            while (w3 <20){
                particles.add(new Particle(w3,20,0,0,0.05));
                w3+=0.05;
            }
            double w4 = 0;
            while (w4 <20){
                particles.add(new Particle(0,w4,0,0,0.05));
                w4+=0.05;
            }
            double w5 = 0;
            while (w5 <20){
                particles.add(new Particle(20,w5,0,0,0.05));
                w5+=0.05;
            }






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
