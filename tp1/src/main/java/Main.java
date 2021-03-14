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
        double time = dynamicData.get(0).get(0).doubleValue();
        double r_c = 5;

        ArrayList<Particle> particles = new ArrayList<>();
        for (int i = 1; i < 1 + N; i++)
            particles.add(new Particle(dynamicData.get(i).get(1), dynamicData.get(i).get(0), staticData.get(i+1).get(0), i - 1));

        CellIndex cellIndex = new CellIndex(N, L,5, r_c, true, particles);
        cellIndex.setNeighbour();
        cellIndex.getOutput();

        ArrayList<Particle> results = fm.readResultsFile("particles.txt", 3);
        if (results.size() == 0)
            return;

        JFrame frame = new JFrame("Graphics");
        frame.getContentPane().add(new Graphic(450, results, 10), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        frame.setSize(d);
        frame.setVisible(true);

    }
}
