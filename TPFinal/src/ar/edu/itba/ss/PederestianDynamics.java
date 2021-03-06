package ar.edu.itba.ss;


import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

import static java.lang.Math.sqrt;

public class PederestianDynamics {






    private static double size = 20;  // meters * meters

    private static double yStart = 3;

    //private static double N = 500; // number of particles
    private static double N = 500;

    double vMax = 2; // m/s


    //radius in meters
    private static double Rmin = 0.15;
    private static double Rmax = 0.32;
    private static double tao = 0.5;


    double d = 3;      // meters

    double h = 2;

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



    double hx0 [];
    double hx1 [];
    double hy0 [];

    double vy0 [];
    double vy1 [];
    double vx0 [];


    int conf;
    double l;

    public PederestianDynamics( double vMax, double dt2, int conf, double l){

        this.conf = conf;
        this.l = l;

        setupWalls();

        deltaTime = Rmin/(2*vMax);


        if(dt2==0)
            deltaTime2=dt2;
        else
            deltaTime2=dt2;

        this.N = N;
        this.d = d;


        System.out.println("dt " + deltaTime);

        generateParticles();

        //TODO
        setPaths();

        saveState(time);



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

            iterations+=1;

        }
        System.out.println("final time: " + time);

    }


    private void generateParticles() {
        int particlesNumber = 0;
        int attempt = 0;


        Random r = new Random();

        while (particlesNumber != N && attempt != maxAttempts) {

            double xPos = Rmax + ((size-Rmax) - Rmax) * r.nextDouble();
            double yPos = (Rmax + (((size-yStart)-Rmax) - Rmax) * r.nextDouble())+yStart;



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


            //borders

            vy = horizontalBorders(p, vy);
            vx = verticalBorders(p, vx);

            //borders edges

            Point2D.Double edg = edges(particles.get(i), vx, vy);

            vx = edg.x;
            vy = edg.y;


            for(int j=0; j<particles.size(); j++){
                if(i!=j){
                    Particle p2 = particles.get(j);
                    double distance = (sqrt((p2.getYPos() - p.getYPos()) * (p2.getYPos() - p.getYPos()) + (p2.getXPos() - p.getXPos()) * (p2.getXPos() - p.getXPos())) - (p.getRadius() + p2.getRadius() ));
                    if (distance < 0){



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

            //TODO calcular target

            Point2D.Double targ = getTarget(p);

            double targetX = targ.x;
            double targetY = targ.y;


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




    private double horizontalBorders(Particle p, double vy){


        for(int n=0; n<hy0.length; n++){

            if(p.getYPos()>hy0[n]) {


                if( ((p.getYPos()-p.getRadius()) <= hy0[n]) && ( p.getXPos() >= hx0[n]  &&   p.getXPos() <= hx1[n]   )    ){
                    vy -= vMax;
                    p.setRadius(Rmin);
                }
            }

            if(p.getYPos()<hy0[n]) {
                if( ((p.getYPos()+p.getRadius()) >= hy0[n]) && ( p.getXPos() >= hx0[n]  &&   p.getXPos() <= hx1[n]   )    ){
                    vy += vMax;
                    p.setRadius(Rmin);
                }
            }

        }

        return vy;
    }



    private double verticalBorders(Particle p, double vx){


        for(int n=0; n<vx0.length; n++){

            if(p.getXPos()>vx0[n]) {
                if( ((p.getXPos()-p.getRadius()) <= vx0[n]) && ( p.getYPos() >= vy0[n]  &&   p.getYPos() <= vy1[n]   )    ){
                    vx -= vMax;
                    p.setRadius(Rmin);
                }
            }

            if(p.getXPos()<vx0[n]) {
                if( ((p.getXPos()+p.getRadius()) >= vx0[n]) && ( p.getYPos() >= vy0[n]  &&   p.getYPos() <= vy1[n]   )    ){
                    vx += vMax;
                    p.setRadius(Rmin);
                }
            }

        }


        return vx;
    }



    List<Point2D.Double> edgePoints = new ArrayList<>();



    private Point2D.Double edges(Particle p, double velx, double vely){

        double vx = velx;
        double vy = vely;


        for(Point2D.Double point : edgePoints){

            double d0 = (sqrt((point.y - p.getYPos()) * (point.y - p.getYPos()) + (point.x - p.getXPos()) * (point.x - p.getXPos())) - (p.getRadius() + 0 ));
            if (d0 < 0 && p.getXPos()>point.x) {
                double degrees = Math.toDegrees(Math.atan2(point.x - p.getXPos(), point.y - p.getYPos()));
                degrees = degrees + Math.ceil(-degrees / 360) * 360;
                double angle = Math.toRadians(degrees);

                double vxx = vMax * Math.sin(angle);
                if (vxx < 0)
                    vxx = vxx * -1;
                double vyy = vMax * Math.cos(angle);
                if (vyy < 0)
                    vyy = vyy * -1;
                double modx = point.x - p.getXPos();
                if (modx < 0)
                    modx = modx * -1;
                double mody = point.y - p.getYPos();
                if (mody < 0)
                    mody = mody * -1;

                vx += vxx * ((point.x - p.getXPos()) / modx);
                vy += vyy * ((point.y - p.getYPos()) / mody);
            }


        }

        return new Point2D.Double(vx, vy);
    }




    private Point2D.Double getTarget(Particle p){

        double x = 0;
        double y = 0;
        boolean finish = false;

        List<Double> distances = new ArrayList<>();



        boolean delete = false;
        if(p.targets.size()>1) {
            for (Point2D.Double[] points : p.targets.get(0)) {
                //horizontales
                if(points[0].y == points[1].y){
                    if(p.getYPos() < points[0].y){
                        delete = true;
                    }
                }
                //verticales
                if(points[0].x == points[1].x){

                }
            }
        }
        if(delete){
            p.targets.remove(0);
        }





        if(!p.targets.isEmpty()){
            for(Point2D.Double[] points : p.targets.get(0)){
                if(finish)
                    break;
                //horizontales
                if(points[0].y == points[1].y){


                    //si estoy arriba de un target
                    if(p.getXPos() >= points[0].x && p.getXPos() <= points[1].x){
                        x= p.getXPos();
                        y= points[0].y;
                        finish = true;
                    }
                    double distance0 = (sqrt((points[0].y - p.getYPos()) * (points[0].y - p.getYPos()) + (points[0].x - p.getXPos()) * (points[0].x - p.getXPos())) - (p.getRadius() + 0 ));
                    double distance1 = (sqrt((points[1].y - p.getYPos()) * (points[1].y - p.getYPos()) + (points[1].x - p.getXPos()) * (points[1].x - p.getXPos())) - (p.getRadius() + 0 ));


                    double distance = Math.min(distance0, distance1);
                    distances.add(distance);
                }
                //verticales
                if(points[0].x == points[1].x){
                }
            }

            if(!finish){



                int index = 0;
                double minDistance = distances.get(0);
                    for(int i=0; i<distances.size(); i++){
                        if(distances.get(i) <= minDistance){
                            index = i;
                            minDistance = distances.get(i);
                        }

                    }


                Point2D.Double[] selected = p.targets.get(0).get(index);
                double distance0 = (sqrt((selected[0].y - p.getYPos()) * (selected[0].y - p.getYPos()) + (selected[0].x - p.getXPos()) * (selected[0].x - p.getXPos())) - (p.getRadius() + 0 ));
                double distance1 = (sqrt((selected[1].y - p.getYPos()) * (selected[1].y - p.getYPos()) + (selected[1].x - p.getXPos()) * (selected[1].x - p.getXPos())) - (p.getRadius() + 0 ));


                //RANDOMIZAR EL PUNTO Q APUNTA
                Random r = new Random();

                if(selected[0].x < selected[1].x){
                    double randomValue = selected[0].x + (selected[1].x - selected[0].x) * r.nextDouble();
                    x=randomValue;
                    y=selected[0].y;
                }
                else{
                    double randomValue = selected[1].x + (selected[0].x - selected[1].x) * r.nextDouble();
                    x=randomValue;
                    y=selected[0].y;
                }

                //IR AL PUNTO MAS CERCANO
                /*
                if(distance0 < distance1){
                    x=selected[0].x;
                    y=selected[0].y;
                }else{
                    x=selected[1].x;
                    y=selected[1].y;
                }

                 */

            }


        }


        return  new Point2D.Double(x,y);
    }


    private void setupWalls(){

        if(conf==1){

            if(l==0){
                edgePoints.add(new Point2D.Double((size/2)-(d/2),0));
                edgePoints.add(new Point2D.Double((size/2)+(d/2),0));

                //horizontal walls
                hx0  = new double[]{0, (size/2)+(d/2)};
                hx1 = new double[]{(size/2)-(d/2), 20};
                hy0 = new double[]{0, 0};

                //vertical walls
                vy0  = new double[]{0, 0};
                vy1 = new double[]{20, 20};
                vx0 = new double[]{0, 20};
            }else{
                edgePoints.add(new Point2D.Double((size/2)-(d/2),0));
                edgePoints.add(new Point2D.Double((size/2)+(d/2),0));

                edgePoints.add(new Point2D.Double((size/2)-(l/2),2));
                edgePoints.add(new Point2D.Double((size/2)+(l/2),2));

                //DE IZQUIERDA A DERECHA

                //horizontal walls
                hx0  = new double[]{0, (size/2)+(d/2), (size/2)-(l/2)};
                hx1 = new double[]{(size/2)-(d/2), 20, (size/2)+(l/2)};
                hy0 = new double[]{0, 0, h};

                //vertical walls
                vy0  = new double[]{0, 0};
                vy1 = new double[]{20, 20};
                vx0 = new double[]{0, 20};
            }
        }
        if(conf==2){
            edgePoints.add(new Point2D.Double((d/2),0));
            edgePoints.add(new Point2D.Double(20-(d/2),0));

            edgePoints.add(new Point2D.Double((l/2),2));
            edgePoints.add(new Point2D.Double(20-(l/2),2));

            //DE IZQUIERDA A DERECHA

            //horizontal walls
            hx0  = new double[]{(d/2),0, 20-(l/2)};
            hx1 = new double[]{20-(d/2),(l/2), 20};
            hy0 = new double[]{0,h,h};

            //vertical walls
            vy0  = new double[]{0, 0};
            vy1 = new double[]{20, 20};
            vx0 = new double[]{0, 20};
        }
        if(conf==3){
            edgePoints.add(new Point2D.Double(((size/2)-((d/3)/2)-l-(d/3)),0));
            edgePoints.add(new Point2D.Double(((size/2)-((d/3)/2)-l),0));
            edgePoints.add(new Point2D.Double( ((size/2)-((d/3)/2)),0));
            edgePoints.add(new Point2D.Double(((size/2)+((d/3)/2)),0));
            edgePoints.add(new Point2D.Double(((size/2)+((d/3)/2)+l),0));
            edgePoints.add(new Point2D.Double( ((size/2)+((d/3)/2)+l+(d/3)) ,0));


            hx0  = new double[]{ 0                       , (size/2)-((d/3)/2)-l, (size/2)+((d/3)/2)  , ((size/2)+((d/3)/2)+l+(d/3))};
            hx1 = new double[]{(size/2)-((d/3)/2)-l-(d/3), (size/2)-((d/3)/2)  , (size/2)+((d/3)/2)+l, 20d};

            hy0 = new double[]{0,0,0, 0};

            //vertical walls
            vy0  = new double[]{0, 0};
            vy1 = new double[]{20, 20};
            vx0 = new double[]{0, 20};
        }

    }



    private void setPaths(){
        if(conf == 1){

            for(Particle p : particles) {
                List<List<Point2D.Double[]>> targetPaths = new ArrayList<>();


                List<Point2D.Double[]> target0= new ArrayList<>();
                Point2D.Double[] target00 = {new Point2D.Double(0, h-0.2),new Point2D.Double(  ((size/2)-(l/2))-0.2 ,h-0.2)};
                target0.add(target00);
                Point2D.Double[] target01 = {new Point2D.Double(((size/2)+(l/2))+0.2,h-0.2),new Point2D.Double(20,h-0.2)};
                target0.add(target01);

                List<Point2D.Double[]> target1= new ArrayList<>();
                Point2D.Double[] target10 = {new Point2D.Double(((size/2)-(d/2))+0.2,0),new Point2D.Double(((size/2)+(d/2))-0.2,0)};
                target1.add(target10);

                List<Point2D.Double[]> target2= new ArrayList<>();
                Point2D.Double[] target20 = {new Point2D.Double(0,-20),new Point2D.Double(20,-20)};
                target2.add(target20);

                if(l==0){
                    targetPaths.add(target1);
                    targetPaths.add(target2);
                }else{
                    targetPaths.add(target0);
                    targetPaths.add(target1);
                    targetPaths.add(target2);
                }


                p.targets = targetPaths;
            }
        }

        if(conf == 2){
            for(Particle p : particles) {
                List<List<Point2D.Double[]>> targetPaths = new ArrayList<>();


                List<Point2D.Double[]> target0= new ArrayList<>();
                Point2D.Double[] target00 = {new Point2D.Double((l/2)+0.2, h-0.2),new Point2D.Double(  (20-(l/2))-0.2 ,h-0.2)};
                target0.add(target00);


                List<Point2D.Double[]> target1= new ArrayList<>();
                Point2D.Double[] target10 = {new Point2D.Double(0,0),new Point2D.Double((d/2)-0.2,0)};
                target1.add(target10);
                Point2D.Double[] target11 = {new Point2D.Double( (20-(d/2))+0.2,0),new Point2D.Double(20,0)};
                target1.add(target11);

                List<Point2D.Double[]> target2= new ArrayList<>();
                Point2D.Double[] target20 = {new Point2D.Double(0,-20),new Point2D.Double(20,-20)};
                target2.add(target20);


                    targetPaths.add(target0);
                    targetPaths.add(target1);
                    targetPaths.add(target2);



                p.targets = targetPaths;
            }


        }


        if(conf == 3){
            for(Particle p : particles) {
                List<List<Point2D.Double[]>> targetPaths = new ArrayList<>();

                List<Point2D.Double[]> target0= new ArrayList<>();
                Point2D.Double[] target00 = {new Point2D.Double((size/2)-((d/3)/2)-(d/3)-l+0.2, 0),new Point2D.Double(  (size/2)-((d/3)/2)-l-0.2,0)};
                target0.add(target00);

                Point2D.Double[] target01 = {new Point2D.Double((size/2)-((d/3)/2)+0.2, 0),new Point2D.Double(  (size/2)+((d/3)/2)-0.2 ,0)};
                target0.add(target01);

                Point2D.Double[] target02 = {new Point2D.Double((size/2)+((d/3)/2)+l+0.2, 0),new Point2D.Double(  (size/2)+((d/3)/2)+(d/3)+l-0.2 ,0)};
                target0.add(target02);


                List<Point2D.Double[]> target1= new ArrayList<>();
                Point2D.Double[] target10 = {new Point2D.Double(0,-20),new Point2D.Double(20,-20)};
                target1.add(target10);

                targetPaths.add(target0);
                targetPaths.add(target1);




                p.targets = targetPaths;

            }


        }

    }

}

