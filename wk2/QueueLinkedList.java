import java.util.Iterator;

class QueueLinkedList<T> implements Iterable<T> {
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

    private Node last;
    private Node first;

    public boolean isEmpty() {
        return first == null;
    }

    public void enqueue(T thing) {
        if (isEmpty()) {
            first = new Node(thing);
            last = first;
        } else {
            Node toAdd = new Node(thing);
            last.add(toAdd);
            last = last.next();
        }
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new NullPointerException("Queue is empty.");
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
                throw new NullPointerException("end of queue.");
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
            QueueLinkedList<String> queue = new QueueLinkedList<>();
            queue.enqueue("help");
            queue.enqueue("you");
            queue.enqueue("save");
            queue.enqueue("me");
            System.out.println(queue.dequeue());
            Iterator<String> str = queue.iterator();
            while (str.hasNext()) {
                System.out.println(str.next());
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }

}
