package ar.edu.itba.ss;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        double N = 500;
        double d = 3;

        int conf = 1;
        double l = 12;

        PederestianDynamics pd = new PederestianDynamics(  2, 0.1, conf, l);

        // timesFile( pd.outTime);




        int i=0;
        for (double k : pd.states.keySet()) {
            OvitoGen.saveDynamicFile(i, pd.states.get(k), d, conf ,l ,"D:\\OV\\");
            i+=1;
        }



    }

    public static void timesFile(List<Double> times) {
        try {
            FileWriter myWriter = new FileWriter("times45.csv");

            myWriter.write("n,time\n");
            int n =1;


            for(Double d: times){
                myWriter.write((n +"," + d +"\n"));
                n+=1;
            }
            myWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void radio(List<Double> times) {
        try {
            FileWriter myWriter = new FileWriter("times45.csv");

            myWriter.write("n,time\n");
            int n =1;


            for(Double d: times){
                myWriter.write((n +"," + d +"\n"));
                n+=1;
            }
            myWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

