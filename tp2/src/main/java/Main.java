
public class Main {

    public static void main(String[] args) {

        int time = 200;

/*
        //2D
        LifeCells lc = new LifeCells(200, 20, 80, time, false);

        for (int i = 0; i <= time; i++) {
            OvitoGen.saveDynamicFile(i, lc.lifeCells,"D://OV");

        }

 */



        //3D
        LifeCells lc2 = new LifeCells(30, 4, 80, time, true);

        for (int i = 0; i <= time; i++) {
            OvitoGen.saveDynamicFileTD(i, lc2.lifeCellsTD,"D://OV");

        }









    }

}
