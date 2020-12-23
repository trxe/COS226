import edu.princeton.cs.algs4.StdRandom;

public class DutchColor {
    private static int countColorCalls = 0;
    private static int countSwapCalls = 0;
    private final static int RED = 0;
    private final static int WHITE = 1;
    private final static int BLUE = 2;
    private static int temp;
    private int[] buckets;

    public DutchColor(int n) {
        this.buckets = new int[n];
        for (int i = 0; i < n; i++ ) {
            buckets[i] = StdRandom.uniform(3);
        }
    }

    public void swap(int a, int b) {
        temp = buckets[a];
        buckets[a] = buckets[b];
        buckets[b] = temp;
        countSwapCalls++;
    }

    public int color(int a) {
        countColorCalls++;
        return buckets[a];
    }

    public void sort(boolean verbose) {
        int i = 0;
        int j = 1;
        int k = buckets.length - 1;

        if (verbose)
            System.out.println("processing...");
        while (j < k) {
            if (i == j)
                j++;
            int c = color(j);
            if (c < DutchColor.WHITE) {
                swap(i, j);
                i++;
            } else if (c > DutchColor.WHITE) {
                swap(k, j);
                k--;
            } else {
                j++;
            }
            if (verbose) {
                String out = "";
                int n = buckets.length;
                for (int x = 0; x < n; x++ ) {
                    out += buckets[x] + (x == i || x == k || x == j ? "*" : "") + " ";
                }
                System.out.println(out);
            }
        }

        System.out.println("counts to color: " + countColorCalls);
        System.out.println("counts to swap: " + countSwapCalls);
    }

    @Override
    public String toString() {
        String out = "";
        int n = buckets.length;
        for (int i = 0; i < n; i++ ) {
            out += buckets[i] + " ";
        }
        return out;
    }

    public static void main(String[] args) {
        int length = 20;
        if (args.length != 0) { length = Integer.parseInt(args[0]); }
        DutchColor dc = new DutchColor(length);
        boolean verbose = true;
        if (length > 50) {
            verbose = false;
        } else {
            System.out.println("original: ");
            System.out.println(dc);
        }
        dc.sort(verbose);
    }

}
