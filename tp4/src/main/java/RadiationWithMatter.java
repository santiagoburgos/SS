

import java.util.*;

public class RadiationWithMatter {
    private static final float Q = 1e-18f;
    //private static final float Q = 1e-19f;
    private static final float M = 1e-27f;
    private static final float K = 1e10f;


    private float L, D;
    private int N;      //NxN = particles amount
    private float velX, velY;
    public ParticleWithCharge particles[][];
    private ParticleWithCharge initial;

    public SortedMap<Double, ParticleWithCharge> state;
    private double time = 0;
    private double deltaTime;

    public RadiationWithMatter(float D, int N, float velX, float velY, double deltaTime){
        particles = new ParticleWithCharge[N][N];
        this.D = D;
        this.N = N;
        this.L = (N-1) * D ;
        this.velX = velX;
        this.velY = velY;
        this.deltaTime = deltaTime;
        this.state = new TreeMap<>();
        generateParticles();


        Float[] f =CoulombElectrostaticForce();
        prevR[0] = (float)initial.getXPos() - (float)deltaTime * (float)initial.getXVel() + (float)(Math.pow(deltaTime,2)/(2*initial.getMass()))*f[0];
        prevR[1] = (float)initial.getYPos() - (float)deltaTime * (float)initial.getYVel() + (float)(Math.pow(deltaTime,2)/(2*initial.getMass()))*f[1];

        System.out.println("prevr " +  prevR[0] + " f " + f[0]  + " " +  prevR[1] + " f " + f[1] );

        saveState();
        simulate();
    }

    private void simulate() {

        int iterations = 0;

        while ( ( (time != 0 && initial.getXPos() != -D) || EnoughistanceFromParticle()  ) && iterations<2000){



            Float[] forces =CoulombElectrostaticForce();

            System.out.println("x " + initial.getXPos() + " f " + forces[0] + " y " + initial.getYPos() +" f " + forces[1] );


            ver(forces);

            saveState();
            iterations++;
        }


    }

    private void saveState() {

        ParticleWithCharge p = new ParticleWithCharge(initial.getXPos(), initial.getYPos(), initial.getXVel(), initial.getYVel(), initial.getRadius(), initial.getMass(), initial.getCharge());
        state.put(time, p);
        time += deltaTime;
    }

    private boolean EnoughistanceFromParticle() {
        for (int i = 0; i < N; i++){        //TODO Se podria obtener vecinos en vez de recorrer todas
            for (int j = 0; j < N; j++){
                double distance = Math.sqrt(Math.pow(particles[i][j].getXPos() - initial.getXPos(), 2) +
                        Math.pow(particles[i][j].getYPos() - initial.getYPos(), 2));
                if (distance < 0.01 * D) return false;
            }
        }
        return true;
    }

    private void generateParticles() {
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                float xPos = i * D;
                float yPos = j * D;
                double charge = (i + j) % 2 == 0 ? -Q : Q;

                particles[i][j] = new ParticleWithCharge(xPos, yPos,
                        0, 0, 0.0000000005,  M, charge);

            }
        }
        Random r = new Random();
        float yPos = ((L/2)-D) + r.nextFloat() * (((L/2)+D) - ((L/2)-D));


        initial = new ParticleWithCharge(-D, yPos, velX, velY, 0.000000001, M, Q);
    }

    public Float[] CoulombElectrostaticForce(){
        double sum = 0;
        float fx=0;
        float fy=0;
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

        fx = (float)(fx * K * initial.getCharge());
        fy = (float)(fy * K * initial.getCharge());

        Float[] forces = {fx,fy};
        return forces;
    }


    private double getModule(double x, double y) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }







    float[] prevR ={0,0};
            public void ver(Float[] forces){
                float rx = 2f*(float)initial.getXPos() - prevR[0] + (float)(Math.pow(deltaTime,2)/(float)initial.getMass()) * forces[0] ;
                float ry = 2f*(float)initial.getYPos() - prevR[1] + (float)(Math.pow(deltaTime,2)/(float)initial.getMass()) * forces[1] ;

                prevR[0] = initial.getXPos();
                prevR[1] = initial.getYPos();

                initial.setXPos(rx);
                initial.setYPos(ry);



            }























    double previousAx = 0;
    double previousAy = 0;

    private void beeman(float fxx, float fyy) {

        float ax = fxx / initial.getMass();
        float ay = fyy / initial.getMass();

        System.out.println("ax " + ax + " ay " + ay);

        double rx = initial.getXPos() + initial.getXVel()*deltaTime + (2/3) * ax * Math.pow(deltaTime,2) - (1/6) * previousAx * Math.pow(deltaTime,2) ;
        double ry = initial.getYPos() + initial.getYVel()*deltaTime + (2/3) * ax * Math.pow(deltaTime,2) - (1/6) * previousAy * Math.pow(deltaTime,2) ;


        double sum = 0;
        double fx=0;
        double fy=0;
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                double rxx = initial.getXPos() - particles[i][j].getXPos();
                double ryy = initial.getYPos() - particles[i][j].getYPos();
                sum += particles[i][j].getCharge() / Math.pow(getModule(rxx, ryy), 2);

                double fn = particles[i][j].getCharge() / Math.pow(getModule(rx, ry), 2);
                double enx = (initial.getXPos() - particles[i][j].getXPos())/getModule(rx, ry);
                double eny = (initial.getYPos() - particles[i][j].getYPos())/getModule(rx, ry);
                fx += fn * enx;
                fy += fn * eny;



            }
        }
        fx = fx * K * initial.getCharge();
        fy = fy * K * initial.getCharge();




        double nextax = fx / initial.getMass();
        double nextay = fy / initial.getMass();


        double vx = initial.getXVel() + ((1/3) * nextax * deltaTime)+ ((5/6) * ax * deltaTime) - ((1/6) * previousAx * deltaTime);
        double vy = initial.getYVel() + ((1/3) * nextay * deltaTime) + ((5/6) * ay * deltaTime) - ((1/6) * previousAy * deltaTime);

        System.out.println("VX " + vx + " VY " + vy);
        previousAx = ax;
        previousAy = ay;

        initial.setXPos((float)rx);
        initial.setYPos((float)ry);
        initial.setXVel((float)vx);
        initial.setYVel((float)vy);
    }


}
