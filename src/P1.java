package src;

import java.io.*;
import java.util.*;

public class P1 {
    public Integer n = 0;
    public Integer m = 0;
    public Integer k = 0;
    public Set<Integer> lords = new HashSet<>();
    public Integer[] permutations;
    public Map<Integer, ArrayList<Integer>> edges = new HashMap<>();
    public Map<Integer, Integer> rang_of_node = new HashMap<>();

    public void read() throws IOException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader("p1.in"));


        String[] first_line = reader.readLine().split(" ");
        this.n = Integer.parseInt(first_line[0]);
        this.m = Integer.parseInt(first_line[1]);
        this.k = Integer.parseInt(first_line[2]);

        String[] lords_string = reader.readLine().split(" ");
        for (int i = 1; i <= k; i++) {
            this.lords.add(Integer.parseInt(lords_string[i - 1]));
        }
        this.permutations = new Integer[this.n - 1];
        String[] permutations_string = reader.readLine().split(" ");
        for (int i = 0; i < n - 1; i++) {
            this.permutations[i] = Integer.parseInt(permutations_string[i]);
            rang_of_node.put(Integer.parseInt(permutations_string[i]), i);
            edges.put(i + 1, new ArrayList<>());
        }
        edges.put(n, new ArrayList<>());
        for (int i = 1; i <= m; i++) {
            String[] edge_string = reader.readLine().split(" ");
            Integer parent = Integer.parseInt(edge_string[0]);
            Integer child  = Integer.parseInt(edge_string[1]);

            ArrayList<Integer> children_of_parent = edges.get(parent);
            children_of_parent.add(child);
            edges.put(parent, children_of_parent);

            ArrayList<Integer> children_of_child = edges.get(child);
            children_of_child.add(parent);
            edges.put(child, children_of_child);
        }
        reader.close();
    }

    public Boolean isRobinHidden(Integer start, Integer lastValid) {
        Set<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        stack.push(start);

        while (!stack.empty()) {
            Integer start_node = stack.pop();
            visited.add(start_node);
            if (lords.contains(start_node)) return false;
            for (Integer child : edges.get(start_node)) {
                if (!visited.contains(child) && (rang_of_node.get(child) > lastValid)) stack.push(child);
            }
        }
        return true;
    }

    public Integer binarySearch(Integer last_failure, Integer last_solution) {

        if (last_solution - last_failure <= 1) {
            if (isRobinHidden(1, last_failure))  return last_failure;
            else return last_solution;
        }

        Integer new_possible_last_solution = last_failure + ((last_solution - last_failure) / 2);
        if (isRobinHidden(1, new_possible_last_solution)) {
            last_solution = new_possible_last_solution;
        }
        else {
            last_failure = new_possible_last_solution;
        }

        return binarySearch(last_failure, last_solution);
    }


    public void solve() throws IOException {
        read();

        FileWriter myWriter = new FileWriter("p1.out");
        int result = binarySearch(0, n - 2) + 1;
        myWriter.write(Integer.valueOf(result).toString());
        myWriter.close();
    }

    public static void main(String[] args) throws IOException {
        P1 solver = new P1();
        solver.solve();
    }
}
