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

    public void createResults(int frame, Map<Integer, Cell[][]> lifeCells, String fileName) {
        try {
            FileWriter writer = null;
            writer = new FileWriter(fileName);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            writer.append("Time: " + dtf.format(now));
            writer.append("\n");

            writer.append("\nLife Cells \n");
            int size = lifeCells.get(0).length;

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if(lifeCells.get(frame)[i][j].alive) {
                        writer.append(lifeCells.get(frame)[i][j].getX() + " " + lifeCells.get(frame)[i][j].getY() + " " + lifeCells.get(frame)[i][j].getZ() + " " + (lifeCells.get(frame)[i][j].alive ? 1 : 2));
                        writer.append("\n");
                    }
                }
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createResultsTD(int frame, Map<Integer, Cell[][][]> lifeCells, String fileName) {
        try {
            FileWriter writer = null;
            writer = new FileWriter(fileName);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            writer.append("Time: " + dtf.format(now));
            writer.append("\n");

            writer.append("\nLife Cells \n");
            int size = lifeCells.get(0).length;

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    for (int z = 0; z < size; z++) {
                        if (lifeCells.get(frame)[i][j][z].alive) {
                            writer.append(lifeCells.get(frame)[i][j][z].getX() + " " + lifeCells.get(frame)[i][j][z].getY() + " " + lifeCells.get(frame)[i][j][z].getZ() + " " + (lifeCells.get(frame)[i][j][z].alive ? 1 : 2));
                            writer.append("\n");
                        }
                    }
                }
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
