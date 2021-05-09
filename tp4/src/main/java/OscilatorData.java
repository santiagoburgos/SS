public class OscilatorData {
    private double mass;
    private double k;
    private double phi;
    private double tf;
    private double r0;

    public OscilatorData(double mass, double k, double phi, double tf, double r0) {
        this.mass = mass;
        this.k = k;
        this.phi = phi;
        this.tf = tf;
        this.r0 = r0;
    }

    public double getMass() {
        return mass;
    }

    public double getK() {
        return k;
    }

    public double getPhi() {
        return phi;
    }

    public double getTf() {
        return tf;
    }

    public double getR0() {
        return r0;
    }
}
