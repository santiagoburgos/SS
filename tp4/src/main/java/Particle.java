public class Particle {
    private float xPos, yPos;
    private float xVel, yVel;
    private float radius;
    private float mass;

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


}
