import java.util.Iterator;

class StackResizingArray<T> {

    private T[] array = (T[]) new Object[1];
    private int n = 0;
    private final static int RESIZING_FACTOR = 2;

    public boolean isEmpty() {
        return n == 0;
    }

    private void resize(int vals) {
        T[] newArray = (T[]) new Object[vals];
        for (int i = 0; i < n; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    public void push(T t) {
        if (n > array.length / RESIZING_FACTOR) {
            resize(array.length * 2);
        }
        array[n++] = t;
    }

    public T pop() {
        if (isEmpty()) {
            throw new NullPointerException("Stack is empty.");
        }
        T out = array[n - 1];
        if (n < array.length / (RESIZING_FACTOR * 2)) {
            resize(array.length / RESIZING_FACTOR);
        }
        array[--n] = null;
        return out;
    }

    private class ArrayIterator implements Iterator<T> {
        private int current = n;

        public boolean hasNext() {
            return current > 0;
        }

        public T next() {
            if (!hasNext()) {
                throw new NullPointerException("end of stack.");
            }
            return array[--current];
        }
    }

    public Iterator<T> iterator() {
        return new ArrayIterator();
    }
    
    public static void main(String[] args) {
        try {
            StackResizingArray<String> stack = new StackResizingArray<>();
            stack.push("help");
            stack.push("you");
            stack.push("save");
            stack.push("me");
            System.out.println(stack.pop());
            System.out.println(stack.pop());
            System.out.println("remaining: ");
            Iterator<String> it = stack.iterator();
            while (it.hasNext()) {
                System.out.println(it.next());
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }
}
