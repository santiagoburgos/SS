package ar.edu.itba.ss;

public class Main {

    public static void main(String[] args) {

        PedestrianDynamics pd = new PedestrianDynamics(200, 1.2, 2, 0.1);

        int i=0;
        for (double d : pd.states.keySet()) {
                OvitoGen.saveDynamicFile(i, pd.states.get(d),"D:\\OV\\");
                i+=1;
        }


    }
}
