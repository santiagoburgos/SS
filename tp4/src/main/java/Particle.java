public class Particle {
    private double xPos, yPos;
    private double xVel, yVel;
    private double radius;
    private double mass;

    public Particle(double xPos, double yPos, double xVel, double yVel, double radius, double mass){
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

    public void setXVel(double vel) {
        xVel = vel;
    }

    public void setYVel(double vel) {
        yVel = vel;
    }

    public void setXPos(double pos) {
        xPos = pos;
    }

    public void setYPos(double pos) {
        yPos = pos;
    }


}
