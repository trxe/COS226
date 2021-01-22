## Graphs

### Undirected graphs.

Set of *vertices* connected pairwise by *edges*.

#### Some terms and the challenges associated

1. **Path**. sequence of vertices connected by edges
2. **Cycle**. path that starts and ends with the same vertex.
3. **Euler tour**. cycle that passes through each edge exactly once.
4. **Hamiltonian tour**. cycle that passes through each vertex exactly once.
---
1. **Connectedness**. a path exists between two connected vertices.
2. **Biconnectivity**. is there a vertex that disconnects the graph when
   removed?
3. **Minimum spanning tree (MST)**. what's the least weighted path between two
   vertices?
4. **Shortest path search**. what's the shortest path between two vertices.
---
1. **Planarity**. does the graph have no crossing edges?
2. **Graph isomorphism**. do two adjacency-lists represent the same graph?

### Undirected graph API

![graph data structure](https://mermaid.ink/img/eyJjb2RlIjoiZ3JhcGggTFJcbiAgICBBe3ZlcnRleE5hbWV9IC0tPnxSZXF1ZXN0IGZyb218IEIoU3ltYm9sIFRhYmxlKVxuICAgIEIgLS0-fGV4dHJhY3RzfCBDe2luZGV4fVxuICAgIEMgLS0-fFJlcXVlc3QgZnJvbXwgRChhcnJheSBvZiBiYWdzIG9mIGFkamFjZW50IHZlcnRpY2VzKVxuICAgIEQgLS0-IEV7YmFnfSIsIm1lcm1haWQiOnsidGhlbWUiOiJkZWZhdWx0In0sInVwZGF0ZUVkaXRvciI6ZmFsc2V9)

a few possible representations:

- set-of-edges *(very inefficient)*
- adjacency-matrix *(most graphs are sparse, meaning most entries are 0, making
  the matrix representation very inefficient)*
- adjacency-list *(at each index a bag of the adjacent vertices are stored)*

![graph data structures](graph_reps.png)

### Depth first search for Trémaux maze exploration

Effective backtracking is key; if a vertex has been traversed before it should
not be traversed again. The key is to leave a record of vertices previously
traversed. So a strategy would be upon reaching vertex *v*,

- mark *v* as visited,
- recursively visit all unvisited nodes adjacent to *v*.

We can decouple graph data type from the graph processing.  We can have a
class ```DepthFirstSearch``` that goes through each vertex's unvisited
neighbours.

```java
private void dfs(Graph G, int v) {
    marked[v] = true;
    for (int w: G.adj(v)) {
        if (!marked[w]) {
            dfs(G,w);
            // edgeTo[] creates a parent-link 
            // representation of a tree rooted at s.
            edgeTo[w] = v;
        }
    }
}
```

After a DFS, we can find vertices connected to *v* in constrant time with
```edgeTo[]```, by "backtracking" the dfs from *v* and putting each vertex found
onto a stack.

### Breadth first search

add the source vertex *s* to a queue. Then do the following while the queue is
not empty: (this process if approx time O(E + V).)

- dequeue *t* from the queue
- add to the queue all unmarked vertices adjacent to *t* and mark them as
  visited.

```java
private void bfs(Graph G, int s) {
    Queue<Integer> q = new Queue<>();
    q.enqueue(s);
    distTo[s] = 0;
    marked[s] = true;
    while (!q.isEmpty()) {
        int v = q.dequeue();
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                q.enqueue(w);
                marked[w] = true;
                distTo[w] = distTo[v] + 1;
                edgeTo[w] = v;
            }
        }
        // thus at any point in time there is a non-negative 
        // number of vertices that are k and k+1 edges away from source.
    }
}
``` 

BFS' client is the same as that of DFS. Common BFS applications include
geneology maps like that for calculating Erdos numbers or Kevin Bacon numbers.

### Connected components

Say we want to reply to queries "is *v* connected to *w*?" in O(1) time. We
could build an adjacency matrix and multiply, but not practical if graph is
massive. We can actually do this with a DFS (union-find is not constant time).

**Connected component**. a maximal set of connected vertices. The relation "is
connected to" is an equivalence relation.

**Strategy.** with DFS, we can discover all the vertices connected in each
component and label them with a distinct component id number. *If two vertices
are connected, they have the same component id number. (constant time!)*

We are essentially 

```java
public CC(Graph G) {
    marked = new boolean[G.V()];
    id = new int[G.V()];
    for (int v = 0; v < G.V(); v++) {
        if (!marked[v]) {
            dfs(G, v); // now the dfs is modified to assign id[v] = count;
            count++;
        }
    }
}
``` 
### Graph challenges

1. Is a graph bipartite?
    1. **Bipartite**. two groups of nodes that within each group, no two nodes
      are adjacent. (Contains no odd cycles.)
    2. DFS can solve this.

2. Find a cycle.

3. Is there an Euler Path in this graph? Find it.

4. Is there a Hamiltonian Path in this graph? Find it.
    2. classical NP-complete problem.

5. Are two graphs isomorphic?
    1. no one knows...

6. Is a graph planar?
    1. linear time DFS planarity algorithm, discovered by Tarjan in 1970s.
    2. too complicated for most.
