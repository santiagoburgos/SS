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
}
