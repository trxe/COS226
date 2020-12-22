import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import java.util.NoSuchElementException;

public class Permutation {
    public static void main(String[] args) {
        try {
            if (args.length == 0)
                throw new IllegalArgumentException("missing parameter k for k-permutation.");
            int k = Integer.parseInt(args[0]);

            RandomizedQueue<String> queue = new RandomizedQueue<>();
            while (!StdIn.isEmpty()) {
                String add = StdIn.readString();
                queue.enqueue(add);
            }

            if (queue.size() < k)
                throw new IllegalArgumentException("not enough strings in input.");
            while (k > 0) {
                StdOut.println(queue.dequeue());
                k--;
            }

        } catch (IllegalArgumentException | NoSuchElementException | UnsupportedOperationException e) {
            StdOut.println(e);
        }
    }
}
