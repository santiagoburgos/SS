package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        StaticData staticData = Main.readStaticFile("Static.txt");
        if (staticData == null)
            return;


        BrownianMovement bm = new BrownianMovement(140, 2.0f, staticData);


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

    public static StaticData readStaticFile(String fileName) {
        File file = new File(fileName);
        float size = 0;
        float r1 = 0;
        float r2 = 0;
        float m1 = 0;
        float m2 = 0;
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (size == 0)
                    size = Float.parseFloat(data);
                else if (r1 == 0)
                    r1 = Float.parseFloat(data);
                else  if (r2 == 0)
                    r2 = Float.parseFloat(data);
                else if (m1 == 0)
                    m1 = Float.parseFloat(data);
                else if (m2 == 0)
                    m2 = Float.parseFloat(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not read file.");
            e.printStackTrace();
            return null;
        }

        return new StaticData(size, r1, r2, m1, m2);

    }



}
