package org.example;

import org.example.graph.Edge;
import org.example.graph.Graph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        FileReader reader = new FileReader("src/main/resources/Taxicab_64.txt");
        Scanner scanner = new Scanner(reader);

        int n = 0; // количество вершин
        n = Integer.parseInt(scanner.nextLine().replaceAll("\\D+", ""));
        Map<Integer, List<Integer>> vertices = new HashMap<>();
        int numVert = 0;
        while (scanner.hasNextLine()) {
            String[] coordinates = scanner.nextLine().replaceAll("\\D+", " ").split("\\D+");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            vertices.putIfAbsent(numVert, Arrays.asList(x, y));
            numVert++;
        }

        scanner.close();

        Graph graph = new Graph(n);

        vertices.forEach((key, value) -> {
            for (int i = key + 1; i < vertices.size(); i++) {
                int weight = Math.abs(vertices.get(key).getFirst() - vertices.get(i).getFirst()) +
                        Math.abs(vertices.get(key).getLast() - vertices.get(i).getLast());
                graph.addEdge(key, i, weight);
            }
        });
        var ref = new Object() {
            int sumWeight = 0;
            int countEdges = 0;
        };
        Graph result = graph.findFreeDiamondGraph(graph);
        result.getEdges().forEach(edge -> {
            ref.sumWeight += edge.getW();
            ref.countEdges++;
        });

        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/Basov_" + n + "_2.txt");
            fileWriter.write("c Вес подграфа = " + ref.sumWeight + "\n");
            fileWriter.write("p edge " + n + " " + ref.countEdges + "\n");
            result.getEdges().forEach(edge -> {
                try {
                    fileWriter.write("e " + (edge.getU() + 1) + " " + (edge.getV() + 1) + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}