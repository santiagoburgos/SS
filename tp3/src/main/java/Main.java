package main.java;

public class Main {


    public static void main(String[] args) {



        BrownianMovement bm = new BrownianMovement(140, 2.0f);


        int i=0;
        float clock = 0f;
        for (float d : bm.states.keySet()) {
            if(d >= clock){
                OvitoGen.saveDynamicFile(i, bm.states.get(d),"D:\\OV\\");
                i+=1;
                clock +=0.5f;
            }
        }

    }



}
