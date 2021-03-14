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
        ArrayList<Double> aux;
        String num;
        String decimalNum;
        boolean addToDecimal;
        double base;
        char lastChar;
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                aux = new ArrayList<Double>();
                String data = myReader.nextLine();
                num = "";
                decimalNum = "0";
                addToDecimal = false;
                base = 10;
                lastChar = ' ';


                for (String word : data.split("\\s")) {
                    if (!word.isEmpty())
                        aux.add(Double.parseDouble(word));

                }




                /*for(char s: data.toCharArray()) {
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
                }*/
                elements.add(aux);


            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not read file.");
            e.printStackTrace();
        }

        return elements;
    }

    public void createResults(ArrayList<ArrayList<Particle>> relationships, long start, String fileName) {
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

            for (ArrayList<Particle> particles: relationships) {
                for (Particle p: particles) {
                    writer.append(p.getNumber() + " " + p.getX() + " " + p.getY() + " " + p.getRadius()+ ", ");
                }
                writer.append("\n");
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Particle> readResultsFile(String fileName, int amountToSkip) {
        boolean isFirst;
        File file = new File(fileName);
        ArrayList<Particle> elements = new ArrayList<Particle>();
        ArrayList<String> aux2;
        Particle aux;
        String number;
        String x;
        String y;
        String radius;
        char lastChar;
        try {
            Scanner myReader = new Scanner(file);
            for (int j = 0; j < amountToSkip; j++)
                myReader.nextLine();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                aux2 = new ArrayList<>();
                aux = new Particle();
                number = "";
                x = "";
                y = "";
                radius = "";
                isFirst = true;
                lastChar = ' ';
                for(char s: data.toCharArray()) {
                    if (s == ',') {
                        if (isFirst) {
                            isFirst = false;
                            aux = new Particle(Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(radius), (int) Double.parseDouble(number));
                        } else {
                            aux.addNeighbour(new Particle(Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(radius), (int) Double.parseDouble(number)));
                        }
                        x = "";
                        y = "";
                        radius = "";
                        number = "";
                        aux2.clear();
                    } else if(!isWhitespace(s)) {
                        switch (aux2.size()) {
                            case 0:
                                number += s;
                                break;
                            case 1:
                                x += s;
                                break;
                            case 2:
                                y += s;
                                break;
                            default:
                                radius +=s;
                                break;
                        }
                    } else if (isWhitespace(s) && lastChar != ',')
                        aux2.add(number);
                    lastChar = s;
                }
                if (x != "") {
                    if (isFirst)
                        elements.add(new Particle(Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(radius), (int) Double.parseDouble(number)));
                    else {
                        aux.addNeighbour(new Particle(Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(radius), (int) Double.parseDouble(number)));
                        elements.add(aux);
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not read file.");
            e.printStackTrace();
        }

        return elements;
    }
}
