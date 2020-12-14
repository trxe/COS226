class StackLinkedList<T> {
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
    private int n;

    public void push(T thing) {
        first = new Node(thing, first);
        n++;
    }

    public T pop() {
        T out = first.get();
        first = first.next();
        n--;
        return out;
    }

}
