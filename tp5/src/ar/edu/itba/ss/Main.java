package ar.edu.itba.ss;


import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        double N = 200;
        double d = 1.2;

        PedestrianDynamics pd = new PedestrianDynamics(N, d, 2, 0.1);

        timesFile( pd.outTime);

        /*
        int i=0;
        for (double k : pd.states.keySet()) {
                OvitoGen.saveDynamicFile(i, pd.states.get(k), d,"D:\\OV\\");
                i+=1;
        }

         */

    }

    public static void timesFile(List<Double> times) {
        try {
            FileWriter myWriter = new FileWriter("times.csv");

            myWriter.write("time\n");
            for(Double d: times){
                myWriter.write(d +",\n");
            }
            myWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
