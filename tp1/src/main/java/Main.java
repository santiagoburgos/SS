import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        FileManager fm = new FileManager();
        ArrayList<ArrayList<Double>> entries = fm.readNumericFile("entrada1.txt");
        int L = entries.get(0).get(0).intValue();
        int N = entries.get(1).get(0).intValue();
        int M = entries.get(2).get(0).intValue();
        double r_c = entries.get(3).get(0);
        ArrayList<Particle> particles = new ArrayList<>();
        for (int i = 4; i < 4 + N; i++)
            particles.add(new Particle(i - 4, entries.get(i).get(0), entries.get(i).get(1)));
    }
}
