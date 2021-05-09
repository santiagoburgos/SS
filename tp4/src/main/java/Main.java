import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {
     // EJERCICIO 1
        /*
        OscilatorData oscilatorData = Main.readStaticOFile("StaticO.txt");
        double dt = Main.readDynamicOFile("DynamicO.txt");
        if (oscilatorData == null || dt == 0)
            return;

        OscilatorSolver os = new OscilatorSolver(oscilatorData, dt);
        os.solve();

         */


        RadiationWithMatter radiationWithMatter = new RadiationWithMatter(1e-8f, 16, 100e3f, 10e3f, 0.000000000000001);

        int i = 0;
        for (double d : radiationWithMatter.state.keySet()) {

            OvitoGen.saveDynamicFile(i, radiationWithMatter.state.get(d), radiationWithMatter.particles, "D:\\OV\\");
            i++;
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

}
