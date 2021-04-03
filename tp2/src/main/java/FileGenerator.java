import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

public class FileGenerator {

    public FileGenerator() {}

    public InitialData readStaticFile(String fileName) {
        File file = new File(fileName);
        boolean isFirstLine = true;
        int dimensions = 0;
        boolean isTime = true;
        int time = 100;
        int size = 0;
        float percentage = 0;
        int center = 0;
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (isFirstLine) {
                    isFirstLine =  false;
                    if (data == "S")
                        isTime = false;
                } else {
                    if (dimensions == 0)
                        dimensions = Integer.parseInt(data);
                    else if (size == 0)
                        size = Integer.parseInt(data);
                    else  if (percentage == 0)
                        percentage = Float.parseFloat(data);
                    else if (center == 0)
                        center = Integer.parseInt(data);
                    else if (isTime)
                        time = Integer.parseInt(data);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not read file.");
            e.printStackTrace();
        }

        if (isTime)
            return new InitialData(dimensions, size, percentage, center, time);
        else
            return new InitialData(dimensions, size, percentage, center);
    }

    public void createResults(Map<Integer, Cell[][]> lifeCells, long start, String fileName) {
        try {
            FileWriter writer = null;
            writer = new FileWriter(fileName);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            long nowSeconds = System.nanoTime();
            double elapsedTimeInSecond = (double) (nowSeconds - start) / 1000000000;
            writer.append("Time: " + dtf.format(now));
            writer.append("\n");
            writer.append("Execution time: " + elapsedTimeInSecond + " seconds");
            writer.append("\n");

            writer.append("\nLife Cells \n");
            // TODO LIFE CELLS

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createResultsTD(Map<Integer, Cell[][][]> lifeCells, long start, String fileName) {
        try {
            FileWriter writer = null;
            writer = new FileWriter(fileName);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            long nowSeconds = System.nanoTime();
            double elapsedTimeInSecond = (double) (nowSeconds - start) / 1000000000;
            writer.append("Time: " + dtf.format(now));
            writer.append("\n");
            writer.append("Execution time: " + elapsedTimeInSecond + " seconds");
            writer.append("\n");

            writer.append("\nLife Cells \n");
            // TODO LIFE CELLS

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
