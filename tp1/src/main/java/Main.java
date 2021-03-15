import com.sun.xml.internal.ws.wsdl.writer.document.Part;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        FileManager fm = new FileManager();
        ArrayList<ArrayList<Double>> staticData = fm.readNumericFile("Static100.txt");
        ArrayList<ArrayList<Double>> dynamicData = fm.readNumericFile("Dynamic100.txt");

        int N = staticData.get(0).get(0).intValue();
        int L = staticData.get(1).get(0).intValue();
        double r_c = 5;

        ParticleGenerator pg = new ParticleGenerator(0.25);

        ArrayList<Particle> particles = new ArrayList<>();
        ArrayList<ArrayList<Particle>> generatedParticles = pg.generate(N,L,0.25);
        if (staticData.size() < 5) {
            fm.createResults(generatedParticles, "generatedParticles.txt");
            for (ArrayList<Particle> arr_p: generatedParticles)
                for (Particle p: arr_p)
                    particles.add(p);
        } else {
            for (int i = 1; i < 1 + N; i++)
                particles.add(new Particle(dynamicData.get(i).get(1), dynamicData.get(i).get(0), staticData.get(i+1).get(0), i - 1));
        }

        long startTime = System.currentTimeMillis();
        CellIndex cellIndex = new CellIndex(N, L,5, r_c, true, particles);
        cellIndex.setNeighbour();
        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        fm.createResults(cellIndex.getOutput(),  System.nanoTime(), "particles.txt");
        fm.createResults(cellIndex.getOutput(), 0, "particles.txt");


       // test();
    }






    public static void test(){

        ArrayList<Integer> number = new ArrayList<>();
        number.add(100);
        number.add(500);
        number.add(1000);
        number.add(5000);
        number.add(10000);
        number.add(20000);

        for(Integer N : number){
            ArrayList<Particle> p= new ArrayList<>();
            for (int i = 0; i < N; i++) {
                double x = Math.random() * 20;
                double y = Math.random() * 20;
                p.add(new Particle(x, y, 0.25, i + 1));
            }

            for(int M = 1; M <= 15; M++){
                long startTime = System.currentTimeMillis();
                CellIndex cellIndex = new CellIndex(N, 20,M, 1, true, p);
                cellIndex.setNeighbour();
                long endTime = System.currentTimeMillis();
                System.out.println("N "+  N  +" and M "+ M + " took " + (endTime - startTime) + " milliseconds");
            }

            System.out.println("\n");

        }

    }


}
