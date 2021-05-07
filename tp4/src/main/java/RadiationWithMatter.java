import java.util.List;

public class RadiationWithMatter {
    private static final double Q = 1e-19;
    private static final double M = 1e-27;
    private static final double K = 1e10;


    private double L, D;
    private int N;      //NxN = particles amount
    private double velX, velY;
    private ParticleWithCharge particles[][];
    private ParticleWithCharge initial;

    public RadiationWithMatter(double D, int N, double velX, double velY){
        particles = new ParticleWithCharge[N][N];
        this.D = D;
        this.N = N;
        this.L = N * D - 1;
        this.velX = velX;
        this.velY = velY;
        generateParticles();
        CoulombElectrostaticForce();
    }

    private void generateParticles() {
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                double xPos = i * D;
                double yPos = j * D;
                double charge = (i + j) % 2 == 0 ? -Q : Q;
                particles[i][j] = new ParticleWithCharge(xPos, yPos,
                        0, 0, 0.1,  M, charge);
            }
        }
        double yPos = Math.random() * (2 * D) + (L / 2) - D;

        initial = new ParticleWithCharge(-D, yPos, velX, velY, 0.1, M, Q);
    }

    public Double CoulombElectrostaticForce(){
        double sum = 0;
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                double rx = initial.getXPos() - particles[i][j].getXPos();
                double ry = initial.getYPos() - particles[i][j].getYPos();
                sum += particles[i][j].getCharge() / Math.pow(getModule(rx, ry), 2);
            }
        }

        Double force = K * initial.getCharge() * sum;
        return force;

    }

    private double getModule(double x, double y) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

}
