package ar.edu.itba.ss;

public class Main {

    public static void main(String[] args) {

        PedestrianDynamics pd = new PedestrianDynamics();

        int i=0;
        float clock = 0f;
        for (double d : pd.states.keySet()) {
            if(d >= clock){
                OvitoGen.saveDynamicFile(i, pd.states.get(d),"D:\\OV\\");
                i+=1;
                clock +=0.1f;
            }
        }


    }
}
