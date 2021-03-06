import java.util.Iterator;

class StackLinkedList<T> implements Iterable<T> {
    private class Node {
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
    }

    private Node first;

    public boolean isEmpty() {
        return first == null;
    }

    public void push(T thing) {
        first = new Node(thing, first);
    }

    public T pop() {
        if (isEmpty()) {
            throw new NullPointerException("Stack is empty.");
        }
        T out = first.get();
        first = first.next();
        return out;
    }

    private class ListIterator implements Iterator<T> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            if (!hasNext()) {
                throw new NullPointerException("end of stack.");
            }
            T out = current.get();
            current = current.next();
            return out;
        }
    }

    public Iterator<T> iterator() {
        return new ListIterator();
    }
    
    public static void main(String[] args) {
        try {
            StackLinkedList<String> stack = new StackLinkedList<>();
            stack.push("help");
            stack.push("you");
            stack.push("save");
            stack.push("me");
            System.out.println(stack.pop());
            System.out.println(stack.pop());
            Iterator<String> str = stack.iterator();
            while (str.hasNext()) {
                System.out.println(str.next());
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }

}
