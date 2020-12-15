import java.util.Iterator;

class QueueResizingArray<Item> {

    private Item[] q = (Item[]) new Object[1];
    private int tail = 0;
    private int head = 0;
    private final static int RESIZING_FACTOR = 2;

    public boolean isEmpty() {
        return q[head] == null;
    }

    private void resize(int vals) {
        int n = tail - head;
        Item[] qN = (Item[]) new Object[vals];
        for (int i = 0; i < n; i++) {
            qN[i] = q[head + i];
        }
        q = qN;
        head = 0;
        tail = n;
    }

    private void probe() {
        System.out.println("head: " + head + "; tail: " + tail + " [" + this + "]");
    }

    public void enqueue(Item t) {
        q[tail++] = t;
        if (tail - head > q.length / RESIZING_FACTOR) {
            resize(q.length * RESIZING_FACTOR);
        }
        //System.out.print("enqueue ");
        //probe();
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NullPointerException("Queue is empty.");
        }
        Item out = q[head];
        q[head++] = null;
        if (tail - head < q.length / (RESIZING_FACTOR * 2)) {
            resize(q.length / RESIZING_FACTOR);
        }
        //System.out.print("dequeue ");
        //probe();
        return out;
    }

    private class ArrayIterator implements Iterator<Item> {
        private int current = head;

        public boolean hasNext() {
            return current != tail;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NullPointerException("end of queue.");
            }
            return q[current++];
        }
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < q.length; i++) {
            if (q[i] == null) { out += "* "; } 
            else { out += q[i] + " "; }
        }
        return out;
    }

    public static void main(String[] args) {
        try {
            QueueResizingArray<String> queue = new QueueResizingArray<>();
            queue.enqueue("help");
            queue.enqueue("you");
            queue.enqueue("save");
            queue.enqueue("me");
            queue.enqueue("save");
            queue.enqueue("me");
            System.out.println(queue.dequeue());
            System.out.println(queue.dequeue());
            System.out.println(queue.dequeue());
            System.out.println("remaining: ");
            Iterator<String> it = queue.iterator();
            while (it.hasNext()) {
                System.out.println(it.next());
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }



}
