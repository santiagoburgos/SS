package main.java;

import java.util.HashMap;
import java.util.List;

public class Particle {
    private float xPos, yPos;
    private float xVel, yVel;
    private float radius;
    private float mass;
    private float collisionTime;

    public Particle(float xPos, float yPos, float xVel, float yVel, float radius, float mass){
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVel = xVel;
        this.yVel = yVel;
        this.radius = radius;
        this.mass = mass;
    }

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public float getXVel() {
        return xVel;
    }

    public float getYVel() {
        return yVel;
    }

    public float getRadius() {
        return radius;
    }

    public float getMass() {
        return mass;
    }

    public void setXVel(float vel) {
        xVel = vel;
    }

    public void setYVel(float vel) {
        yVel = vel;
    }



    public Float particleCollision(Particle particle){



        float deltaR[] = {xPos - particle.getXPos(), yPos - particle.getYPos()};
        float deltaV[] = {xVel - particle.getXVel(), yVel - particle.getYVel()};
        float esc = deltaV[0] * deltaR[0] + deltaV[1] * deltaR[1];

        if (esc >= 0){
            return Float.MAX_VALUE;
        }
        float sigma = radius + particle.getRadius();
        float escDeltaV = (float) (Math.pow(deltaV[0], 2) + Math.pow(deltaV[1], 2));
        float escDeltaR = (float) (Math.pow(deltaR[0], 2) + Math.pow(deltaR[1], 2));

        float d = (float) (Math.pow(esc, 2) - escDeltaV * ( escDeltaR - Math.pow(sigma, 2)));
        if (d < 0){
            return Float.MAX_VALUE;
        }
        float collisionTime = (float) -((esc + Math.sqrt(d)) / escDeltaV);


        return  collisionTime;
    }


    public void newPosition(Float timeCollision){
        xPos = xPos + (xVel * timeCollision);
        yPos = yPos + (yVel * timeCollision);
    }



    public Float verticalWallCollision(float size){

        Float collisionTime;

        //verticales
        Float collisionTimeXver=Float.MAX_VALUE;

        if (xVel > 0.0f){
            collisionTimeXver =  (size - radius - xPos) / xVel;
        } else if (xVel < 0.0f) {
            collisionTimeXver = (radius - xPos) / xVel;
        }


        collisionTime = collisionTimeXver ;

        return collisionTime;
    }

    public Float horizontalWallCollision(float size){

        Float collisionTime;

        //horizontales
        Float collisionTimeYhor=Float.MAX_VALUE;

        if (yVel > 0.0f){
            collisionTimeYhor =  (size - radius - yPos) / yVel;
        } else if (yVel < 0.0f){
            collisionTimeYhor = (radius - yPos) / yVel;
        }
        collisionTime = collisionTimeYhor;



        return collisionTime;
    }


}
