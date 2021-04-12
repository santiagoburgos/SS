package main.java;

public class Particle {
    private double xPos, yPos;
    private double xVel, yVel;
    private double radius;
    private double mass;
    private double collisionTime;

    public Particle(Double xPos, Double yPos, Double xVel, Double yVel, Double radius, Double mass){
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVel = xVel;
        this.yVel = yVel;
        this.radius = radius;
        this.mass = mass;
    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public double getXVel() {
        return xVel;
    }

    public double getYVel() {
        return yVel;
    }

    public double getRadius() {
        return radius;
    }

    public double getMass() {
        return mass;
    }

    public Double wallCollision(int size){
        Double auxTime;
        if (xVel > 0.0){
            collisionTime =  (size - radius - xPos) / xVel;
        } else {
            auxTime = (radius - xPos) / xVel;
            if (auxTime < collisionTime) collisionTime = auxTime;
        }
        if (yVel > 0.0){
            auxTime = (size - radius - yPos) / yVel;
            if (auxTime < collisionTime) collisionTime = auxTime;
        } else {
            auxTime = (radius - yPos) / yVel;
            if (auxTime < collisionTime) collisionTime = auxTime;
        }
        return collisionTime;
    }

    public Double particleCollision(Particle particle){
        Double deltaV[] = {xVel - particle.getXVel(), yVel - particle.getYVel()};
        Double deltaR[] = {xPos - particle.getXPos(), yPos - particle.getYPos()};
        Double esc = deltaV[0] * deltaR[1] + deltaV[1] * deltaR[1];
        if (esc >= 0){
            return null;
        }
        Double sigma = radius + particle.getRadius();
        Double escDeltaV = Math.pow(deltaV[0], 2) + Math.pow(deltaV[1], 2);
        Double escDeltaR = Math.pow(deltaR[0], 2) + Math.pow(deltaR[1], 2);
        Double d = Math.pow(esc, 2) - escDeltaV * ( escDeltaR - Math.pow(sigma, 2));
        if (d < 0){
            return null;
        }
        return  (esc + Math.sqrt(d)) / escDeltaV;
    }

    public void newPosition(Double timeCollision){
        xPos = xPos + xVel * timeCollision;
        yPos = yPos + yVel * timeCollision;
    }
}
