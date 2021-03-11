import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Character.isWhitespace;

public class FileManager {

    public ArrayList<ArrayList<Double>> readNumericFile(String fileName) {
        File file = new File(fileName);
        ArrayList<ArrayList<Double>> elements = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> aux = new ArrayList<Double>();
        String num;
        String decimalNum;
        boolean addToDecimal;
        double base;
        char lastChar;
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                num = "";
                decimalNum = "0";
                addToDecimal = false;
                base = 10;
                lastChar = ' ';
                for(char s: data.toCharArray()) {
                    if (isWhitespace(s) && !isWhitespace(lastChar)) {
                        aux.add(new Double(num) * Math.pow(base, new Integer(decimalNum)));
                        num = "";
                        decimalNum = "0";
                        addToDecimal = false;
                        base = 10;
                    } else if (!isWhitespace(s)) {
                        if (s == 'e')
                            addToDecimal = true;
                        else if (lastChar == 'e' && s == '-')
                            base = 0.1;
                        else {
                            if (addToDecimal)
                                decimalNum += s;
                            else
                                num += s;
                        }
                    }
                    lastChar = s;
                }
                if (num != "") {
                    aux.add(new Double(num) * Math.pow(base, new Integer(decimalNum)));
                }
                elements.add(aux);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not read file.");
            e.printStackTrace();
        }

        return elements;
    }

    public void createResults(ArrayList<ArrayList<Particle>> relationships, long start) {
        boolean isFirst;
        try {
            FileWriter writer = null;
            writer = new FileWriter("relations.txt");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            long nowSeconds = System.nanoTime();
            double elapsedTimeInSecond = (double) (nowSeconds - start) / 1000000000;
            writer.append("Time: " + dtf.format(now));
            writer.append("\n");
            writer.append("Execution time: " + elapsedTimeInSecond + " seconds");
            writer.append("\n");

            for (ArrayList<Particle> particles: relationships) {
                isFirst = true;
                for (Particle p: particles) {
                    if (isFirst) {
                        writer.append("Particle: " + p.getNumber() + " on x: " + p.getX() + " y: " + p.getY());
                        isFirst = false;
                    } else {
                        writer.append(" , " + p.getNumber() + " on x: " + p.getX() + " y: " + p.getY());
                    }
                }
                writer.append("\n");
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
