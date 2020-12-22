import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int RESIZING_FACTOR = 2;
    private Item[] q;
    private int head;
    private int tail;
    private int n;

    public RandomizedQueue() {
        this.q = (Item[]) new Object[1];
        this.head = 0;
        this.tail = 0;
        this.n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    private void resize(int vals) {
        Item[] qN = (Item[]) new Object[(vals != 0) ? vals : 1];
        for (int i = 0; i < n; i++) {
            qN[i] = q[i + head];
        }
        q = qN;
        head = 0;
        tail = n;
    }

    private void swapTail(int index) {
        Item temp = q[tail - 1];
        q[tail - 1] = q[index];
        q[index] = temp;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item t) {
        if (t == null)
            throw new IllegalArgumentException("null cannot be inserted in queue.");
        q[tail] = t;
        tail++;
        n++;
        if (n > q.length / RESIZING_FACTOR) {
            resize(n * RESIZING_FACTOR);
        }
    }

    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty.");
        int index = StdRandom.uniform(n);
        swapTail(index + head);
        Item out = q[tail - 1];
        q[tail - 1] = null;
        n--;
        tail--;
        if (n < q.length / (RESIZING_FACTOR * 2)) {
            resize(q.length / RESIZING_FACTOR);
        }
        return out;
    }

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty.");
        int index = StdRandom.uniform(n);
        return q[index + head];
    }

    private class ArrayIterator implements Iterator<Item> {
        private final Item[] itArray;
        private int index = 0;

        ArrayIterator() { 
            int i = head;
            int k = 0;
            Item[] array = (Item[]) new Object[n];
            int[] order = StdRandom.permutation(n);
            while (i < tail && k < n) {
                array[order[k]] = q[i];
                k++;
                i++;
            }
            this.itArray = array;
            this.index = 0;
        }

        public boolean hasNext() {
            return index < itArray.length;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("end of queue.");
            }
            return itArray[index++];
        }
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    public static void main(String[] args) {
        try {
            if (args.length != 0) {
                RandomizedQueue<String> queue = new RandomizedQueue<>();
                while (!StdIn.isEmpty()) {
                    queue.enqueue(StdIn.readString());
                    if (StdRandom.bernoulli())
                        queue.dequeue();
                }

                Iterator<String> it = queue.iterator();
                while (it.hasNext()) {
                    StdOut.print(it.next() + " ");
                }
            } else {
                RandomizedQueue<String> queue = new RandomizedQueue<>();
                queue.enqueue("help");
                StdOut.println(queue.dequeue());
                queue.enqueue("you");
                queue.enqueue("save");
                queue.enqueue("me");
                queue.enqueue("save2");
                queue.enqueue("me2");
                StdOut.println(queue.dequeue());
                StdOut.println(queue.dequeue());
                StdOut.println(queue.dequeue());
                StdOut.println(queue.dequeue());
                StdOut.println(queue.sample());
                StdOut.println("remaining: " + queue.size());
                Iterator<String> it = queue.iterator();
                Iterator<String> it2 = queue.iterator();
                while (it.hasNext()) {
                    StdOut.println(it.next() + " | " + it2.next());
                }
                queue.enqueue("new");
                queue.dequeue();
                it = queue.iterator();
                it2 = queue.iterator();
                while (it.hasNext()) {
                    StdOut.println(it.next() + " | " + it2.next());
                }
            }
        } catch (IllegalArgumentException | NoSuchElementException | UnsupportedOperationException e) {
            StdOut.println(e);
        }
    }

}
