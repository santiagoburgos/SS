
public class Main {



    public static void main(String[] args) {

        int time = 100;

        CellsGenerator cg = new CellsGenerator();
        Cell[][] cells = cg.generateCells(50,5,40);
        Cell[][][] cellsTD = cg.generateCellsTD(50,5,40);



        LifeCells lc = new LifeCells( 100, cells);
        for (int i = 0; i <= time; i++) {
            OvitoGen.saveDynamicFile(i, lc.lifeCells,"D:\\OV\\");
        }


        LifeCells lctd = new LifeCells( 100, cellsTD);
        for (int i = 0; i <= time; i++) {
           OvitoGen.saveDynamicFileTD(i, lctd.lifeCellsTD,"D:\\OVTD\\");
        }


    }

}
