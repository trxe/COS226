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
    
    public static void main(String[] args) {
        try {
            StackLinkedList<String> stack = new StackLinkedList<>();
            stack.push("help");
            stack.push("you");
            stack.push("save");
            stack.push("me");
            System.out.println(stack.pop());
            System.out.println(stack.pop());
            System.out.println(stack.pop());
            System.out.println(stack.pop());
            System.out.println(stack.pop());
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }

}
