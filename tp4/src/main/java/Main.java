import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {
     // EJERCICIO 1

        OscilatorData oscilatorData = Main.readStaticOFile("StaticO.txt");
        double dt = Main.readDynamicOFile("DynamicO.txt");
        if (oscilatorData == null || dt == 0)
            return;

        OscilatorSolver os = new OscilatorSolver(oscilatorData, dt);
        os.solve();

        /*ArrayList<Double> rs = reaad("originalR");
        ArrayList<Double> vr = reaad("verletR");
        ArrayList<Double> br = reaad("beemannR");
        ArrayList<Double> gr = reaad("gpco5R");

        double auxV = 0;
        double auxB = 0;
        double auxG = 0;
        int i = 1;
        for(Double d: rs) {
            auxV += Math.pow(d - vr.get(i), 2);
            auxB += Math.pow(d - br.get(i), 2);
            auxG += Math.pow(d - gr.get(i), 2);
            i++;
        }
        System.out.println("Verlet ECM: " + auxV / rs.size());
        System.out.println("Beeman ECM: " + auxB / rs.size());
        System.out.println("G ECM: " + auxG / rs.size());*/



        RadiationWithMatter radiationWithMatter = new RadiationWithMatter(1e-8, 16, 20e3, 0, 1e-16, 100);


        int it = 0;
        for (double d : radiationWithMatter.state.keySet()) {
            OvitoGen.saveDynamicFile(it, radiationWithMatter.state.get(d), radiationWithMatter.particles, "D:\\OV\\");
            it++;
        }

    }

    public static OscilatorData readStaticOFile(String fileName) {
        File file = new File(fileName);
        double mass = 0;
        double k = 0;
        double phi = 0;
        double tf = 0;
        double r0 = 0;
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                double aux;
                if (data.contains("^")) {
                    int potPosition = data.indexOf('^');
                    int num = Integer.parseInt(data.substring(0, potPosition));
                    int pot = Integer.parseInt(data.substring(potPosition + 1));
                    aux = Math.pow(num, pot);
                } else {
                    aux = Double.parseDouble(data);
                }

                if (mass == 0)
                    mass = aux;
                else if (k == 0)
                    k = aux;
                else  if (phi == 0)
                    phi = aux;
                else if (tf == 0)
                    tf = aux;
                else if (r0 == 0)
                    r0 = aux;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not read file.");
            e.printStackTrace();
            return null;
        }

        return new OscilatorData(mass, k, phi, tf, r0);

    }

    public static double readDynamicOFile(String fileName) {
        File file = new File(fileName);
        double aux = 0.1f;
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                aux = Double.parseDouble(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not read file.");
            e.printStackTrace();
            return 0;
        }

        return aux;

    }

    public static ArrayList<Double> reaad(String fileName) {
        ArrayList<Double> auxiliar = new ArrayList<>();
        File file = new File(fileName);

        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                int point = data.indexOf(",");
                String first = data.substring(0, point);
                String second = data.substring(point +1);
                auxiliar.add(Double.parseDouble(first + "." + second));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not read file.");
            e.printStackTrace();
            return null;
        }

        return auxiliar;

    }

}
