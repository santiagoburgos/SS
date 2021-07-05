package ar.edu.itba.ss;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class OvitoGen {
    public static void saveDynamicFile(int frame, List<Particle> particles,double d, int conf, double l, String path) {

        double size = 20;
        double ypos = 2;

        try(FileWriter fw = new FileWriter(path + "frame-" + frame + ".xyz", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {

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



            if(conf == 3){
                double w1 = 0;
                while (w1 < ((size/2)-((d/3)/2)-l-(d/3))){
                    particles.add(new Particle(w1,0,0,0,0.05));
                    w1+=0.05;
                }
                w1 = ((size/2)-((d/3)/2)-l);
                while (w1 < ((size/2)-((d/3)/2)) ){
                    particles.add(new Particle(w1,0,0,0,0.05));
                    w1+=0.05;
                }
                w1 = ((size/2)+((d/3)/2));
                while (w1 < ((size/2)+((d/3)/2)+l) ){
                    particles.add(new Particle(w1,0,0,0,0.05));
                    w1+=0.05;
                }
                w1 = ((size/2)+((d/3)/2)+l+(d/3));
                while (w1 < 20 ){
                    particles.add(new Particle(w1,0,0,0,0.05));
                    w1+=0.05;
                }

            }

            if(conf == 1){
                double w1 = 0;
                if(l!=0){
                    w1 = (size/2)-(l/2);
                    while (w1 < (size/2)+(l/2) ){
                        particles.add(new Particle(w1,ypos,0,0,0.05));
                        w1+=0.05;
                    }
                }

                w1 = 0;
                while (w1 < (size/2)-(d/2) ){
                    particles.add(new Particle(w1,0,0,0,0.05));
                    w1+=0.05;
                }
                w1 = (size/2)+(d/2);
                while (w1 < 20 ){
                    particles.add(new Particle(w1,0,0,0,0.05));
                    w1+=0.05;
                }
            }

            if(conf == 2){
                double w1 = 0;
                while (w1 < (l/2) ){
                    particles.add(new Particle(w1,ypos,0,0,0.05));
                    w1+=0.05;
                }
                w1 = size-(l/2);
                while (w1 <size ){
                    particles.add(new Particle(w1,ypos,0,0,0.05));
                    w1+=0.05;
                }
                w1 = (d/2);
                while (w1 <  size-(d/2)){
                    particles.add(new Particle(w1,0,0,0,0.05));
                    w1+=0.05;
                }
            }


            int sizep = particles.size();

            out.println(sizep);
            out.println("");

            for (Particle p: particles) {
                out.println(p.getXPos() + " " + p.getYPos() + " " + 0 + " " + p.getRadius() );
            }




        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
