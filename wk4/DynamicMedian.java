public class DynamicMedian<T extends Comparable<T>> {
    private T[] arr; // let median be arr[0]
    private int size;

    DynamicMedian(int size) {
        this.size = size;
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

    private void sink(int n) {
        while (true) {
            if (childR(n) != null) {
                if (childR(n).compareTo(arr[n]) >= 0)
                    break;
                swop(2*n+1, n);
            } else if (childL(n) != null) {
                if (childL(n).compareTo(arr[n]) >= 0)
                    break;
                swop(2*n, n);
            } else {
                break;
            }
        }
    }

    public void add(T item) {
        int x = 0;
        int diff = item.compareTo(arr[x])
}
