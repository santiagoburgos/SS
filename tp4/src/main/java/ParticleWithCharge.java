public class ParticleWithCharge extends Particle{
    private double charge;
    public ParticleWithCharge(double xPos, double yPos, double xVel, double yVel, double radius, double mass, double charge) {
        super( xPos, yPos,  xVel,  yVel, (float) radius,(float) mass);
        this.charge = charge;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }
}
