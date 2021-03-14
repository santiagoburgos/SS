import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class CellIndex {
    private int N, L, M;   // N particulas, L tamanio de area, MxM celdas
    private double R;    // R radio de interaccion
    private boolean periodic = false;

    private List<Particle> list[][];
    private Particle particles[];


    /*public CellIndex(int N, int L, int M, double R, boolean periodic){

        if((L/M) <= R){
            //throw bad arguments
        }

        this.N = N;
        this.L = L;
        this.M = M;
        this.R = R;
        this.periodic = periodic;
        list = new List[this.M][this.M];
        createParticles(N);
    }*/




    public CellIndex(int N, int L, int M, double R, boolean periodic, ArrayList<Particle> parts){

        double maxRadius = 0;
        for(Particle p: parts){
            if(p.getRadius() > maxRadius)
                maxRadius = p.getRadius();
        }



        this.N = N;
        this.L = L;
        this.M = M;
        this.R = R;
        this.periodic = periodic;
        list = new List[this.M][this.M];

        if(((float)L/(float)M) <= (maxRadius+R)){
            throw new IllegalArgumentException();
        }


        this.particles = new Particle[N];
        for (int i = 0; i < N; i++){
            Particle p = parts.get(i);

            particles[i] = p;
            setHead(particles[i]);
        }
    }


    public void calculateNeighbors(){

    }

/*
    private void createParticles(int N) {
        this.particles = new Particle[N];
        for (int i = 0; i < N; i++){
            double x = Math.random() * L;
            double y = Math.random() * L;
            particles[i] = new Particle(x, y, i + 1);

            setHead(particles[i]);
        }
    }


    public CellIndex(int N, int L, double R, boolean periodic, ArrayList<Particle> parts){

        this.N = N;
        this.L = L;
        this.periodic = periodic;
        list = new List[this.M][this.M];

        double maxRadius = 0;
        for(Particle p: parts){
            if(p.getRadius() > maxRadius)
                maxRadius = p.getRadius();
        }
        this.R = R + maxRadius;
        M = (int)floor((float)L/(float)R);

        System.out.println("M " + M);

        this.particles = new Particle[N];
        for (int i = 0; i < N; i++){
            Particle p = parts.get(i);
            particles[i] = p;
            setHead(particles[i]);
        }



    }*/



    //Guardo las particulas que pertenecen a la misma celda
    private void setHead(Particle particle){

        float lm = ((float)L/(float)M);

        double auxX = floor(particle.getX()/lm);
        double auxY = floor(particle.getY()/lm);

        int x = (int)auxX;
        int y = (int)auxY;

        if (list[x][y] == null){
            list[x][y] = new ArrayList<>();
        }
        list[x][y].add(particle);

/*
        for (int i = 0; i < M; i++){
            for (int j = 0; j < M; j++){
                if (list[i][j] != null){
                   System.out.println("celda:" + i + "-" + j);
                    for (Particle p : list[i][j]){
                        System.out.println(p.getNumber());
                    }

                }}} */



    }




    //recorro celdas y agrego vecinos
    public void setNeighbour(){
        int x, y;
        for (int i = 0; i < M; i++){
            for (int j = 0; j < M; j++){
                if (list[i][j] != null){

                    for(Particle p : list[i][j]) {





                        //misma celda
                        for (Particle part : list[i][j]) {
                            double distance = sqrt((part.getY() - p.getY()) * (part.getY() - p.getY()) + (part.getX() - p.getX()) * (part.getX() - p.getX())) - max(p.getRadius(), part.getRadius());
                            if((p.getNumber() < part.getNumber()) && distance <= this.R){
                                p.addNeighbour(part);
                                part.addNeighbour(p);
                            }
                        }

                        //arriba
                        if (list[i][(j + 1) % M]!= null && (periodic || j<M-1)) {
                            for (Particle part : list[i][(j + 1) % M]) {
                                double distance = sqrt((part.getY() - p.getY()) * (part.getY() - p.getY()) + (part.getX() - p.getX()) * (part.getX() - p.getX())) - max(p.getRadius(), part.getRadius());

                                if(periodic && j == (M-1)){
                                    double periodicDistance = sqrt(((part.getY()+L) - p.getY()) * ((part.getY()+L) - p.getY()) + ((part.getX()+L) - p.getX()) * ((part.getX()+L) - p.getX())) - max(p.getRadius(), part.getRadius());
                                    distance = min(distance, periodicDistance);
                                }

                                if(distance <= this.R && p.getNumber() != part.getNumber()){
                                    p.addNeighbour(part);
                                    part.addNeighbour(p);
                                }
                            }
                        }

                        //arriba derecha
                        if (list[(i + 1) % M][(j + 1) % M] != null && (periodic || (j<M-1 && i<M-1))) {

                            for (Particle part : list[(i + 1) % M][(j + 1) % M]) {
                                double distance = sqrt((part.getY() - p.getY()) * (part.getY() - p.getY()) + (part.getX() - p.getX()) * (part.getX() - p.getX())) - max(p.getRadius(), part.getRadius());

                                if(periodic && i == (M-1) && j == (M-1)){
                                    double periodicDistance = sqrt(((part.getY()+L) - p.getY()) * ((part.getY()+L) - p.getY()) + ((part.getX()+L) - p.getX()) * ((part.getX()+L) - p.getX())) - max(p.getRadius(), part.getRadius());
                                    distance = min(distance, periodicDistance);
                                }

                                if(distance <= this.R && p.getNumber() != part.getNumber()){
                                    p.addNeighbour(part);
                                    part.addNeighbour(p);
                                }
                            }
                        }

                        //derecha
                        if (list[(i + 1) % M][j] != null && (periodic || i<M-1)) {
                            for (Particle part : list[(i + 1) % M][j]) {
                                double distance = sqrt((part.getY() - p.getY()) * (part.getY() - p.getY()) + (part.getX() - p.getX()) * (part.getX() - p.getX())) - max(p.getRadius(), part.getRadius());

                                if(periodic && i == (M-1)){
                                    double periodicDistance = sqrt(((part.getY()+L) - p.getY()) * ((part.getY()+L) - p.getY()) + ((part.getX()+L) - p.getX()) * ((part.getX()+L) - p.getX())) - max(p.getRadius(), part.getRadius());
                                    distance = min(distance, periodicDistance);
                                }

                                if(distance <= this.R && p.getNumber() != part.getNumber()){
                                    p.addNeighbour(part);
                                    part.addNeighbour(p);
                                }
                            }
                        }

                        // abajo derecha
                        if (list[i == 0 ? M - 1 : (i - 1)][(j + 1) % M] != null && (periodic || (j<M-1 && i>0)) ) {
                        for (Particle part : list[i == 0 ? M - 1 : (i - 1)][(j + 1) % M]) {
                            double distance = sqrt((part.getY() - p.getY()) * (part.getY() - p.getY()) + (part.getX() - p.getX()) * (part.getX() - p.getX())) - max(p.getRadius(), part.getRadius());

                            if(periodic && i == 0 && j == (M-1)){
                                double periodicDistance = sqrt(((part.getY()+L) - p.getY()) * ((part.getY()+L) - p.getY()) + ((part.getX()+L) - p.getX()) * ((part.getX()+L) - p.getX())) - max(p.getRadius(), part.getRadius());
                                distance = min(distance, periodicDistance);
                            }


                            if(distance <= this.R && p.getNumber() != part.getNumber()){
                                p.addNeighbour(part);
                                part.addNeighbour(p);
                            }
                        }
                    }

                    }
                }
            }
        }
    }




    public void printParticles(){
        for (int i = 0; i < N; i++){
            System.out.println("X: " + particles[i].getX() +
                    "\tY: " + particles[i].getY() +
                    "\tindex: " + particles[i].getNumber());
        }
    }

    public Particle[] getParticles() {
        return particles;
    }



    public void getOutput(){
        FileManager fileManager = new FileManager();
        ArrayList<ArrayList<Particle>> aux = new ArrayList<>();
        for (Particle p : particles){
            ArrayList<Particle> aux2 = new ArrayList<>();
            aux2.add(p);
            aux2.addAll(p.getNeighbour());
            aux.add(aux2);
        }
        fileManager.createResults(aux, 0, "particles.txt");
    }

}
