import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        OscilatorData oscilatorData = Main.readStaticOFile("StaticO.txt");
        float dt = Main.readDynamicOFile("DynamicO.txt");
        if (oscilatorData == null || dt == 0)
            return;

        OscilatorSolver os = new OscilatorSolver(oscilatorData, dt);


    }

    public static OscilatorData readStaticOFile(String fileName) {
        File file = new File(fileName);
        float mass = 0;
        float k = 0;
        float phi = 0;
        float tf = 0;
        float r0 = 0;
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                float aux;
                if (data.contains("^")) {
                    int potPosition = data.indexOf('^');
                    int num = Integer.parseInt(data.substring(0, potPosition));
                    int pot =Integer.parseInt(data.substring(potPosition + 1));
                    aux = (float) Math.pow(num, pot);
                } else {
                    aux = Float.parseFloat(data);
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

    public static float readDynamicOFile(String fileName) {
        File file = new File(fileName);
        float aux = 0.1f;
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                aux = Float.parseFloat(data);
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
