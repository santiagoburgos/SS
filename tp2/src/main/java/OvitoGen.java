import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class OvitoGen {


    public static void saveDynamicFile(int frame, Map<Integer, Cell[][]> lifeCells, String path) {
        try(FileWriter fw = new FileWriter(path + "frame-" + frame + ".xyz", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            // Se dice la cantidad de particulas
            int size = lifeCells.get(0).length;
            //out.println(size * size);
            //out.println("");


            int aliveCells = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                        if(lifeCells.get(frame)[i][j].alive)
                            aliveCells +=1;
                }
            }
            out.println(aliveCells);
            out.println("");
            //



            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    // Se crea en cada particula con su (posX, PosY, PosZ, color)
                    if(lifeCells.get(frame)[i][j].alive)
                    out.println(lifeCells.get(frame)[i][j].getX() + " " + lifeCells.get(frame)[i][j].getY() + " " + lifeCells.get(frame)[i][j].getZ() + " " + (lifeCells.get(frame)[i][j].alive? 1:2) );
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static void saveDynamicFileTD(int frame, Map<Integer, Cell[][][]> lifeCells, String path) {
        try(FileWriter fw = new FileWriter(path + "frame-" + frame + ".xyz", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            // Se dice la cantidad de particulas
            int size = lifeCells.get(0).length;
           // out.println(size * size * size);
           // out.println("");
            //

            int aliveCells = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    for (int z = 0; z < size; z++) {
                        if(lifeCells.get(frame)[i][j][z].alive)
                            aliveCells +=1;
                    }
                    }
                    }
             out.println(aliveCells);
             out.println("");
            //

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    for (int z = 0; z < size; z++) {
                        // Se crea en cada particula con su (posX, PosY, PosZ, color)
                        if(lifeCells.get(frame)[i][j][z].alive)
                        out.println(lifeCells.get(frame)[i][j][z].getX() + " " + lifeCells.get(frame)[i][j][z].getY() + " " + lifeCells.get(frame)[i][j][z].getZ() + " " + (lifeCells.get(frame)[i][j][z].alive ? 1 : 2));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



/*

    public static void saveDynamicFile(int frame, Level level, State state, String path) {
        try(FileWriter fw = new FileWriter(path + "frame-" + frame + ".xyz", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            int walls_size = level.walls.size();
            int boxes_size = state.box_positions.size();
            int goals_size = level.goals.size();
            // Se dice la cantidad de particulas
            out.println(boxes_size+walls_size+1+goals_size);
            out.println("");
            // Se crea en cada particula con su (posX, PosY, radio, color)
            out.println(state.player_position.getX() + " " + state.player_position.getY() + " " + 0.5 + " " + 3);
            for(Position b : state.box_positions) {
                out.println(b.getX() + " " + b.getY() + " " + 0.5 + " " + 2);
            }
            for(Position b : level.goals) {
                out.println(b.getX() + " " + b.getY() + " " + 0.25 + " " + 4);
            }
            for(Position w : level.walls) {
                out.println(w.getX() + " " + w.getY() + " " + 0.5 + " " + 1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveSolution(String path, Level level, Node solution_node){
        Node node = solution_node;
        int frame = 0;
        while(node!=null){
            node = node.parent;
            frame++;
        }
        node = solution_node;
        while(node!=null){
            OvitoGen.saveDynamicFile(frame--,level,node.state, path);
            node = node.parent;
        }

    }

*/
}
