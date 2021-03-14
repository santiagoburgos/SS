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
    private int radius;
    private int L;

    public Graphic(int size, ArrayList<Particle> particles){
        this.L = size;
        setSize(L, L);
        this.particles = particles;
        for (Particle p : this.particles){
            System.out.println("SALIDA: X: " + p.getX() + " Y: " + p.getY() + " RADIUS: " + p.getRadius());
        }
        //createParticles();

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
                        if (lastPoint.getX() <= p.getX() + radius && lastPoint.getX() >= p.getX() - radius){
                            if (lastPoint.getY() <= p.getY() + radius && lastPoint.getY() >= p.getY() - radius){
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

    private void resetColor(ArrayList<Particle> colorChanged) {
        Graphics g = getGraphics();
        for (Particle rel : colorChanged){
            g.drawOval((int) rel.getX(), (int) rel.getY(), (int) rel.getRadius(), (int) rel.getRadius());
        }

    }

/*
    private void createParticles() {
        this.particles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            double ranx = Math.random() * this.L;
            double rany = Math.random() * this.L;
            System.out.println("Particula creada en x: " + ranx + "\ty: " + rany);
            this.particles.add(new Particle(ranx, rany, i + 1));
        }
    }
*/
    //grafica las particulas
    public void graphicCircle(){
        Graphics g = getGraphics();
        for (Particle p : particles){
            g.drawOval((int) p.getX(), (int) p.getY(), (int) p.getRadius(), (int) p.getRadius());
        }
    }

    //cambia el color al tocarla
    public void changeColor(Particle p, ArrayList<Particle> points){
        Graphics g = getGraphics();
        g.setColor(Color.green);
        for (Particle rel : points){
            g.drawOval((int) rel.getX(), (int) rel.getY(), this.radius, this.radius);
        }
        g.setColor(Color.red);
        g.drawOval((int) p.getX(), (int) p.getY(), this.radius, this.radius);
    }


}
