package src;

import javax.xml.stream.events.EndDocument;
import java.io.*;
import java.util.*;

public class P3 {
    public Integer n = 0;
    public Integer m = 0;
    public Double energy = 0.0;

    public Map<Integer, Vertex> vertices = new HashMap<>();
    public Map<Integer, ArrayList<Edge>> edges = new HashMap<>();
    public Set<Integer> visited = new HashSet<>();
    public Double infinity = Double.MAX_VALUE;


    class Vertex {
        Integer id = -1;
        Integer parent = -1;
        Double energyLeft = -infinity;

        Vertex(Integer id) {
            this.id = id;
        }

        Vertex(Integer id, Double distance) {
            this.id = id;
            this.energyLeft = distance;
        }

        Vertex(Integer id, Double distance, Integer parent) {
            this.id = id;
            this.energyLeft = distance;
            this.parent = parent;
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "id=" + id +
                    ", parent=" + parent +
                    ", distance=" + energyLeft +
                    '}';
        }
    }

    class Edge {
        Integer child = -1;
        Integer procent = 0;

        Edge(Integer child, Integer procent) {
            this.child = child;
            this.procent = procent;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "child=" + child +
                    ", procent=" + procent +
                    '}';
        }
    }

    public void read() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("p3.in"));
        String[] first_line = reader.readLine().split(" ");
        this.n = Integer.parseInt(first_line[0]);
        this.m = Integer.parseInt(first_line[1]);
        this.energy = Double.parseDouble(first_line[2]);

        for (int i = 1; i <= n; i++) {
            vertices.put(i, new Vertex(i));
            edges.put(i, new ArrayList<Edge>());
        }

        for (int i = 1; i <= m; i++) {
            String[] edge = reader.readLine().split(" ");
            Integer parent = Integer.parseInt(edge[0]);
            Integer child = Integer.parseInt(edge[1]);
            Integer procent = Integer.parseInt(edge[2]);

            ArrayList<Edge> parent_children = edges.get(parent);
            parent_children.add(new Edge(child, procent));
            edges.put(parent, parent_children);
        }
        vertices.put(1, new Vertex(1, energy));
    }

    public void solve() throws IOException {
        read();
        PriorityQueue<Vertex> heap = new PriorityQueue<>(new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                if (o1.energyLeft == o2.energyLeft) return (o1.id - o2.id);
                else if (o1.energyLeft < o2.energyLeft) return 1;
                    else return -1;
            }
        });

        heap.add(vertices.get(1));

        while (!heap.isEmpty()) {
            Vertex node = heap.remove();
            if (node.id == n) break;
            visited.add(node.id);
            for (Edge child : edges.get(node.id)) {
                Integer child_id = child.child;
                if (!visited.contains(child_id)) {
                    Double energyLeft = node.energyLeft;
                    Integer child_procent = child.procent;
                    Vertex child_object = new Vertex(child_id, energyLeft * (1 - (child_procent / 100.0)), node.id);
                    vertices.put(child_id, child_object);
                    heap.add(child_object);
                }
            }
        }


        Stack<Integer> solution = new Stack<>();
        Vertex backtracer = vertices.get(n);
        while (backtracer.id != 1) {
            solution.add(backtracer.id);
            backtracer = vertices.get(backtracer.parent);
        }
        solution.add(1);

        StringBuilder res = new StringBuilder();
        res.append(solution.pop());
        while (!solution.empty()) {
            res.append(" ");
            res.append(solution.pop());
        }

        FileWriter myWriter = new FileWriter("p3.out");

        System.out.println(vertices.get(n).energyLeft);
        myWriter.write(Double.valueOf(vertices.get(n).energyLeft).toString());
        myWriter.write("\n");
        myWriter.write(res.toString());
        myWriter.close();
    }

    public static void main(String[] args) throws IOException {
        P3 solver = new P3();
        solver.solve();
    }
}
