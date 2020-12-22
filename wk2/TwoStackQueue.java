import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Random;

class TwoStackQueue<T> {
    private T[] stackForward;
    private T[] stackBackward;
    private int f = 0;
    private int b = 0;

    public TwoStackQueue() {
        stackForward = (T[]) new Object[1];
        stackBackward = (T[]) new Object[1];
    }

    private boolean isEmpty() {
        return f == 0 && b == 0;
    }

    private void resizeF(int size) {
        if (size == 0)
            size = 1;
        T[] newForward = (T[]) new Object[size];
        for (int i = 0; i < f; i++) {
            newForward[i] = stackForward[i];
        }
        stackForward = newForward;
    }

    private void resizeB(int size) {
        if (size == 0)
            size = 1;
        T[] newBackward = (T[]) new Object[size];
        for (int i = 0; i < b; i++) {
            newBackward[i] = stackBackward[i];
        }
        stackBackward = newBackward;
    }

    private void pushF(T t) {
        stackForward[f] = t;
        f++;
    }

    private T popB() {
        T out = stackBackward[b-1];
        stackBackward[b-1] = null;
        b--;
        return out;
    }

    private void transfer() {
        if (f > 0) {
            if (b > stackBackward.length / 2) {
                resizeB(b * 2);
            }
            T pop = stackForward[f-1];
            stackForward[f-1] = null;
            f--;
            stackBackward[b] = pop;
            b++;
        }
    }

    public void enqueue(T t) {
        if (t == null)
            throw new IllegalArgumentException();
        pushF(t);
        if (f > stackForward.length / 2) {
            resizeF(f * 2);
        }
    }

    public T dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        else if (b == 0) {
            while (f != 0) {
                transfer();
            }
        }
        T out = popB();
        if (f < stackForward.length / 4) {
            resizeF(stackForward.length / 2);
        }
        if (b < stackBackward.length / 4) {
            resizeB(stackBackward.length / 2);
        }
        return out;
    }

    public static void main(String[] args) {
        try {
            TwoStackQueue<String> q = new TwoStackQueue<>();
            if (args.length != 0) {
                if (Integer.parseInt(args[0]) == 0) {
                    Scanner sc = new Scanner(System.in);
                    Random rng = new Random();
                    while (sc.hasNext()) {
                        q.enqueue(sc.next());
                        if (rng.nextBoolean()) {
                            System.out.print(q.dequeue() + " ");
                        }
                    }
                }
            }
        } catch (IllegalArgumentException | NoSuchElementException e) {
            System.out.println(e);
        }
    }
}
