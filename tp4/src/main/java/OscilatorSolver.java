import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class OscilatorSolver {

    private OscilatorData od;
    private double dt;
    ArrayList<Double> originalR = new ArrayList<>();
    ArrayList<Double> verletR = new ArrayList<>();
    ArrayList<Double> verletV = new ArrayList<>();
    ArrayList<Double> beemanR = new ArrayList<>();
    ArrayList<Double> beemanV = new ArrayList<>();
    ArrayList<Double> gpco5R = new ArrayList<>();
    ArrayList<Double> gpco5V = new ArrayList<>();

    public OscilatorSolver(OscilatorData od, double dt) {
        this.od = od;
        this.dt = dt;
        this.originalR.add(od.getR0());
        double initialV = (-1) * od.getR0() * (od.getPhi()/ (2 * od.getMass()));
        // Euler for previous values
        double prevR = od.getR0() - dt * initialV + Math.pow(dt, 2) * getForce(od.getR0(), initialV) / (2 * od.getMass());
        double prevV = initialV - (dt / od.getMass()) * getForce(od.getR0(), initialV);
        this.verletR.add(prevR);
        this.verletR.add(od.getR0());
        this.verletV.add(prevV);
        this.verletV.add(initialV);
        this.beemanR.add(prevR);
        this.beemanR.add(od.getR0());
        this.beemanV.add(prevV);
        this.beemanV.add(initialV);
        this.gpco5R.add(prevR);
        this.gpco5R.add(od.getR0());
        this.gpco5V.add(prevV);
        this.gpco5V.add(initialV);
    }

    private double getForce(double r, double v) {
        return - od.getK() * r - od.getPhi() * v;
    }

    private double getOriginalR(float t) {
        double wPrime = Math.sqrt(((od.getK() / od.getMass()) - (Math.pow(od.getPhi(), 2) / Math.pow(2 * od.getMass(), 2))));
        return Math.exp(- t * (od.getPhi() / (2 * od.getMass()))) * Math.cos(wPrime * t);
    }

    private void getVerlet() {
        double rt = verletR.get(verletR.size() - 1);
        double vt = verletV.get(verletV.size() - 1);
        double prevRt = verletR.get(verletR.size() - 2);

        double finalR = 2 * rt - prevRt + ( (Math.pow(dt, 2) / od.getMass())) * getForce(rt, vt);
        double finalV = (rt - prevRt) / (2 * dt);
        verletR.add(finalR);
        verletV.add(finalV);
    }

    private void getBeeman() {
        double rt = beemanR.get(beemanR.size() - 1);
        double vt = beemanV.get(beemanV.size() - 1);
        double prevRt = verletR.get(verletR.size() - 2);
        double prevVt = verletV.get(verletV.size() - 2);
        double finalR = rt + vt * dt  + (2 * getForce(rt, vt) * Math.pow(dt, 2)) / (3 * od.getMass()) - Math.pow(dt, 2) * getForce(prevRt, prevVt) / (6 * od.getMass());

        double predictedV = vt + (3 * getForce(rt, vt) * dt) / 2 - dt * getForce(prevRt, prevVt) / (2 * od.getMass());
        double correctedV = vt + getForce(finalR, predictedV)  * dt / 3 + (5 * getForce(rt, vt) * dt) / 6 - Math.pow(dt, 2) * getForce(prevRt, prevVt) / (6 * od.getMass());

        beemanR.add(finalR);
        beemanV.add(correctedV);
    }

    private void getGPCO5() {
        double rt = gpco5R.get(gpco5R.size() - 1);
        double vt = gpco5V.get(gpco5V.size() - 1);
        // First: Predict
        // return - od.getK() * r - od.getPhi() * v;
        double r2 = getForce(rt, vt) / od.getMass();
        double r3 = - od.getK() * vt / od.getMass() - od.getPhi() * r2 / od.getMass();
        double r4 = - od.getK() * r2 / od.getMass() - od.getPhi() * r3 / od.getMass();
        double r5 = - od.getK() * r3 / od.getMass() - od.getPhi() * r4 / od.getMass();

        double rP = rt + vt * dt + r2 * Math.pow(dt, 2) / 2 + r3 * Math.pow(dt, 3) / 6 + r4 * Math.pow(dt, 4) / 24 + r5 * Math.pow(dt, 5) / 120;
        double r1P = vt + r2 * dt + r3 * Math.pow(dt, 2) / 2 + r4 * Math.pow(dt, 3) / 6 + r5 * Math.pow(dt, 4) / 24;
        double r2P = r2 + r3 * dt + r4 * Math.pow(dt, 2) / 2 + r5 * Math.pow(dt, 3) / 6;
        double r3P = r3 + r4 * dt + r5 * Math.pow(dt, 2) / 2;
        double r4P = r4 + r5 * dt;
        double r5P = r5;

        double deltaR2 = (getForce(rP, r1P) / od.getMass() - r2P) * (float) Math.pow(dt, 2) / 2;
        double finalR = rP + deltaR2 * 3 / 16;
        double finalV = r1P + (251 * deltaR2) / (360 + dt);
        gpco5R.add(finalR);
        gpco5V.add(finalV);
    }

    public void solve() throws IOException {
        float t = 0;
        while (t < od.getTf()) {
            t += dt;
            originalR.add(getOriginalR(t));
            getVerlet();
            getBeeman();
            getGPCO5();
        }
        /*createEDOFile("originalR", originalR);
        createEDOFile("verletR", verletR);
        createEDOFile("verletV", verletV);
        createEDOFile("beemannR", beemanR);
        createEDOFile("beemanV", beemanV);
        createEDOFile("gpco5R", gpco5R);
        createEDOFile("gpco5V", gpco5V);*/
    }

    private void createEDOFile(String fileName, ArrayList<Float> elements) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        for (Float f: elements) {
            writer.write(f + "\n");
        }

        writer.close();

    }
}
