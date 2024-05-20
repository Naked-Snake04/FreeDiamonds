package org.example.graph;

public class Edge implements Comparable<Edge> {
    private int u;
    private int v;
    private int w;

    Edge(int u, int v, int w)
    {
        this.setU(u);
        this.setW(w);
        this.setV(v);
    }

    public int compareTo(Edge other)
    {
        return (this.getW() - other.getW());
    }

    public int getU() {
        return u;
    }

    public void setU(int u) {
        this.u = u;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }
}
