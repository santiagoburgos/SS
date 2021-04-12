package main.java;

import java.util.ArrayList;
import java.util.List;



public class BrownianMovement {
    private static final int MAX_VEL = 2;
    private static final double R1 = 0.2;
    private static final double R2 = 0.7;
    private static final double M1 = 0.9;
    private static final double M2 = 2.0;


    private int maxSize;
    private int N;
    private List<Particle> particles;

    public BrownianMovement(int maxSize, int N){
        this.maxSize = maxSize;
        this.N = N;
        this.particles = new ArrayList<>();
    }

    public void generateParticles(){
        particles.add(new Particle(maxSize/2.0,maxSize/2.0,0.0,0.0, R2, M2));
        for (int i = 0; i < N; i++){
            double xPos = Math.random() * this.maxSize;
            double yPos = Math.random() * this.maxSize;
            double xVel = Math.random() * MAX_VEL * 2;
            xVel -= MAX_VEL;
            double yVel = Math.random() * MAX_VEL * 2;
            yVel -= MAX_VEL;
            //TODO EVITAR SUPERPOSICIONES
            particles.add(new Particle(xPos, yPos, xVel, yVel, R1, M1));
        }
    }

    public Double nextCollision(){
        Double nextTimeCollision = 0.0;
        Double auxTime;
        for (int i = 0; i < N; i++){
            nextTimeCollision = particles.get(i).wallCollision(maxSize);
            for (Particle p : particles){
                auxTime = particles.get(i).particleCollision(p);
                if (auxTime != null && auxTime < nextTimeCollision) nextTimeCollision = auxTime;
            }
        }
        return nextTimeCollision;
    }

    public void updateStatus(Double timeCollision){
        for (int i = 0; i < N; i++){
            particles.get(i).newPosition(timeCollision);
            //TODO ACTUALIZAR VELOCIDADES 
        }
    }




}
