import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF id;
    private boolean[][] grid;
    private int n;
    private int openSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        uf = new WeightedQuickUnionUF(n * n + 2);
        id = new WeightedQuickUnionUF(n * n + 2);
        grid = new boolean[n + 1][n + 1];
        for (int r = 1; r <= n; r++) {
            for (int c = 1; c <= n; c++) {
                grid[r][c] = false;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!inRange(row) || !inRange(col)) {
            throw new IllegalArgumentException();
        }

        if (!grid[row][col]) {
            openSites++;
            grid[row][col] = true;
        }

        if (inRange(col - 1) && isOpen(row, col - 1)) {
            uf.union(index(row, col), index(row, col - 1));
            id.union(index(row, col), index(row, col - 1));
        }
        if (inRange(col + 1) && isOpen(row, col + 1)) {
            uf.union(index(row, col), index(row, col + 1));
            id.union(index(row, col), index(row, col + 1));
        }

        if (inRange(row - 1) && isOpen(row - 1, col)) {
            uf.union(index(row, col), index(row - 1, col));
            id.union(index(row, col), index(row - 1, col));
        } else if (row == 1) {
            uf.union(0, index(row, col));
            id.union(0, index(row, col));
        }

        if (inRange(row + 1) && isOpen(row + 1, col)) {
            uf.union(index(row, col), index(row + 1, col));
            id.union(index(row, col), index(row + 1, col));
        } else if (row == n) {
            uf.union(n*n+1, index(row, col));
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!inRange(row) || !inRange(col)) {
            throw new IllegalArgumentException();
        }
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!inRange(row) || !inRange(col)) {
            throw new IllegalArgumentException();
        }
        return id.find(0) == id.find(index(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(0) == uf.find(n * n + 1);
    }

    private boolean inRange(int k) {
        return k >= 1 && k <= n;
    }

    private int index(int row, int col) {
        return (row - 1) * n + col;
    }

    /*
    public String output() {
        String out = "";
        for (int r = 1; r <= n; r++) {
            for (int c = 1; c <= n; c++) {
                if (grid[r][c]) {
                    out += index(r, c) + " ";
                } else {
                    out += ". ";
                }
            }
            out += "\n";
        }
        return out;
    }
    */

    // test client (optional)
    public static void main(String[] args) {
        try {
            Percolation test = new Percolation(5);
            test.open(1, 1);
            test.open(2, 1);
            test.open(3, 1);
            test.open(4, 1);
            test.open(4, 3);
            test.open(5, 3);
            test.open(5, 1);
            System.out.println("5,3 " + test.isFull(5, 3));
            System.out.println("5,1 " + test.isFull(5, 1));
            System.out.println(test.percolates());

            test = new Percolation(3);
            for (int r = 3; r >= 1; r--) {
                for (int c = 3; c >= 1; c--) {
                    test.open(r,c);
                }
            }
            System.out.println(test.percolates());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: n <= 0");
        }
    }
}
