import edu.princeton.cs.algs4.StdOut;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    // constant worst case time: use nodes and linked list.
    // 48n + 192 memory usage.
    private class Node {
        Item item;
        Node nextNode;
        Node prevNode;

        Node(Item item) {
            this.item = item;
        }

        public void addNext(Node nextNode) {
            this.nextNode = nextNode;
        }

        public void addPrev(Node prevNode) {
            this.prevNode = prevNode;
        }

        public Item get() {
            return item;
        }

        public Node next() {
            return nextNode;
        }

        public Node prev() {
            return prevNode;
        }

        /*
        public String toString() {
            return "" + item;
        }
        */
    }

    private Node first;
    private Node last;
    private int n;

    // construct an empty deque
    public Deque() {
        this.first = null;
        this.last = first;
        this.n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("null cannot be inserted in queue.");
        if (isEmpty()) {
            last = new Node(item);
            first = last;
        } else {
            Node toAdd = new Node(item);
            toAdd.addNext(first);
            first.addPrev(toAdd);
            first = toAdd;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("null cannot be inserted in queue.");
        if (isEmpty()) {
            last = new Node(item);
            first = last;
        } else {
            Node toAdd = new Node(item);
            last.addNext(toAdd);
            toAdd.addPrev(last);
            last = toAdd;
        }
        n++;
    }
    
    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("deque is empty.");
        Node oldFirst = first;
        Item out = oldFirst.get();
        if (size() != 1) {
            first = oldFirst.next();
            first.addPrev(null);
        } else {
            last = null;
            first = last;
        }
        n--;
        return out;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("deque is empty.");
        Node oldLast = last;
        Item out = oldLast.get();
        if (size() != 1) {
            last = oldLast.prev();
            last.addNext(null);
        } else {
            last = null;
            first = last;
        }
        n--;
        return out;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) 
                throw new NoSuchElementException("end of deque.");
            Item next = current.get();
            current = current.next();
            return next;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    /*
    @Override
    public String toString() {
        String out = "[ ";
        Node start = first;
        while (start != null) {
            out += start + " ";
            start = start.next();
        }
        out += "]";
        return out;
    }
    */

    // unit testing (required)
    public static void main(String[] args) {
        try {
            Deque<Integer> d = new Deque<>();
            d.addLast(1);
            StdOut.println("size : " + d.size());
            d.addFirst(22);
            StdOut.println("size : " + d.size());
            d.addFirst(3);
            StdOut.println("size : " + d.size());
            d.addLast(44);
            StdOut.println("size : " + d.size());

            Iterator<Integer> it = d.iterator();
            while (it.hasNext()) {
                StdOut.print(it.next() + " ");
            }
            StdOut.println("\n______");

            StdOut.print(d.removeFirst());
            StdOut.println(" size : " + d.size());
            StdOut.print(d.removeLast());
            StdOut.println(" size : " + d.size());
            StdOut.print(d.removeFirst());
            StdOut.println(" size : " + d.size());
            StdOut.print(d.removeLast());
            StdOut.println(" size : " + d.size());
            StdOut.println("isEmpty: " + d.isEmpty());
        } catch (IllegalArgumentException | NoSuchElementException | UnsupportedOperationException e) {
            StdOut.println(e);
        }
    }

}
