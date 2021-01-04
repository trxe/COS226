import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Queue;
import java.util.Iterator;

public class Board {

    private int[][] tiles;
    private final int size;
    private int hamming = -1;
    private int manhattan = -1;
    private Board twinB;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = Board.copyT(tiles);
        this.size = tiles[0].length;
    }
                                           
    // string representation of this board
    public String toString() {
        StringBuilder out = new StringBuilder(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                out.append(" " + tiles[i][j]);
            }
            out.append("\n");
        }
        return out.toString();
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        if (this.hamming >= 0)
            return this.hamming;

        int k = 1;
        int hd = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] != k) {
                    hd++;
                }
                k++;
            }
        }

        // don't consider the last tile
        hd--;
        this.hamming = hd;
        return hd;
    }

    private int tileManhattan(int k, int r, int c) {
        if (k == 0)
            return 0;

        int goalR = k / size;
        int goalC = k % size;
        if (goalC == 0) {
            goalC = size - 1;
            goalR -= 1;
        } else {
            goalC -= 1;
        }
        return Math.abs(goalR - r) + Math.abs(goalC - c);
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (this.manhattan >= 0)
            return this.manhattan;

        int md = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                md += tileManhattan(tiles[i][j], i, j);
            }
        }

        this.manhattan = md;
        return md;
    }


    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        } else if (y instanceof Board) {
            Board that = (Board) y;
            if (this.size != that.size)
                return false;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (this.tiles[i][j] != that.tiles[i][j])
                        return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidSwop(int[][] list, int count) {
        if (count >= 4)
            return false;
        int currR = list[count][0];
        int currC = list[count][1];
        int k = this.dimension();
        if (currR < 0 || currC < 0 || currR >= k || currC >= k)
            return false;
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int r0 = -1;
        int c0 = -1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == 0) {
                    r0 = i;
                    c0 = j;
                    break;
                }
            }
        }
        int[][] list = new int[][] {{r0 - 1, c0}, {r0, c0 + 1}, {r0 + 1, c0},
            {r0, c0 - 1}};
        Queue<Board> neighs = new Queue<>();
        for (int i = 0; i < 4; i++) {
            if (isValidSwop(list, i)) {
                Board newNeighbor = Board.copy(this);
                newNeighbor.swop(list[i][0], list[i][1], r0, c0);
                neighs.enqueue(newNeighbor);
            }
        }

        return neighs;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (twinB != null)
            return twinB;

        twinB = Board.copy(this);
        if (this.size == 1)
            return twinB;
        int ir = StdRandom.uniform(0, size);
        int ic = StdRandom.uniform(0, size);
        int jr = StdRandom.uniform(0, size);
        int jc = StdRandom.uniform(0, size);
        while ((ir == jr && ic == jc) || twinB.tiles[ir][ic] == 0 ||
                twinB.tiles[jr][jc] == 0) {
            ir = StdRandom.uniform(0, size);
            ic = StdRandom.uniform(0, size);
            jr = StdRandom.uniform(0, size);
            jc = StdRandom.uniform(0, size);
        }
        twinB.swop(ir, ic, jr, jc);
        return twinB;
    }

    private static int[][] copyT(int[][] that) {
        int n = that[0].length;
        int[][] out = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                out[i][j] = that[i][j];
            }
        }
        return out;
    }

    private static Board copy(Board that) {
        return new Board(Board.copyT(that.tiles));
    }

    private void swop(int ir, int ic, int jr, int jc) {
        int temp = tiles[ir][ic];
        tiles[ir][ic] = tiles[jr][jc];
        tiles[jr][jc] = temp;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        if (args.length == 0) {
            Board board = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
            Board board2 = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
            StdOut.println(board);
            StdOut.println("hamming : " + board.hamming());
            StdOut.println("manhattan : " + board.manhattan());
            StdOut.println("board and board 2 are " + (board.equals(board2) ? ""
                        : "not ") + "equal");
            Board perf = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
            StdOut.println(perf);
            StdOut.println("hamming : " + perf.hamming());
            StdOut.println("manhattan : " + perf.manhattan());
            StdOut.println("perf is " + (perf.isGoal() ? "" : "not ") + "goals");
            StdOut.println("board is " + (board.isGoal() ? "" : "not ") + "goals");

            Iterator<Board> it = board.neighbors().iterator();
            StdOut.println("neighbours of board");
            while (it.hasNext()) {
                StdOut.println(it.next());
            }

            it = perf.neighbors().iterator();
            StdOut.println("neighbours of perf");
            while (it.hasNext()) {
                StdOut.println(it.next());
            }
        } else if (args.length == 1) {
            In in = new In(args[0]);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    tiles[i][j] = in.readInt();
            Board initial = new Board(tiles);
            StdOut.println("orig board: ");
            StdOut.println(initial);
            StdOut.println("twin board: ");
            StdOut.println(initial.twin());
        }
    }

}
