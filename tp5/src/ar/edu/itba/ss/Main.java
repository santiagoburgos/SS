package ar.edu.itba.ss;

public class Main {

    public static void main(String[] args) {

        double N = 180;
        double d = 1.2;

        PedestrianDynamics pd = new PedestrianDynamics(N, d, 2, 0.2);

        int i=0;
        for (double k : pd.states.keySet()) {
                OvitoGen.saveDynamicFile(i, pd.states.get(k), d,"D:\\OV\\");
                i+=1;
        }


    }
}
