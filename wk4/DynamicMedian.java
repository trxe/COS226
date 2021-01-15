public class DynamicMedian<T extends Comparable<T>> {
    private T[] arr; // let median be arr[0]
    private int size = 0;

    DynamicMedian(int size) {
        this.arr = (T[]) new Object[size];
    }

    private T childL(int n) { 
        if (2 * n < size)
            return arr[2 * n];
        return null;
    }

    private T childR(int n) { 
        if (2 * n + 1 < size)
            return arr[2 * n + 1];
        return null;
    }

    private T parent(int n) { 
        if (n / 2 >= 0)
            return arr[n / 2];
        return null;
    }

    private void swop(int a, int b) {
        T temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    private void swim(int n) {
        while (n % 2 == 0 && parent(n) != null) {
            if (parent(n).compareTo(arr[n]) <= 0)
                break;
            swop(n/2, n);
            n /= 2;
        }
    }

    private void sinkLeft(int n) {
        while (childL(n) != null) {
            if (childL(n).compareTo(arr[n]) >= 0)
                break;
            swop(2*n, n);
        }
    }

    public void isEmpty() { return size == 0; }

    public void add(T item) {
        if (size + 1 > arr.length) throw new Exception("queue full!");
        if (size == 0) {
            arr[size++] = item;
            return;
        }
        arr[size++] = item;
        swim(size - 1);
        sink(size - 1);
    }

    public T removeMedian() {
        if (this.isEmpty()) throw new Exception("queue empty!");
        T out = arr[0];
        arr[0] = arr[size - 1];
        arr[size - 1] = null;
        sink(0);
    }

    public static void main(String[] args) {
        DynamicMedian<String> q = new DynamicMedian<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) q.insert(item);
            else if (!q.isEmpty()) StdOut.print(q.delMax() + " ");
        }
        StdOut.println("(" + q.size() + " left on q)");
    }

}
