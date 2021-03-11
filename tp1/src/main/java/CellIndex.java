import java.util.ArrayList;
import java.util.List;

public class CellIndex {
    private int N, L, M;   // N particulas, L tamanio de area, MxM celdas
    private double R;    // R radio de interaccion

    private List<Particle> list[][];
    private Particle particles[];

    public CellIndex(int N, int L, int M, double R){
        this.N = N;
        this.L = L;
        this.M = M;
        this.R = R;
        list = new List[this.M][this.M];
        createParticles(N);
    }

    private void createParticles(int N) {
        this.particles = new Particle[N];
        for (int i = 0; i < N; i++){
            double x = Math.random() * M;
            double y = Math.random() * M;
            particles[i] = new Particle(x, y, i + 1);
            setHead(particles[i]);
        }
    }

    //Guardo las particulas que pertenecen a la misma celda
    private void setHead(Particle particle){
        int x = (int)particle.getX();
        int y = (int)particle.getY();

        if (list[x][y] == null){
            list[x][y] = new ArrayList<>();
        }
        list[x][y].add(particle);
    }

    //recorro celdas y agrego vecinos
    public void setNeighbour(){     // hecho con condiciones periodicas de contorno
        int x, y;
        for (int i = 0; i < M; i++){
            for (int j = 0; j < M; j++){
                if (list[i][j] != null){    //TODO: considerar radios de interaccion antes de agregar como vecino

                    list[i][j].get(0).setNeighbour(list[i][j]);
                    list[i][j].get(0).setNeighbour(list[i == 0? M - 1  : (i - 1)][j]);
                    list[i][j].get(0).setNeighbour(list[i == 0? M - 1  : (i - 1)][(j + 1) % M]);
                    list[i][j].get(0).setNeighbour(list[i][(j + 1) % M]);
                    list[i][j].get(0).setNeighbour(list[(i + 1) % M][(j + 1) % M]);
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
            aux.add(p.getNeighbour());
        }
        fileManager.createResults(aux, 0);                  //TODO: setear tiempo de inicio
    }

}
