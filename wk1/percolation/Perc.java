public interface Perc {
	
	// opens the site (row, col) if it is not open already
	public void open(int row, int col);

	// is the site (row, col) open?
	public boolean isOpen(int row, int col);

	// is the site (row, col) full?
	public boolean isFull(int row, int col);

	// returns the number of open sites
	public int numberOfOpenSites();

	// does the system percolate?
	public boolean percolates();
}