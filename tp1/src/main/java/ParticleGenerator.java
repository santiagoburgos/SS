import java.util.ArrayList;

public class ParticleGenerator {
    private ArrayList<ArrayList<Particle>> particles;
    private int i;
    private double maxRadius;
    private int l;

    public ParticleGenerator(double maxRadius){
        this.particles = new ArrayList<>();
        this.maxRadius = maxRadius;
    }

    //genera 'n' particulas variando su posicion entre 0 y 'l'; con radio entre 0 y 'maxRadius'
    public ArrayList<ArrayList<Particle>> generate(int n, int l, double maxRadius){
        this.l = l;
        for (i = 0; i < n; i++){
            Particle aux = createParticle(maxRadius);
            if (this.particles.isEmpty()){
                this.particles.add(new ArrayList<>());
                this.particles.get(0).add(aux);
            } else {
                for (ArrayList<Particle> parts : this.particles) {
                    for (Particle p : parts){
                        aux = checkPosition(aux, p);
                    }
                }
                this.particles.get(0).add(aux);
            }
        }
        return this.particles;
    }

    //Evita superposicion de particulas
    private Particle checkPosition(Particle p, Particle par){
        for (int count = 0; count < 100 && p.getX() <= par.getX() - p.getRadius() - par.getRadius() && p.getX() >= par.getX() + p.getRadius() + par.getRadius(); count++){
            p = createParticle(this.maxRadius);
        }
        for (int count = 0; count < 100 && p.getY() <= par.getY() - p.getRadius() - par.getRadius() && p.getY() >= par.getY() + p.getRadius() + par.getRadius(); count++){
            p = createParticle(this.maxRadius);
        }
        return p;
    }


    private Particle createParticle(double maxRadius) {
        double x = Math.random() * l;
        double y = Math.random() * l;
        double radius = Math.random() * maxRadius;
        return new Particle(x, y, radius, i++);
    }
}
