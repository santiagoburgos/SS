import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Graphic extends JPanel {

    private  Point lastPoint;
    private ArrayList<ArrayList<Point>> particles;
    private int radius;
    private int L;

    public Graphic(int size, int radius){
        this.L = size;
        setSize(L, L);
        this.radius = radius;
        createParticles();

        addMouseListener(new MouseAdapter() {

            ArrayList<Point> colorChanged = new ArrayList<>();
            public void mousePressed(MouseEvent e){
                lastPoint = new Point(e.getX(), e.getY());
                if(e.getButton() == MouseEvent.BUTTON3){
                    System.out.println("CLICK DERECHO");
                    resetColor(colorChanged);
                    colorChanged.clear();
                }
                if (e.getButton() == MouseEvent.BUTTON1 && colorChanged.isEmpty()){
                    for (ArrayList<Point> points : particles){
                        for (Point p : points){
                            if (lastPoint.getX() <= p.getX() + radius && lastPoint.getX() >= p.getX() - radius){
                                if (lastPoint.getY() <= p.getY() + radius && lastPoint.getY() >= p.getY() - radius){
                                    changeColor(p, points);
                                    colorChanged.addAll(points);
                                }
                            }
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

    private void resetColor(ArrayList<Point> colorChanged) {
        Graphics g = getGraphics();
        for (Point rel : colorChanged){
            g.drawOval((int) rel.getX(), (int) rel.getY(), this.radius, this.radius);
        }

    }

    //deberia tomar el dato del archivo de salida
    private void createParticles() {
        this.particles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            double ranx = Math.random() * this.L;
            double rany = Math.random() * this.L;
            System.out.println("Particula creada en x: " + ranx + "\ty: " + rany);
            this.particles.add(new ArrayList<>());
            this.particles.get(i).add(new Point((int) ranx, (int) rany));
        }
    }

    //grafica las particulas
    public void graphicCircle(int r){
        Graphics g = getGraphics();
        for (ArrayList<Point> points : particles){
            for (Point p : points){
                g.drawOval((int) p.getX(), (int) p.getY(), this.radius, this.radius);
            }
        }
    }

    //cambia el color al tocarla
    public void changeColor(Point p, ArrayList<Point> points){
        Graphics g = getGraphics();
        g.setColor(Color.green);
        for (Point rel : points){
            g.drawOval((int) rel.getX(), (int) rel.getY(), this.radius, this.radius);
        }
        g.setColor(Color.red);
        g.drawOval((int) p.getX(), (int) p.getY(), this.radius, this.radius);
        //TODO: Retornar las particulas que cambiaron de color para reestablecerlas luego
    }


}
