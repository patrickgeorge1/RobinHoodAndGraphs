package src;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class P2 {
    public Integer n = 0;
    public Integer m = 0;
    public Integer start = -1;
    public Integer end = -1;
    public Integer[] distance;
    public Integer infinity = Integer.MAX_VALUE;
    public ArrayList<EdgeOptimised> edgesOptimised = new ArrayList<>();

    class EdgeOptimised {
        public Integer parent = -1;
        public Integer child = -1;
        public Integer cost  = -1;

        EdgeOptimised(Integer parent, Integer child, Integer cost) {
            this.parent = parent;
            this.child = child;
            this.cost  = cost;
        }
    }

    public void readOptimised() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("p2.in"));

        String[] first_line = reader.readLine().split(" ");
        this.n = Integer.parseInt(first_line[0]);
        this.m = Integer.parseInt(first_line[1]);
        distance = new Integer[n + 1];

        String[] second_line = reader.readLine().split(" ");
        this.start = Integer.parseInt(second_line[0]);
        this.end = Integer.parseInt(second_line[1]);

        for (int i = 1; i <= n; i++) {
            distance[i] = infinity;
        }
        distance[start] = 0;

        for (int muchie = 1; muchie <= m; muchie++) {
            String[] edge = reader.readLine().split(" ");
            this.edgesOptimised.add(new EdgeOptimised(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]), Integer.parseInt(edge[2])));
        }
    }

    public void solveOptimised() throws IOException {
        Boolean neverOptimized;
        readOptimised();

        for (int i = 1; i <= n; i++) {
            distance[i] = infinity;
        }
        distance[start] = 0;

        // each optimization
        for (int optimization = 1; optimization < n; optimization++) {
            // each edge
            neverOptimized = true;
            for (EdgeOptimised possibleEdge : edgesOptimised) {
                if (distance[possibleEdge.parent] != infinity && (distance[possibleEdge.parent] + possibleEdge.cost < distance[possibleEdge.child])) {
                    distance[possibleEdge.child] = distance[possibleEdge.parent] + possibleEdge.cost;
                    neverOptimized = false;
                }
            }
            if (neverOptimized) {
                break;
            }
        }

        FileWriter myWriter = new FileWriter("p2.out");
        myWriter.write(Integer.valueOf(distance[end]).toString());
        myWriter.close();
    }


    public static void main(String[] args) throws IOException {
        P2 solver = new P2();
        solver.solveOptimised();
    }
}
