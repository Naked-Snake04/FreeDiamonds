package org.example.graph;

import java.util.*;

/**
 * Класс Граф
 */
public class Graph {
    private int verticesNum; // количество вершин
    private List<Edge> edges; // рёбра
    Map<Integer, List<Integer>> adjacencyList;

    public List<Edge> getEdges() {
        return edges;
    }

    public Graph(int verticesNum) {
        this.verticesNum = verticesNum;
        edges = new ArrayList<>();
        this.adjacencyList = new HashMap<>();
        for (int i = 0; i < verticesNum; i++) {
            adjacencyList.put(i, new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int w) {
        edges.add(new Edge(u, v, w));
        adjacencyList.get(u).add(v);
        adjacencyList.get(v).add(u);
    }

    public Graph findFreeDiamondGraph(Graph graph) {
        Graph result = new Graph(graph.verticesNum);
        int count = 0;
        graph.getEdges().sort(Comparator.comparingInt(Edge::getW).reversed());
        for (Edge e : graph.getEdges()) {
            System.out.println(++count);
            result.addEdge(e.getU(), e.getV(), e.getW());
            if (containsDiamond(result)) {
                result.removeEdge(e.getU(), e.getV());
            }
        }

        return result;
    }

    private boolean containsDiamond(Graph g) {
        // Перебор всех комбинаций 4 вершин из графа
        for (int i = 0; i < g.verticesNum; i++) {
            for (int j = i + 1; j < g.verticesNum; j++) {
                for (int k = j + 1; k < g.verticesNum; k++) {
                    for (int l = k + 1; l < g.verticesNum; l++) {
                        // Проверка, что выбранные вершины образуют алмаз
                        int countEdges = 0;
                        if (g.adjacencyList.get(i).contains(j)) countEdges++;
                        if (g.adjacencyList.get(i).contains(k)) countEdges++;
                        if (g.adjacencyList.get(i).contains(l)) countEdges++;
                        if (g.adjacencyList.get(j).contains(k)) countEdges++;
                        if (g.adjacencyList.get(j).contains(l)) countEdges++;
                        if (g.adjacencyList.get(k).contains(l)) countEdges++;
                        if (countEdges == 5) {
                            return true; // Найден алмаз
                        }
                    }
                }
            }
        }
        return false;
    }

    public void removeEdge(int u, int v) {
        edges.removeIf(edge -> (edge.getU() == u && edge.getV() == v) || (edge.getU() == v && edge.getV() == u));
        adjacencyList.get(u).remove((Integer) v);
        adjacencyList.get(v).remove((Integer) u);
    }
}
