package ngordnet.main;

import edu.princeton.cs.algs4.Bag;


public class Graph {
    private int V;
    private int E;
    private Bag<Integer>[] adj;

    public Graph(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Number of vertices must be non-negative");
        }
        this.V = V;
        this.E = 0;
        adj = new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

    private void chekcValidateVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    public void addEdge(int v, int w) {
        // from v to w
        chekcValidateVertex(v);
        chekcValidateVertex(w);
        E++;
        adj[v].add(w);
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public Iterable<Integer> adj(int v) {
        chekcValidateVertex(v);
        return adj[v];
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
        }
        return s.toString();
    }
}
