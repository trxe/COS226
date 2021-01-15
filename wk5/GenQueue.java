import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.RedBlackBST;
import java.util.Iterator;

class GenQueue<T> {

    private static int counter = 0;
    private RedBlackBST<Integer, T> tree;

    public GenQueue() {
        this.tree = new RedBlackBST<Integer, T>();
    }

    public void enqueue(T item) {
        tree.put(counter++, item);
    }

    public T dequeue() {
        T out = tree.get(tree.min());
        tree.deleteMin();
        return out;
    }

    public T peek(int i) {
        int min = tree.min();
        if (i < 0) throw new IllegalArgumentException();
        T out = tree.get(i + min);
        return out;
    }

    public T remove(int i) {
        int min = tree.min();
        T out = tree.get(i + min);
        tree.delete(i + min);
        return out;
    }

    public Iterable<Integer> keys() {
        return tree.keys();
    }

    public String toString() {
        String out = "";
        Iterator<Integer> it = tree.keys().iterator();
        while (it.hasNext()) {
            out += tree.get(it.next()) + " ";
        }
        out += "\n";
        return out;
    }

    public static void main(String[] args) {
        GenQueue<String> st = new GenQueue<String>();
        StdOut.print("enter strings: ");
        int i = 0;
        for (i = 0; !StdIn.isEmpty(); i++) {
            String val = StdIn.readString();
            st.enqueue(val);
        }
        StdOut.println();

        System.out.println(st.toString());

        System.out.println(st.dequeue());
        int rand = StdRandom.uniform(0, i);
        System.out.println(rand + "th value: " + st.peek(rand) + " (to be removed)");
        st.remove(rand);
        
        System.out.println(st.toString());
    }
}
