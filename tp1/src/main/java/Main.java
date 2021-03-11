import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {

    public static void main(String[] args) {

        CellIndex cellIndex = new CellIndex(10, 10, 10, 0.5);
        cellIndex.printParticles();
        cellIndex.setNeighbour();
        cellIndex.getOutput();
        /*
        ////////////////prueba grafica ////////////////////
        JFrame frame = new JFrame("Graphics");
        frame.getContentPane().add(new Graphic(450, 20), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        frame.setSize(d);
        frame.setVisible(true);
        ////////////////////////////////////////////////////
*/
    }
}
