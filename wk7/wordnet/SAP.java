import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Digraph;
import java.util.Iterator;

public class SAP {

    private final Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
         if (G == null) throw new IllegalArgumentException();
         this.G = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v >= G.V() || w >= G.V()) throw new IllegalArgumentException();
        Queue<Integer> qv = new Queue<>();
        Queue<Integer> qw = new Queue<>();
        int[] distToV = bfs(v, qv);
        int[] distToW = bfs(w, qw);
        while (!qv.isEmpty() || !qw.isEmpty()) {
            if (!qv.isEmpty()) {
                int nextV = qv.dequeue();
                if (distToW[nextV] > 0) return distToW[nextV] + distToV[nextV];
            }
            if (!qw.isEmpty()) {
                int nextW = qw.dequeue();
                if (distToV[nextW] > 0) return distToW[nextW] + distToV[nextW];
            }
        }
        return -1;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v >= G.V() || w >= G.V()) throw new IllegalArgumentException();
        Queue<Integer> qv = new Queue<>();
        Queue<Integer> qw = new Queue<>();
        int[] distToV = bfs(v, qv);
        int[] distToW = bfs(w, qw);
        while (!qv.isEmpty() || !qw.isEmpty()) {
            if (!qv.isEmpty()) {
                int nextV = qv.dequeue();
                if (distToW[nextV] > 0) return nextV;
            }
            if (!qw.isEmpty()) {
                int nextW = qw.dequeue();
                if (distToV[nextW] > 0) return nextW;
            }
        }
        return -1;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
         if (v == null || w == null) throw new IllegalArgumentException();
         return -1;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
         if (v == null || w == null) throw new IllegalArgumentException();
         return -1;
    }

    private int[] bfs(int v, Queue<Integer> bfs) {
        int[] distTo = new int[G.V()];
        if (!bfs.isEmpty()) bfs = new Queue<>();
        Queue<Integer> q = new Queue<>();
        q.enqueue(v);
        int dist = 1;
        distTo[v] = -1;
        while (!q.isEmpty()) {
            int x = q.dequeue();
            bfs.enqueue(x);
            Iterator<Integer> adj = G.adj(x).iterator();
            while (adj.hasNext()) {
                int next = adj.next();
                if (distTo[next] <= 0) continue;
                distTo[next] = dist;
                q.enqueue(next);
            }
            dist++;
        }
        return distTo;
    }

    // do unit testing of this class
    public static void main(String[] args) {
    }
}

