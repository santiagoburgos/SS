import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class OscilatorSolver {

    private OscilatorData od;
    private float dt;
    ArrayList<Float> originalR = new ArrayList<>();
    ArrayList<Float> verletR = new ArrayList<>();
    ArrayList<Float> verletV = new ArrayList<>();
    ArrayList<Float> beemanR = new ArrayList<>();
    ArrayList<Float> beemanV = new ArrayList<>();
    ArrayList<Float> gpco5R = new ArrayList<>();
    ArrayList<Float> gpco5V = new ArrayList<>();

    public OscilatorSolver(OscilatorData od, float dt) {
        this.od = od;
        this.dt = dt;
        this.originalR.add(od.getR0());
        float initialV = (-1) * od.getR0() * (od.getPhi()/ (2 * od.getMass()));
        this.verletR.add(od.getR0());
        this.verletV.add(initialV);
        this.beemanR.add(od.getR0());
        this.beemanV.add(initialV);
        this.gpco5R.add(od.getR0());
        this.gpco5V.add(initialV);
    }

    private float getForce(float r, float v) {
        return - od.getK() * r - od.getPhi() * v;
    }

    private float getOriginalR(float t) {
        float wPrime = (float) Math.sqrt(((od.getK() / od.getMass()) - (Math.pow(od.getPhi(), 2) / Math.pow(2 * od.getMass(), 2))));
        return (float) (Math.exp(- t * (od.getPhi() / (2 * od.getMass()))) * Math.cos(wPrime * t));
    }

    private void getVerlet(boolean isFirstTime) {
        float rt = verletR.get(verletR.size() - 1);
        float vt = verletV.get(verletV.size() - 1);
        float prevRt;
        // TODO change this with euler
        if (isFirstTime) prevRt = rt;
        else prevRt = verletR.get(verletR.size() - 2);

        float finalR = 2 * rt - prevRt + ((float) (Math.pow(dt, 2) / od.getMass())) * getForce(rt, vt);
        float finalV = (rt - prevRt) / (2 * dt);
        verletR.add(finalR);
        verletV.add(finalV);
    }

    private void getBeeman() {
        float rt = beemanR.get(beemanR.size() - 1);
        float vt = beemanV.get(beemanV.size() - 1);
        float finalR = rt + vt * dt  + (2 * getForce(rt, vt) * (float) Math.pow(dt, 2)) / (3 * od.getMass()); // TODO MISSING prev acceleration

        float predictedV = vt + (3 * getForce(rt, vt) * dt) / 2; // TODO MISSING prev acceleration
        float correctedV = vt + getForce(finalR, predictedV)  * dt / 3 + (5 * getForce(rt, vt) * dt) / 6; // TODO MISSING prev acceleration

        beemanR.add(finalR);
        beemanV.add(correctedV);
    }

    private void getGPCO5() {
        float rt = gpco5R.get(gpco5R.size() - 1);
        float vt = gpco5V.get(gpco5V.size() - 1);
        // First: Predict
        // return - od.getK() * r - od.getPhi() * v;
        float r2 = getForce(rt, vt) / od.getMass();
        float r3 = - od.getK() * vt / od.getMass() - od.getPhi() * r2 / od.getMass();
        float r4 = - od.getK() * r2 / od.getMass() - od.getPhi() * r3 / od.getMass();
        float r5 =- od.getK() * r3 / od.getMass() - od.getPhi() * r4 / od.getMass();

        float rP = rt + vt * dt + r2 * (float) Math.pow(dt, 2) / 2 + r3 * (float) Math.pow(dt, 3) / 6 + r4 * (float) Math.pow(dt, 4) / 24 + r5 * (float) Math.pow(dt, 5) / 120;
        float r1P = vt + r2 * dt + r3 * (float) Math.pow(dt, 2) / 2 + r4 * (float) Math.pow(dt, 3) / 6 + r5 * (float) Math.pow(dt, 4) / 24;
        float r2P = r2 + r3 * dt + r4 * (float) Math.pow(dt, 2) / 2 + r5 * (float) Math.pow(dt, 3) / 6;
        float r3P = r3 + r4 * dt + r5 * (float) Math.pow(dt, 2) / 2;
        float r4P = r4 + r5 * dt;
        float r5P = r5;

        float deltaR2 = (getForce(rP, r1P) / od.getMass() - r2P) * (float) Math.pow(dt, 2) / 2;
        float finalR = rP + deltaR2 * 3 / 16;
        float finalV = r1P + (251 * deltaR2) / (360 + dt);
        gpco5R.add(finalR);
        gpco5V.add(finalV);
    }

    public void solve() throws IOException {
        boolean isFirstTime = true;
        float t = 0;
        while (t < od.getTf()) {
            t += dt;
            originalR.add(getOriginalR(t));
            getVerlet(isFirstTime);
            getBeeman();
            getGPCO5();
            isFirstTime = false;
        }
        createEDOFile("originalR", originalR);
        createEDOFile("verletR", verletR);
        createEDOFile("verletV", verletV);
        createEDOFile("beemannR", beemanR);
        createEDOFile("beemanV", beemanV);
        createEDOFile("gpco5R", gpco5R);
        createEDOFile("gpco5V", gpco5V);
    }

    private void createEDOFile(String fileName, ArrayList<Float> elements) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        for (Float f: elements) {
            writer.write(f + "\n");
        }

        writer.close();

    }
}
