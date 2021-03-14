import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Character.isWhitespace;

public class Graphic extends JPanel {

    private  Point lastPoint;
    private ArrayList<Particle> particles;
    private int L;

    public Graphic(int size, ArrayList<Particle> particles){
        this.L = size;
        setSize(L, L);
        this.particles = particles;

        addMouseListener(new MouseAdapter() {
            ArrayList<Particle> colorChanged = new ArrayList<>();
            public void mousePressed(MouseEvent e){
                lastPoint = new Point(e.getX(), e.getY());
                if(e.getButton() == MouseEvent.BUTTON3){
                    resetColor(colorChanged);
                    colorChanged.clear();
                }
                if (e.getButton() == MouseEvent.BUTTON1 && colorChanged.isEmpty()){
                    for (Particle p : particles){
                        if (lastPoint.getX() <= p.getX() + p.getRadius() && lastPoint.getX() >= p.getX() - p.getRadius()){
                            if (lastPoint.getY() <= p.getY() + p.getRadius() && lastPoint.getY() >= p.getY() - p.getRadius()){
                                changeColor(p, p.getNeighbour());
                                ArrayList<Particle> aux = new ArrayList<>();
                                aux.add(p);
                                aux.addAll(p.getNeighbour());
                                colorChanged.addAll(aux);
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
                graphicCircle();
                button.setVisible(false);
            }
        });
    }

    //dibuja un ciruclo indicando centro, radio y color
    private void createCircle(double x, double y, double radius, Color color){
        Graphics g = getGraphics();
        g.setColor(color);
        g.drawOval((int) (x - radius), (int) (y - radius), (int) radius * 2, (int) radius * 2);

    }

    //resetea el color luego de elegir una particula
    private void resetColor(ArrayList<Particle> colorChanged) {
        for (Particle rel : colorChanged){
            createCircle(rel.getX(), rel.getY(), rel.getRadius(), Color.blue);
        }

    }

    //grafica las particulas
    public void graphicCircle(){
        for (Particle p : particles){
            createCircle(p.getX(), p.getY(), p.getRadius(), Color.blue);
        }
    }

    //cambia el color al tocarla
    public void changeColor(Particle p, ArrayList<Particle> points){
        Graphics g = getGraphics();
        createCircle(p.getX(), p.getY(), p.getRadius(), Color.red);
        g.setColor(Color.green);
        for (Particle rel : points){
            System.out.println("VECINOS: " + rel.getX() + ", " + rel.getY());
            createCircle(rel.getX(), rel.getY(), rel.getRadius(), Color.green);
        }

    }


}
