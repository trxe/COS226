import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class Board {

    private int[][] tiles;
    private final int size;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = Board.copyT(tiles);
        this.size = tiles[0].length;
    }
                                           
    // string representation of this board
    public String toString() {
        String out = size + "\n";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                out += " " + tiles[i][j];
            }
            out += "\n";
        }
        return out;
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        int k = 1;
        int hd = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] != k++) {
                    hd++;
                }
            }
        }

        hd--; //don't consider the last tile
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
        int md = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //StdOut.println(tiles[i][j] + " " + tileManhattan(tiles[i][j], i, j));
                md += tileManhattan(tiles[i][j], i, j);
            }
        }
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

    class Neighbours implements Iterator<Board> {

        private Board board;
        private int r0, c0;
        private int[][] list;
        private int count = 0;

        Neighbours(int r0, int c0, Board board) {
            this.board = board;
            this.r0 = r0;
            this.c0 = c0;
            this.list = new int[][] {{r0 - 1, c0}, {r0, c0 + 1}, {r0 + 1, c0}, {r0, c0 - 1}};
        }

        private boolean isValid() {
            if (count >= 4)
                return false;
            int currR = list[count][0];
            int currC = list[count][1];
            int k = board.dimension();
            if (currR < 0 || currC < 0 || currR >= k || currC >= k)
                return false;
            return true;
        }

        public boolean hasNext() {
            while (!isValid() && count < 4) {
                count++;
            }
            if (count >= 4)
                return false;
            return true;
        }
        
        public Board next() {
            while (!isValid() && count < 4) {
                count++;
            }
            if (count >= 4)
                throw new NoSuchElementException();
            Board newB = Board.copy(board);
            newB.swop(r0, c0, list[count][0], list[count][1]);
            count++;
            return newB;
        }
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
        final int r = r0;
        final int c = c0;
        return () -> new Neighbours(r, c, this);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board newB = Board.copy(this);
        if (this.size == 1)
            return newB;
        int ir = StdRandom.uniform(0, size);
        int ic = StdRandom.uniform(0, size);
        int jr = StdRandom.uniform(0, size);
        int jc = StdRandom.uniform(0, size);
        while (ir == jr && ic == jc) {
            ir = StdRandom.uniform(0, size);
            ic = StdRandom.uniform(0, size);
        }
        newB.swop(ir, ic, jr, jc);
        return newB;
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
        Board board = new Board(new int[][]{{8,1,3},{4,0,2},{7,6,5}});
        Board board2 = new Board(new int[][]{{8,1,3},{4,0,2},{7,6,5}});
        StdOut.println(board);
        StdOut.println("hamming : " + board.hamming());
        StdOut.println("manhattan : " + board.manhattan());
        StdOut.println("board and board 2 are " + (board.equals(board2) ? "" : "not ") + "equal");
        Board perf = new Board(new int[][]{{1,2,3},{4,5,6},{7,8,0}});
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

        StdOut.println("board Twin");
        StdOut.println(board.twin());

    }

}
