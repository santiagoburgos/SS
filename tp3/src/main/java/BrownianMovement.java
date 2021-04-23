package main.java;

import java.util.*;

import static java.lang.Math.*;


public class BrownianMovement {
    private static final float MAXSIZE = 6f;
    private static final float R1 = 0.2f;
    private static final float R2 = 0.7f;
    private static final float M1 = 0.9f;
    private static final float M2 = 2.0f;

    private int maxIterations = 100000; //max events
    private int maxSimulatedTime = 200; //max events

    private int maxAttempts = 100000; //generating particles

    private double maxVel = 2;
    private int N;
    private List<Particle> particles;

    public SortedMap<Float, List<Particle>> states = new TreeMap<>();
    float time = 0;

    int iterations;
    public BrownianMovement(int N, float maxVel) {
        this.N = N;
        this.maxVel = maxVel;
        this.particles = new ArrayList<>();

        generateParticles();

        List<Particle> copy = new ArrayList<>();
        for (Particle p : particles) {
            copy.add(new Particle(p.getXPos(), p.getYPos(), p.getXVel(), p.getYVel(), p.getRadius(), p.getMass()));
        }
        states.put(time, copy);


        while(!finish() && (iterations < maxIterations)  && (time < maxSimulatedTime)){

            float next = nextCollisionTime();
            updateStatus(next);
            generateCollision();

            iterations +=1;
        }

        System.out.println("iteration " + iterations);
        System.out.println("finish simulation time " + time);
    }

    private boolean finish(){

        float x = particles.get(0).getXPos();
        float y =  particles.get(0).getYPos();



        if( x<=R2 || y<=R2 || y >= MAXSIZE-R2 || x >= MAXSIZE-R2){
            System.out.println("flag " + iterations);
            return true;
        }


        return false;
    }






    private void generateParticles() {
        particles.add(new Particle(MAXSIZE / 2.0f, MAXSIZE / 2.0f, 0.0f, 0.0f, R2, M2));

        int particlesNumber = 1;
        int attempt = 0;

        Random r = new Random();

        while (particlesNumber != N && attempt != maxAttempts) {

            float xPos = (float) (R1 + ((MAXSIZE - R1) - R1) * r.nextDouble());
            float yPos = (float) (R1 + ((MAXSIZE - R1) - R1) * r.nextDouble());

            if (addParticle(xPos, yPos, R1, M1)) {
                particlesNumber += 1;
            }
            attempt += 1;

        }
    }

    private boolean addParticle(float xPos, float yPos, float radius, float mass) {
        for (Particle p : particles) {

            float distance = (float) (sqrt((yPos - p.getYPos()) * (yPos - p.getYPos()) + (xPos - p.getXPos()) * (xPos - p.getXPos())) - (p.getRadius() + R1));
            if (distance < 0)
                return false;
        }
        Random r = new Random();
        float velocity = (float) (maxVel * r.nextDouble());
        float angle = (float) Math.toRadians(Math.random() * 360);

        float xVel = (float) (velocity * Math.cos(angle));
        float yVel = (float) (velocity * Math.sin(angle));

        particles.add(new Particle(xPos, yPos, xVel, yVel, R1, M1));

        return true;
    }


    List<Collision> collisions;

    private float nextCollisionTime() {
        float nextEventTime = Float.MAX_VALUE;
        collisions = new ArrayList<>();


        for (int i = 0; i < particles.size(); i++) {


            float verticalWallTime = particles.get(i).verticalWallCollision(MAXSIZE);
            float horizontalWallTime = particles.get(i).horizontalWallCollision(MAXSIZE);


            float particleCollisionTime = Float.MAX_VALUE;
            int selectedJ = 0;

            for (int j = i + 1; j < particles.size(); j++) {

                float particleTime = particles.get(i).particleCollision(particles.get(j));



                if (particleTime < particleCollisionTime) {
                    particleCollisionTime = particleTime;
                    selectedJ = j;
                }
            }

            float eventTime = Math.min(Math.min(horizontalWallTime, verticalWallTime), particleCollisionTime);

            if (eventTime <= nextEventTime) {

                if (eventTime < nextEventTime) {
                    collisions.clear();
                }

                nextEventTime = eventTime;


                if ((eventTime == verticalWallTime) && eventTime != Float.MAX_VALUE) {
                    collisions.add(new Collision(verticalWallTime, COLLISIONTYPE.VERWALL, particles.get(i)));
                }
                if ((eventTime == horizontalWallTime) && eventTime != Float.MAX_VALUE) {
                    collisions.add(new Collision(horizontalWallTime, COLLISIONTYPE.HORWALL, particles.get(i)));
                }
                if ((eventTime == particleCollisionTime) && eventTime != Float.MAX_VALUE) {
                    collisions.add(new Collision(eventTime, COLLISIONTYPE.PARTCOL, particles.get(i), particles.get(selectedJ)));
                }
            }
        }

        return nextEventTime;
    }


    private void updateStatus(float timeCollision) {
        for (Particle p : particles) {
            p.newPosition(timeCollision);
        }

        time += timeCollision;
        List<Particle> copy = new ArrayList<>();
        for (Particle p : particles) {
            copy.add(new Particle(p.getXPos(), p.getYPos(), p.getXVel(), p.getYVel(), p.getRadius(), p.getMass()));
        }
        states.put(time, copy);
    }


    private void generateCollision() {
        for (Collision c : collisions) {


            if (c.type == COLLISIONTYPE.VERWALL) {
                float xVel = c.p1.getXVel() * -1;
                c.p1.setXVel(xVel);
            } else if (c.type == COLLISIONTYPE.HORWALL) {
                float yVel = c.p1.getYVel() * -1;
                c.p1.setYVel(yVel);
            } else {

                //2 es i
                //1 es j

                float deltaR[] = {c.p1.getXPos() - c.p2.getXPos(), c.p1.getYPos() - c.p2.getYPos()}; //deltaX, deltaY
                float deltaV[] = {c.p1.getXVel() - c.p2.getXVel(), c.p1.getYVel() - c.p2.getYVel()}; //deltaVX, deltaVY
                float esc = deltaV[0] * deltaR[0] + deltaV[1] * deltaR[1]; //deltaV.deltaR

                float sigma = c.p1.getRadius() + c.p2.getRadius();

                float m1 = c.p1.getMass();
                float m2 = c.p2.getMass();

                float j = (2 * m1 * m2 * esc) / (sigma * (m1 + m2));
                float jX = (j * (c.p1.getXPos() - c.p2.getXPos())) / sigma;
                float jY = (j * (c.p1.getYPos() - c.p2.getYPos())) / sigma;


                //j
                float p1xVel = c.p1.getXVel() - (jX / m1);
                float p1yVel = c.p1.getYVel() - (jY / m1);

                c.p1.setXVel(p1xVel);
                c.p1.setYVel(p1yVel);

                //i
                float p2xVel = c.p2.getXVel() + (jX / m2);
                float p2yVel = c.p2.getYVel() + (jY / m2);

                c.p2.setXVel(p2xVel);
                c.p2.setYVel(p2yVel);
            }

        }


    }


}


enum COLLISIONTYPE{
    VERWALL,
    HORWALL,
    PARTCOL
}


class Collision{

     COLLISIONTYPE type;
     Particle p1 = null;
     Particle p2 = null;
    float time;

    public Collision(float time, COLLISIONTYPE collisionType, Particle p1){
        this.type = collisionType;
        this.p1 = p1;
        this.time = time;
    }
    public Collision(float time, COLLISIONTYPE collisionType, Particle p1, Particle p2){
        this.type = collisionType;
        this.p1 = p1;
        this.p2 = p2;
        this.time = time;
    }
}

