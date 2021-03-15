import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Character.isWhitespace;

public class Graphic extends JPanel {

    private final static int RAD = 2;
    private  Point lastPoint;
    private ArrayList<Particle> particles;
    private int L;
    private int multi;

    public Graphic(int size, ArrayList<Particle> particles, int multi){
        this.multi = multi;
        this.L = size * multi;
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
                    System.out.println("Click: " + lastPoint.getX() + ", " + lastPoint.getY());
                    for (Particle p : particles){
                        //System.out.println("X: " + p.getX() * multi + " Y: " + p.getY() * multi);
                        System.out.println((p.getRadius()) * multi);
                        if (lastPoint.getX() <= (p.getX() + RAD) * multi && lastPoint.getX() >= (p.getX() - RAD) * multi){
                            if (lastPoint.getY() <= (p.getY() + RAD) * multi && lastPoint.getY() >= (p.getY() - RAD) * multi){
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
    private void createCircle(double x, double y, Color color){
        Graphics g = getGraphics();
        g.setColor(color);
        int diam = RAD * 2 * this.multi;
        g.fillOval((int) (x - RAD) * this.multi, (int) (y - RAD) * this.multi, diam, diam);
    }

    //resetea el color luego de elegir una particula
    private void resetColor(ArrayList<Particle> colorChanged) {
        for (Particle rel : colorChanged){
            createCircle(rel.getX(), rel.getY(), Color.blue);
        }

    }

    //grafica las particulas
    public void graphicCircle(){
        for (Particle p : particles){
            createCircle(p.getX(), p.getY(), Color.blue);
        }
    }

    //cambia el color al tocarla
    public void changeColor(Particle p, ArrayList<Particle> points){
        createCircle(p.getX(), p.getY(), Color.red);
        for (Particle rel : points){
            System.out.println("VECINOS: " + rel.getX() + ", " + rel.getY());
            createCircle(rel.getX(), rel.getY(), Color.green);
        }

    }


}
