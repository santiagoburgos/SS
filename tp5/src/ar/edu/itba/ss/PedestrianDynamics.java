package ar.edu.itba.ss;

import java.util.*;

import static java.lang.Math.sqrt;

public class PedestrianDynamics {

    private static double size = 20;  // meters * meters

    double d = 1.2;      // meters
    double vMax = 2; // m/s
    double N = 200; // numbers por particles

    //radius in meters
    private static double Rmin = 0.15;
    private static double Rmax = 0.32;
    private static double tao = 0.5;

    double deltaTime;
    double deltaTime2;

    private List<Particle> particles = new ArrayList<>();
    public SortedMap<Double, List<Particle>> states = new TreeMap<>();
    double time = 0;


    public List<Double> outTime = new ArrayList<>();
    boolean allOut=false;


    int maxAttempts = 200000;
    private List<Double> vex = new ArrayList<>();
    private List<Double> vey = new ArrayList<>();

    private List<Double> vdx = new ArrayList<>();
    private List<Double> vdy = new ArrayList<>();

    private double target1X0, target1X1;
    private double target2X0, target2X1;

    public PedestrianDynamics(double N, double d, double vMax, double dt2){

        deltaTime = Rmin/(2*vMax);


        if(dt2==0)
            deltaTime2=dt2;
        else
            deltaTime2=dt2;

        this.N = N;
        this.d = d;


        System.out.println("dt " + deltaTime);

        generateParticles();
        saveState(time);

        target1X0 = (size/2)-((d/2)-0.1);
        target1X1 = (size/2)+((d/2)-0.1);

        int iterations = 0;
        double time2 = 0f;
        while(!allOut){

            calculateVe();
            adjustRadius();
            calculateVd();
            updateVelandPos();


            if(time >= time2){
                saveState(time);
                time2 +=deltaTime2;
            }


            time+=deltaTime;


        }
        System.out.println("final time: " + time);

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

        for(Particle p : particles) {
            pcopy.add(p);
        }
    }
    //
    List<Particle> pcopy = new ArrayList<>();


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




            if(particles.get(i).getYPos()>0) {
                if (((particles.get(i).getYPos() - particles.get(i).getRadius() <= 0) && ( particles.get(i).getXPos()  < ((size / 2) - (d / 2)))) || ((particles.get(i).getYPos() - particles.get(i).getRadius() <= 0) && (particles.get(i).getXPos()  > ((size / 2) + (d / 2)))) ) {
                   // p.setRadius(Rmin);
                    vy -= vMax;
                }
            }



            double wall0XPoint = ((size/2)-(d/2));
            double wall0YPoint = 0;
            double d0 = (sqrt((wall0YPoint - p.getYPos()) * (wall0YPoint - p.getYPos()) + (wall0XPoint - p.getXPos()) * (wall0XPoint - p.getXPos())) - (p.getRadius() + 0 ));
            if (d0 < 0 && p.getXPos()>wall0XPoint){
                    double degrees = Math.toDegrees(Math.atan2(wall0XPoint - p.getXPos(), wall0YPoint - p.getYPos()));
                    degrees = degrees + Math.ceil( -degrees / 360 ) * 360;
                    double angle = Math.toRadians(degrees);

                    double vxx = vMax * Math.sin(angle);
                    if(vxx<0)
                        vxx = vxx*-1;
                    double vyy = vMax * Math.cos(angle);
                    if(vyy<0)
                        vyy = vyy*-1;
                    double modx= wall0XPoint - p.getXPos();
                    if(modx<0)
                        modx = modx*-1;
                    double mody= wall0YPoint - p.getYPos();
                    if(mody<0)
                        mody = mody*-1;

                    vx += vxx * ((wall0XPoint - p.getXPos())/modx);
                    vy += vyy * ((wall0YPoint - p.getYPos())/mody);
            }

            double wall1XPoint = ((size/2)-(d/2));
            double wall1YPoint = 0;
            double d1 = (sqrt((wall1YPoint - p.getYPos()) * (wall1YPoint - p.getYPos()) + (wall1XPoint - p.getXPos()) * (wall1XPoint - p.getXPos())) - (p.getRadius() + 0 ));
            if (d1 < 0 &&  p.getXPos()<wall1XPoint){
                double degrees = Math.toDegrees(Math.atan2(wall1XPoint - p.getXPos(), wall1YPoint - p.getYPos()));
                degrees = degrees + Math.ceil( -degrees / 360 ) * 360;
                double angle = Math.toRadians(degrees);

                double vxx = vMax * Math.sin(angle);
                if(vxx<0)
                    vxx = vxx*-1;
                double vyy = vMax * Math.cos(angle);
                if(vyy<0)
                    vyy = vyy*-1;
                double modx= wall1XPoint - p.getXPos();
                if(modx<0)
                    modx = modx*-1;
                double mody= wall1YPoint - p.getYPos();
                if(mody<0)
                    mody = mody*-1;


                vx += vxx * ((wall1XPoint - p.getXPos())/modx);
                vy += vyy * ((wall1YPoint - p.getYPos())/mody);
            }





            for(int j=0; j<particles.size(); j++){
                if(i!=j){
                    Particle p2 = particles.get(j);
                    double distance = (sqrt((p2.getYPos() - p.getYPos()) * (p2.getYPos() - p.getYPos()) + (p2.getXPos() - p.getXPos()) * (p2.getXPos() - p.getXPos())) - (p.getRadius() + p2.getRadius() ));
                    if (distance < 0){

                       // p.setRadius(Rmin);
                       // p2.setRadius(Rmin);

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


            if(vex.get(i) != 0){
                p.setRadius(Rmin);
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
                }
            }
            else{
                targetY = -20;
                targetX = p.getXPos();
                if((p.getXPos())< ((size/2)-1.5d)){
                    targetX = ((size/2)-1.5d);
                }
                if((p.getXPos())> ((size/2)+1.5d)){
                    targetX = ((size/2)+1.5d);
                }
            }



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

        allOut=true;

        for(int i=0; i<particles.size(); i++){

            double xv = (vex.get(i)+vdx.get(i));
            double yv = (vey.get(i)+vdy.get(i));



            particles.get(i).setXVel(xv);
            particles.get(i).setYVel(yv);

            double xp = particles.get(i).getXPos() + (deltaTime*xv);
            double yp = particles.get(i).getYPos() + (deltaTime*yv);
            particles.get(i).setXPos(xp);
            particles.get(i).setYPos(yp);




            //
           // if(i==0){
           //     System.out.println("vex " + vex.get(i) + " vdx " + vdx.get(i) + " rad " + particles.get(i).getRadius());
           //     System.out.println("vey " + vey.get(i) + " vdy " + vdy.get(i) + " rad " + particles.get(i).getRadius());
           // }
            //



            if(particles.get(i).getYPos()+particles.get(i).getRadius() >0){
                allOut = false;
            }



            if(particles.get(i).getYPos()+particles.get(i).getRadius() <0 && pcopy.contains(particles.get(i)) ){
                outTime.add(time);
                pcopy.remove(particles.get(i));
            }




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
