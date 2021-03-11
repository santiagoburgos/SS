import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Graphic extends JPanel {

    private  Point lastPoint;
    private Point particles[];
    private int radius;
    private int M;
    public Graphic(int size, int radius){
        this.M = size;
        setSize(M, M);
        this.radius = radius;
        createParticles();

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e){
                lastPoint = new Point(e.getX(), e.getY());
                for (Point p : particles){
                    System.out.println("X: " + p.getX() + "\tY: " + p.getY());
                    System.out.println("puntoX: " + lastPoint.getX() + "\tpuntoY: " + lastPoint.getY());
                    if (lastPoint.getX() <= p.getX() + radius && lastPoint.getX() >= p.getX() - radius){
                        if (lastPoint.getY() <= p.getY() + radius && lastPoint.getY() >= p.getY() - radius){
                            changeColor(p);
                            System.out.println("ENTROOO");
                        }
                    }
                }
            }
        });

        JButton button = new JButton("Iniciar");
        button.setSize(100, 50);
        button.setVisible(true);
        button.setLocation(new Point(50, 50));
        this.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphicCircle(10);
                button.setVisible(false);
            }
        });
    }

    //deberia tomar el dato del archivo de salida
    private void createParticles() {
        particles = new Point[10];
        for (int i = 0; i < 10; i++) {
            double ranx = Math.random() * this.M + 50;
            double rany = Math.random() * this.M + 50;
            System.out.println("Particula creada en x: " + ranx + "\ty: " + rany);
            this.particles[i] =  new Point((int) ranx, (int) rany);
        }
    }

    //grafica las particulas
    public void graphicCircle(int r){
        Graphics g = getGraphics();
        for (Point p : particles){
            g.drawOval((int) p.getX(), (int) p.getY(), this.radius, this.radius);
        }
    }

    //cambia el color al tocarla
    public void changeColor(Point p){
        Graphics g = getGraphics();
        g.setColor(Color.red);
        g.drawOval((int) p.getX(), (int) p.getY(), this.radius, this.radius);

    }


}
