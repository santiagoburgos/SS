public class OscilatorData {
    private float mass;
    private float k;
    private float phi;
    private float tf;
    private float r0;

    public OscilatorData(float mass, float k, float phi, float tf, float r0) {
        this.mass = mass;
        this.k = k;
        this.phi = phi;
        this.tf = tf;
        this.r0 = r0;
    }

    public float getMass() {
        return mass;
    }

    public float getK() {
        return k;
    }

    public float getPhi() {
        return phi;
    }

    public float getTf() {
        return tf;
    }

    public float getR0() {
        return r0;
    }
}
