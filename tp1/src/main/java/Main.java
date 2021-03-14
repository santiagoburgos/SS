import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
       /*
        FileManager fm = new FileManager();
        ArrayList<ArrayList<Double>> entries = fm.readNumericFile("entrada1.txt");
        int L = entries.get(0).get(0).intValue();
        int N = entries.get(1).get(0).intValue();
        int M = entries.get(2).get(0).intValue();
        double r_c = entries.get(3).get(0);
        ArrayList<Particle> particles = new ArrayList<>();
        for (int i = 4; i < 4 + N; i++)
            particles.add(new Particle(entries.get(i).get(1), entries.get(i).get(0), i - 4));



        CellIndex cellIndex = new CellIndex(N, L, 1, r_c, true, particles);
        //randomize particles positions
        //CellIndex cellIndex = new CellIndex(1000, 20, 5, 0.5, true);
        //cellIndex.printParticles();
        cellIndex.setNeighbour();
        cellIndex.getOutput();
         */

        FileManager fm = new FileManager();
        ArrayList<ArrayList<Double>> staticData = fm.readNumericFile("Static100.txt");
        ArrayList<ArrayList<Double>> dynamicData = fm.readNumericFile("Dynamic100.txt");

        int N = staticData.get(0).get(0).intValue();
        int L = staticData.get(1).get(0).intValue();
        double time = dynamicData.get(0).get(0).doubleValue();
        double r_c = 1;

        ArrayList<Particle> particles = new ArrayList<>();
        for (int i = 1; i < 1 + N; i++)
            particles.add(new Particle(dynamicData.get(i).get(1), dynamicData.get(i).get(0), staticData.get(i+1).get(0), i - 1));

        CellIndex cellIndex = new CellIndex(N, L,25, r_c, false, particles);
        cellIndex.setNeighbour();
        cellIndex.getOutput();

        /////////////////////////

        


        JFrame frame = new JFrame("Graphics");
        frame.getContentPane().add(new Graphic(450, 20), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        frame.setSize(d);
        frame.setVisible(true);

    }
}
