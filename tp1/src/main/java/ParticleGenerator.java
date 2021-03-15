import com.sun.xml.internal.ws.wsdl.writer.document.Part;

import java.util.ArrayList;

public class ParticleGenerator {
    private double maxRadius;
    private boolean notRandom;
    private int l;

    public ParticleGenerator(double maxRadius, boolean notRandom){
        this.maxRadius = maxRadius;
        this.notRandom = notRandom;
    }

    //genera 'n' particulas variando su posicion entre 0 y 'l'; con radio entre 0 y 'maxRadius'
    public ArrayList<ArrayList<Particle>> generate(int n, int l){
        ArrayList<ArrayList<Particle>> particles = new ArrayList<>();
        ArrayList<Particle> auxArray;
        ArrayList<Particle> allGenerated = new ArrayList<>();
        this.l = l;
        for (int i = 0; i < n; i++){
            auxArray = new ArrayList<>();
            Particle aux = createParticle(i);
            if (allGenerated.isEmpty()){
                auxArray.add(aux);
            } else {
                for (Particle p : allGenerated)
                    aux = checkPosition(aux, p, i);
                auxArray.add(aux);
            }
            allGenerated.add(aux);
            particles.add(auxArray);
        }
        return particles;
    }

    //Evita superposicion de particulas
    private Particle checkPosition(Particle p, Particle par, int i){
        for (int count = 0; count < 100 && p.getX() <= par.getX() - p.getRadius() - par.getRadius() && p.getX() >= par.getX() + p.getRadius() + par.getRadius(); count++){
            p = createParticle(i);
        }
        for (int count = 0; count < 100 && p.getY() <= par.getY() - p.getRadius() - par.getRadius() && p.getY() >= par.getY() + p.getRadius() + par.getRadius(); count++){
            p = createParticle(i);
        }
        return p;
    }


    private Particle createParticle(int i) {
        double x = Math.random() * l;
        double y = Math.random() * l;
        double radius = Math.random() * this.maxRadius;
        if (notRandom)
            radius = this.maxRadius;
        return new Particle(x, y, radius, i);
    }
}
