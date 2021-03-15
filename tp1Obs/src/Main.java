import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        FileManager fm = new FileManager();
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
