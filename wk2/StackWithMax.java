import java.util.Iterator;
import java.util.NoSuchElementException;

class StackWithMax<T extends Comparable<T>> {
    private class Node implements Comparable<Node> {
        T item;
        Node next;

        Node(T item) {
            this.item = item;
        }

        Node(T item, Node next) {
            this.item = item;
            this.next = next;
        }

        public void add(Node next) {
            this.next = next;
        }

        public T get() {
            return item;
        }

        public Node next() {
            return next;
        }

        public int compareTo(Node other) {
            return this.get().compareTo(other.get());
        }
    }

    private Node first;
    private Node max;

    public boolean isEmpty() {
        return first == null;
    }

    public void push(T thing) {
        if (first == null) {
            first = new Node(thing, null);
            max = first;
        } else { 
            first = new Node(thing, first);
            max = (first.compareTo(max) > 0) ? first : max;
        }
    }

    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty.");
        }
        T out = first.get();
        Node oldFirst = first;
        first = oldFirst.next();
        oldFirst = null;
        if (max == null) {
            max = getMax();
        }
        return out;
    }

    private class ListIterator implements Iterator<T> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("end of stack.");
            }
            T out = current.get();
            current = current.next();
            return out;
        }
    }

    public Iterator<T> iterator() {
        return new ListIterator();
    }

    public T max() {
        return max.get();
    }

    private Node getMax() {
        if (isEmpty())
            throw new NoSuchElementException();
        if (max == null)
            return first;
        Node out = max;
        Node curr = first;
        while (curr != null) {
            if (out.get().compareTo(curr.get()) < 0) {
                out = curr;
            }
            curr = curr.next();
        }
        return out;
    }

    public static void main(String[] args) {
        try {
            StackWithMax<Integer> stack = new StackWithMax<>();
            stack.push(1);
            stack.push(1212);
            stack.push(230);
            stack.push(-1);
            System.out.println(stack.pop());
            System.out.println(stack.pop());
            System.out.println("max " + stack.max());
            Iterator<Integer> str = stack.iterator();
            while (str.hasNext()) {
                System.out.println(str.next());
            }
        } catch (NoSuchElementException e) {
            System.out.println(e);
        }
    }

}
