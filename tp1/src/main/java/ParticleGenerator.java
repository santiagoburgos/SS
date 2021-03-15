import com.sun.xml.internal.ws.wsdl.writer.document.Part;

import java.util.ArrayList;

public class ParticleGenerator {
    private int i;
    private double maxRadius;
    private int l;

    public ParticleGenerator(double maxRadius){
        this.maxRadius = maxRadius;
    }

    //genera 'n' particulas variando su posicion entre 0 y 'l'; con radio entre 0 y 'maxRadius'
    public ArrayList<ArrayList<Particle>> generate(int n, int l, double maxRadius){
        ArrayList<ArrayList<Particle>> particles = new ArrayList<>();
        ArrayList<Particle> auxArray;
        ArrayList<Particle> allGenerated = new ArrayList<>();
        this.l = l;
        for (i = 0; i < n; i++){
            auxArray = new ArrayList<>();
            Particle aux = createParticle(maxRadius);
            if (allGenerated.isEmpty()){
                auxArray.add(aux);
            } else {
                for (Particle p : allGenerated)
                    aux = checkPosition(aux, p);
                auxArray.add(aux);
            }
            allGenerated.add(aux);
            particles.add(auxArray);
        }
        System.out.println(n);
        System.out.println(particles.size());
        return particles;
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
