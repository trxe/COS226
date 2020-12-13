import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationWQU implements Perc {
    private WeightedQuickUnionUF uf;
    private int[][][] grid;
    private int n;
    private int openSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public PercolationWQU(int n) {
        if (n <= 0) {
            System.out.println(n);
        }
        this.n = n;
        int k = 1;
        uf = new WeightedQuickUnionUF(n*n + 2);
        grid = new int[n+1][n+1][2];
        for (int r = 1; r <= n; r++) {
            for (int c = 1; c <= n; c++) {
                grid[r][c][0] = k++;
                grid[r][c][1] = 0; // 0 represents not open
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!inRange(row) || !inRange(col)) {
            throw new IllegalArgumentException();
        }

        if (grid[row][col][1] == 0) {
            openSites++;
            grid[row][col][1] = 1;
        }

        if (inRange(row + 1) && isOpen(row+1, col)) {
            uf.union(grid[row][col][0], grid[row+1][col][0]);
        } else if (row == n) {
            uf.union(n*n+1, grid[row][col][0]);
        }

        if (inRange(row - 1) && isOpen(row-1, col)) {
            uf.union(grid[row][col][0], grid[row-1][col][0]);
        } else if (row == 1) {
            uf.union(0, grid[row][col][0]);
        }

        if (inRange(col - 1) && isOpen(row, col-1)) {
            uf.union(grid[row][col][0], grid[row][col-1][0]);
        } 
        if (inRange(col + 1) && isOpen(row, col+1)) {
            uf.union(grid[row][col][0], grid[row][col+1][0]);
        } 
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!inRange(row) || !inRange(col)) {
            throw new IllegalArgumentException();
        }
        return grid[row][col][1] != 0;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!inRange(row) || !inRange(col)) {
            throw new IllegalArgumentException();
        }
        return uf.find(0) == uf.find(grid[row][col][0]);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(0) == uf.find(n*n + 1);
    }

    // check if two are connected
    public boolean connected(int row1, int col1, int row2, int col2) {
        return uf.find(grid[row1][col1][0]) == uf.find(grid[row2][col2][0]);
    }

    // print diagram
    public void printAll() {
        String output = "";
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (isOpen(i, j)) {
                    output += " x ";
                } else {
                    output += "   ";
                }
            }
            output += "\n";
        }
        System.out.print(output);
    }

    private boolean inRange(int k) {
        return k >= 1 && k <= n;
    }

    // test client (optional)
    public static void main(String[] args) {
       try {
           Percolation test = new Percolation(3);
           test.open(1,1);
           test.open(2,2);
           test.open(1,2);
           test.open(3,3);
           test.printAll();
           System.out.println(test.percolates() ? "perc" : "no perc");
           test.open(2,3);
           test.open(2,3);
           if (test.isFull(3,3)) {
                System.out.println("ok");
           }
           test.printAll();
           System.out.println(test.percolates() ? "perc" : "no perc");
           System.out.println(test.numberOfOpenSites());
       } catch (IllegalArgumentException e) {
           System.err.println("Invalid input: n <= 0");
       } 
    }
}