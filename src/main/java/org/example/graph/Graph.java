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
        int count = edges.size();
        graph.getEdges().sort(Comparator.comparingInt(Edge::getW).reversed());
        for (Edge e : graph.getEdges()) {
            System.out.println(count--);
            result.addEdge(e.getU(), e.getV(), e.getW());
            if (containsDiamond(result)) {
                result.removeEdge(e.getU(), e.getV());
            }
        }

        return result;
    }

    private boolean containsDiamond(Graph g) {
        // Перебор комбинаций по соседям вершин
        for (int i = 0; i < g.verticesNum; i++) {
            if (g.adjacencyList.size() > 3) {
                for (int j : g.adjacencyList.get(i)) {
                    for (int k : g.adjacencyList.get(i)) {
                        if (j == k)
                            continue;
                        if (g.adjacencyList.get(j).contains(k))
                            return true;
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
