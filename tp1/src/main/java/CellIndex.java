import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.floor;
import static java.lang.Math.max;

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

        if(((float)L/(float)M) <= R){
            return;
        }

        this.N = N;
        this.L = L;
        this.M = M;
        this.periodic = periodic;
        list = new List[this.M][this.M];

        double maxRadius = 0;

        this.particles = new Particle[N];
        for (int i = 0; i < N; i++){
            Particle p = parts.get(i);
            if(p.getRadius() > maxRadius)
                maxRadius = p.getRadius();

            particles[i] = p;
            setHead(particles[i]);
        }

        this.R = R + maxRadius;

    }


    /*private void createParticles(int N) {
        this.particles = new Particle[N];
        for (int i = 0; i < N; i++){
            double x = Math.random() * L;
            double y = Math.random() * L;
            particles[i] = new Particle(x, y, i + 1);

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
                            double distance = Math.hypot(part.getX() - p.getX(), part.getY() - p.getY()) - 2*R;
                            if((p.getNumber() < part.getNumber()) && distance <= 0){
                                p.addNeighbour(part);
                                part.addNeighbour(p);
                            }
                        }

                        //arriba
                        if (list[i][(j + 1) % M]!= null && (periodic || j<M-1)) {
                            for (Particle part : list[i][(j + 1) % M]) {
                                double distance = Math.hypot(part.getX() - p.getX(), part.getY() - p.getY()) - 2*R;
                                if(distance <= 0 && p.getNumber() != part.getNumber()){
                                    p.addNeighbour(part);
                                    part.addNeighbour(p);
                                }
                            }
                        }

                        //arriba derecha
                        if (list[(i + 1) % M][(j + 1) % M] != null && (periodic || (j<M-1 && i<M-1))) {
                            for (Particle part : list[(i + 1) % M][(j + 1) % M]) {
                                double distance = Math.hypot(part.getX() - p.getX(), part.getY() - p.getY()) - 2*R;
                                if(distance <= 0 && p.getNumber() != part.getNumber()){
                                    p.addNeighbour(part);
                                    part.addNeighbour(p);
                                }
                            }
                        }

                        //derecha
                        if (list[(i + 1) % M][j] != null && (periodic || i<M-1)) {
                            for (Particle part : list[(i + 1) % M][j]) {
                                double distance = Math.hypot(part.getX() - p.getX(), part.getY() - p.getY()) - 2*R;
                                if(distance <= 0 && p.getNumber() != part.getNumber()){
                                    p.addNeighbour(part);
                                    part.addNeighbour(p);
                                }
                            }
                        }

                        // abajo derecha
                        if (list[i == 0 ? M - 1 : (i - 1)][(j + 1) % M] != null && (periodic || (j<M-1 && i>0)) ) {
                        for (Particle part : list[i == 0 ? M - 1 : (i - 1)][(j + 1) % M]) {
                            double distance = Math.hypot(part.getX() - p.getX(), part.getY() - p.getY()) - 2*R;
                            if(distance <= 0 && p.getNumber() != part.getNumber()){
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
