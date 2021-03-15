import com.sun.xml.internal.ws.wsdl.writer.document.Part;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        FileManager fm = new FileManager();
        ArrayList<ArrayList<Double>> staticData = fm.readNumericFile("Static100.txt");
        ArrayList<ArrayList<Double>> dynamicData = fm.readNumericFile("Dynamic100.txt");

        int L = staticData.get(0).get(0).intValue();
        int N = staticData.get(1).get(0).intValue();
        double r_c = staticData.get(2).get(0).doubleValue();
        int M = staticData.get(3).get(0).intValue();
        double r = 0.25;
        boolean hasR = false;
        if (staticData.size() > 4) {
            hasR = true;
            r = staticData.get(4).get(0).doubleValue();
        }

        ParticleGenerator pg = new ParticleGenerator(r, hasR);

        ArrayList<Particle> particles = new ArrayList<>();
        ArrayList<ArrayList<Particle>> generatedParticles = pg.generate(N,L);
        if (staticData.size() < 6) {
            fm.createResults(generatedParticles, "Dynamic.txt");
            for (ArrayList<Particle> arr_p: generatedParticles)
                for (Particle p: arr_p)
                    particles.add(p);
        } else {
            for (int i = 6; i < 6 + N; i++)
                particles.add(new Particle(dynamicData.get(i).get(1), dynamicData.get(i).get(0), staticData.get(i+1).get(0), i - 1));
        }

        long startTime = System.currentTimeMillis();
        CellIndex cellIndex = new CellIndex(N, L,M, r_c, true, particles);
        cellIndex.setNeighbour();
        long endTime = System.currentTimeMillis();
        System.out.println("Periodic took " + (endTime - startTime) + " milliseconds");
        fm.createResults(cellIndex.getOutput(),  0, "particlesPeriodic.txt");

        cellIndex = new CellIndex(N, L,M, r_c, false, particles);
        cellIndex.setNeighbour();
        System.out.println("Not periodic took " + (System.currentTimeMillis() - endTime) + " milliseconds");
        fm.createResults(cellIndex.getOutput(),  0, "particlesNotPeriodic.txt");
    }
}
