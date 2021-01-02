import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.Queue;

public class Solver {

    private final Queue<Board> sequence;
    private final boolean solvable;

    private class Node implements Comparable<Node> {
        private final Board board;
        private final int moveCount;
        private final int priority;
        private final Board prev;

        Node(Board board, int moveCount, Board prev) {
            this.board = board;
            this.moveCount = moveCount;
            this.priority = moveCount + board.manhattan();
            this.prev = prev; 
        }

        Board board() {
            return board;
        }

        boolean isGoal() {
            return board.isGoal();
        }

        Node[] neighbors() {
            Iterator<Board> it = board.neighbors().iterator();
            Node[] neighs = new Node[4];
            int k = 0;
            while (it.hasNext()) {
                Board nextBoard = it.next();
                if (!nextBoard.equals(prev))
                    neighs[k++] = new Node(nextBoard, this.moveCount + 1, this.board);
            }
            return neighs;
        }

        public int compareTo(Node that) {
            int diff = this.priority - that.priority;
            if (diff > 0)
                return 1;
            else if (diff < 0)
                return -1;
            else
                return 0;
        }

        public String toString() {
            return board.toString() + "priority: " + priority +
                String.format("(%d, %d)", priority - moveCount, moveCount) + "\n";
        }

    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();
        MinPQ<Node> pq = new MinPQ<>();
        MinPQ<Node> pqTwin = new MinPQ<>();
        pq.insert(new Node(initial, 0, null));
        pqTwin.insert(new Node(initial.twin(), 0, null));

        boolean main = true;
        boolean solvable = false;
        Queue<Board> seqMain = new Queue<>();
        Queue<Board> seqTwin = new Queue<>();
        MinPQ<Node> pqRef = pq;
        Queue<Board> sequence = seqMain;
        while (!pqRef.isEmpty()) {
            Node min = pqRef.delMin();
            if (min.isGoal()) {
                break;
            }
            else {
                update(sequence, min, pqRef);
                main = !main;
                if (main) {
                    pqRef = pq;
                    sequence = seqMain;
                } else {
                    pqRef = pqTwin;
                    sequence = seqTwin;
                }
            }
        }

        if (pqRef == pq)
            solvable = true;
        this.solvable = solvable;
        this.sequence = sequence;
    }

    private void update(Queue<Board> seq, Node min, MinPQ<Node> pqRef) {
        assert !min.isGoal();
        seq.enqueue(min.board());
        Node[] neighs = min.neighbors();
        int k = 0;
        while (k < 4 && neighs[k] != null) {
            pqRef.insert(neighs[k++]);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable())
            return -1;
        return sequence.size();
    }


    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        assert this.sequence != null;
        return this.sequence; 
    }

    // test client (see below) 
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}

