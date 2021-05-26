package ar.edu.itba.ss;

import java.util.*;

import static java.lang.Math.sqrt;

public class PedestrianDynamics {

    double size = 20;  // meters * meters

    double d = 1.2;      // meters
    double vMax = 2; // m/s
    double N = 200; // numbers por particles

    //radius in meters
    double Rmin = 0.15;
    double Rmax = 0.32;
    double tao = 0.5;

    double deltaTime = 0.01;


    private List<Particle> particles = new ArrayList<>();
    public SortedMap<Double, List<Particle>> states = new TreeMap<>();
    double time = 0;

    int maxAttempts = 200000;
    private List<Double> vex = new ArrayList<>();
    private List<Double> vey = new ArrayList<>();

    private List<Double> vdx = new ArrayList<>();
    private List<Double> vdy = new ArrayList<>();

    private double target1X0, target1X1;
    private double target2X0, target2X1;

    public PedestrianDynamics(){

        generateParticles();
        saveState(time);

        target1X0 = (size/2)-((d/2)-0.2);
        target1X1 = (size/2)+((d/2)-0.2);


        for(int i=0; i<15000; i++){

            calculateVe();
            adjustRadius();
            calculateVd();
            updateVelandPos();

            time+=deltaTime;
            saveState(time);
        }

    }


    private void generateParticles() {
        int particlesNumber = 0;
        int attempt = 0;

        Random r = new Random();

        while (particlesNumber != N && attempt != maxAttempts) {

            double xPos = Rmax + ((size-Rmax) - Rmax) * r.nextDouble();
            double yPos = Rmax + ((size-Rmax) - Rmax) * r.nextDouble();

            if (addParticle(xPos, yPos, Rmax)) {
                particlesNumber += 1;
            }
            attempt += 1;

        }
    }

    private boolean addParticle(double xPos, double yPos, double radius) {
        for (Particle p : particles) {

            float distance = (float) (sqrt((yPos - p.getYPos()) * (yPos - p.getYPos()) + (xPos - p.getXPos()) * (xPos - p.getXPos())) - (p.getRadius() + radius));
            if (distance < 0)
                return false;
        }


        particles.add(new Particle(xPos, yPos, 0, 0, Rmax));

        vex.add(0d);
        vey.add(0d);

        vdx.add(0d);
        vdy.add(0d);

        return true;
    }



    private void calculateVe(){
        for(int i=0; i<particles.size(); i++){
            //chequeo con todas si hay contacto, calculo ve y guardo

            double vx = 0;
            double vy = 0;

            Particle p = particles.get(i);
            for(int j=0; j<particles.size(); j++){
                if(i!=j){
                    Particle p2 = particles.get(j);
                    double distance = (sqrt((p2.getYPos() - p.getYPos()) * (p2.getYPos() - p.getYPos()) + (p2.getXPos() - p.getXPos()) * (p2.getXPos() - p.getXPos())) - (p.getRadius() + p2.getRadius() ));
                    if (distance < 0){

                        p.setRadius(Rmin);
                        p2.setRadius(Rmin);

                        double degrees = Math.toDegrees(Math.atan2(p2.getXPos() - p.getXPos(), p2.getYPos() - p.getYPos()));
                        degrees = degrees + Math.ceil( -degrees / 360 ) * 360;
                        double angle = Math.toRadians(degrees);

                        double vxx = vMax * Math.sin(angle);
                        if(vxx<0)
                            vxx = vxx*-1;
                        double vyy = vMax * Math.cos(angle);
                        if(vyy<0)
                            vyy = vyy*-1;
                        double modx= p2.getXPos() - p.getXPos();
                        if(modx<0)
                            modx = modx*-1;
                        double mody= p2.getYPos() - p.getYPos();
                        if(mody<0)
                            mody = mody*-1;
                         vx += vxx * ((p2.getXPos() - p.getXPos())/modx);
                         vy += vyy * ((p2.getYPos() - p.getYPos())/mody);
                    }
                }
            }
            vex.set(i, -vx);
            vey.set(i, -vy);
        }
    }


    private void adjustRadius(){

        for(int i=0; i<particles.size(); i++){
            //ajusto radio
            Particle p = particles.get(i);

            if(p.getRadius()<Rmax){
                double rad = p.getRadius() + (Rmax/(tao/deltaTime));
                p.setRadius(rad);
            }



        }
    }


    private void calculateVd(){
        for(int i=0; i<particles.size(); i++){

            Particle p = particles.get(i);

            //calcular target
            double targetX = 10;
            double targetY = 0;



            if(p.getYPos() >0){
                if(p.getXPos() <= target1X0)
                    targetX = target1X0;
                else if(p.getXPos() >= target1X1)
                    targetX = target1X1;
                else{
                    targetX = p.getXPos();
                  //  System.out.println(targetX);
                }
            }
            else{
                targetY = -20;
                targetX = p.getXPos();
            }




            //
            //

            double degrees = Math.toDegrees(Math.atan2(targetX - p.getXPos(), targetY - p.getYPos()));
            degrees = degrees + Math.ceil( -degrees / 360 ) * 360;

            double angle = Math.toRadians(degrees);

            double vd = vMax * ((p.getRadius()-Rmin)/(Rmax-Rmin));

            double vxx = vd * Math.sin(angle);
            if(vxx<0)
                vxx = vxx*-1;
            double vyy = vd * Math.cos(angle);
            if(vyy<0)
                vyy = vyy*-1;

            double modx= targetX - p.getXPos();
            if(modx<0)
                modx = modx*-1;

            double mody= targetY - p.getYPos();
            if(mody<0)
                mody = mody*-1;


            double vx = 0;
            if(modx != 0)
             vx = vxx * ((targetX - p.getXPos())/modx);

            double vy = 0;
            if(mody != 0)
            vy = vyy * ((targetY - p.getYPos())/mody);

            vdx.set(i,vx);
            vdy.set(i,vy);

        }
    }


    private void updateVelandPos(){
        for(int i=0; i<particles.size(); i++){

            double xv = (vex.get(i)+vdx.get(i));
            double yv = (vey.get(i)+vdy.get(i));


            if(particles.get(i).getYPos()>0){
                if((particles.get(i).getYPos()-particles.get(i).getRadius() < 0) && (particles.get(i).getXPos()+particles.get(i).getRadius()< ((size/2)-(d/2)))){
                    yv=0;
                }
                if((particles.get(i).getYPos()-particles.get(i).getRadius() < 0) && (particles.get(i).getXPos()-particles.get(i).getRadius()> ((size/2)+(d/2)))){
                    yv=0;
                }
            }






            particles.get(i).setXVel(xv);
            particles.get(i).setYVel(yv);

            double xp = particles.get(i).getXPos() + (deltaTime*xv);
            double yp = particles.get(i).getYPos() + (deltaTime*yv);
            particles.get(i).setXPos(xp);
            particles.get(i).setYPos(yp);


        }
    }


    private void saveState(double time) {

        List<Particle> copy = new ArrayList<>();
        for (Particle p : particles) {
            copy.add(new Particle(p.getXPos(), p.getYPos(), p.getXVel(), p.getYVel(), p.getRadius()));
        }
        states.put(time, copy);
    }

}
