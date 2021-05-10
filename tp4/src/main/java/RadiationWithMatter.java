

import java.util.*;

public class RadiationWithMatter {
    private static final float Q = 1e-19f;
    private static final float M = 1e-27f;
    private static final float K = 1e10f;


    private double L, D;
    private int N;      //NxN = particles amount
    private double velX, velY;
    public ParticleWithCharge particles[][];
    private ParticleWithCharge initial;

    private int saveDT;
    public SortedMap<Double, ParticleWithCharge> state;
    private double time = 0;
    private double deltaTime;

    public SortedMap<Double, Double> energy = new TreeMap<>();



    public RadiationWithMatter(double D, int N, double velX, double velY, double deltaTime, int saveDeltaTime){
        particles = new ParticleWithCharge[N][N];
        this.D = D;
        this.N = N;
        this.L = (N-1) * D ;
        this.velX = velX;
        this.velY = velY;
        this.deltaTime = deltaTime;
        this.state = new TreeMap<>();
        this.saveDT=saveDeltaTime;
        generateParticles();


        Double[] f =CoulombElectrostaticForce();
        prevR[0] = initial.getXPos() - deltaTime * initial.getXVel() + (Math.pow(deltaTime,2)/(2*initial.getMass()))*f[0];
        prevR[1] = initial.getYPos() - deltaTime * (float)initial.getYVel() + (Math.pow(deltaTime,2)/(2*initial.getMass()))*f[1];
        saveState(0, calculateEnergy());

        simulate();
    }

    private void simulate() {

        double initialEnergy = calculateEnergy();
        System.out.println(initialEnergy);

        int iterations = 0;

        while (   EnoughistanceFromParticle()  && !outOfBounderies() && iterations<2000000){

            Double[] forces =CoulombElectrostaticForce();
            verlet(forces);
            saveState(iterations, initialEnergy);
            iterations++;
        }

        System.out.println("iterations " + iterations + ", states saved " + state.size());
    }

    private void saveState(int iterations, double initiale) {

        ParticleWithCharge p = new ParticleWithCharge(initial.getXPos(), initial.getYPos(), initial.getXVel(), initial.getYVel(), initial.getRadius(), initial.getMass(), initial.getCharge());

        if(iterations %saveDT == 0){
            state.put(time, p);
            double e = initiale-calculateEnergy();
            if(e<0)
                e= e*-1;
            energy.put(time, e);
        }
        time += deltaTime;
    }

    private boolean outOfBounderies() {
        if(initial.getXPos() > (L+D) || initial.getXPos() < (-D) || initial.getYPos() > (L+D) || initial.getYPos() < (-D)){
            System.out.println("SALIO");
            return true;
        }
        return false;
    }

    private boolean EnoughistanceFromParticle() {
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                double distance = Math.sqrt(Math.pow(particles[i][j].getXPos() - initial.getXPos(), 2) +
                        Math.pow(particles[i][j].getYPos() - initial.getYPos(), 2));

                if (distance < 0.01 * D){
                    System.out.println("CONSUMIDO");
                    return false;
                }
            }
        }

        return true;
    }

    private void generateParticles() {
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                double xPos = i * D;
                double yPos = j * D;
                double charge = (i + j) % 2 == 0 ? -Q : Q;

                particles[i][j] = new ParticleWithCharge(xPos, yPos,
                        0, 0, 0.0000000005,  M, charge);
            }
        }
        Random r = new Random();
        double yPos = ((L/2)-D) + r.nextFloat() * (((L/2)+D) - ((L/2)-D));


        initial = new ParticleWithCharge(-D, yPos, velX, velY, 0.000000001, M, Q);
    }

    public Double[] CoulombElectrostaticForce(){
        double sum = 0;
        double fx=0;
        double fy=0;
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                double rx = initial.getXPos() - particles[i][j].getXPos();
                double ry = initial.getYPos() - particles[i][j].getYPos();
                sum += particles[i][j].getCharge() / Math.pow(getModule(rx, ry), 2);

                double fn = particles[i][j].getCharge() / Math.pow(getModule(rx, ry), 2);

                double enx = rx/getModule(rx, ry);
                double eny = ry/getModule(rx, ry);
                fx += fn * enx;
                fy += fn * eny;
            }
        }

        Double force = K * initial.getCharge() * sum;

        fx = (fx * K * initial.getCharge());
        fy = (fy * K * initial.getCharge());

        Double[] forces = {fx,fy};
        return forces;
    }


    private double getModule(double x, double y) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }



    double[] prevR ={0,0};
    private void verlet(Double[] forces){
        double rx = 2*initial.getXPos() - prevR[0] + (Math.pow(deltaTime,2)/initial.getMass()) * forces[0] ;
        double ry = 2*initial.getYPos() - prevR[1] + (Math.pow(deltaTime,2)/initial.getMass()) * forces[1] ;

        prevR[0] = initial.getXPos();
        prevR[1] = initial.getYPos();

        initial.setXPos(rx);
        initial.setYPos(ry);

        double vx = ((rx - prevR[0])/(2*deltaTime));
        double vy = ((ry - prevR[1])/(2*deltaTime));

        initial.setXVel(rx);
        initial.setYVel(ry);
    }


    private double calculateEnergy(){
        return kineticE() + potentialE();
    }

    private double kineticE(){

        double v = Math.sqrt(Math.pow(initial.getXVel(), 2) + Math.pow(initial.getYVel(), 2));
        double k = 0.5f * (float)initial.getMass() * Math.pow(v,2);


        return k;
    }


    private double potentialE(){
        double sum =0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                double rx = initial.getXPos() - particles[i][j].getXPos();
                double ry = initial.getYPos() - particles[i][j].getYPos();
                sum += particles[i][j].getCharge() / getModule(rx, ry);
            }
        }
        double u = K * initial.getCharge() * sum;

        return u;
    }



}
