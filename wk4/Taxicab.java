import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.MinPQ;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.Iterator;

class Taxicab {

    public static void main(String[] args) {
        StdOut.print("enter a maximum: ");
        int n = StdIn.readInt();
        List<int[]> taxicabs = new ArrayList<>();
        Function<int[], Integer> sumCube = x -> x[0]*x[0]*x[0] + x[1]*x[1]*x[1];
        MinPQ<int[]> queue = new MinPQ<>((x, y) -> sumCube.apply(x) - sumCube.apply(y));
        for (int i = 1; i <= n; i++)
            for (int j = i; j <= n; j++)
                queue.insert(new int[] {i, j});
        StdOut.println();


        int[] key = queue.delMin();
        while (!queue.isEmpty()) {
            int val1 = sumCube.apply(key);
            int val2 = sumCube.apply(queue.min());
            if (val1 == val2) {
                int[] match = queue.delMin();
                taxicabs.add(new int[] { key[0], key[1], match[0], match[1] });
            } else {
                key = queue.delMin();
            }
        }

        Iterator<int[]> taxis = taxicabs.iterator();
        while (taxis.hasNext()) {
            key = taxis.next();
            StdOut.println("(" + key[0] + "^3 + " + key[1] + "^3 = " + key[2] + "^3 + " + key[3] + "^3)");
        }
    }
}
