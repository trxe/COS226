import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

class Bitonic {
    private int[] id;

    Bitonic(int[] id) {
        this.id = id;
    }

    boolean ascendingBinarySearch(int lo, int hi, int n) {
        int mid = (hi + lo)/2;

        while (hi - lo > 1) {
            int diff = n - id[mid];
            if (diff < 0) { // lg N
                hi = mid;
            } else {
                lo = mid;
            }
            mid = (hi + lo) / 2;
        }
        return id[hi] == n || id[lo] == n;
    }

    boolean descendingBinarySearch(int lo, int hi, int n) {
        int mid = (hi + lo)/2;

        while (hi - lo > 1) {
            int diff = n - id[mid];
            if (diff > 0) { // lg N
                hi = mid;
            } else {
                lo = mid;
            }
            mid = (hi + lo) / 2;
        }
        return id[hi] == n || id[lo] == n;
    }

    boolean search(int n) {
        int max = maxIndex();
        if (n == id[max]) {
            return true;
        } else if (n > id[max]) {
            return false;
        }
        return ascendingBinarySearch(0, max - 1, n) || descendingBinarySearch(max + 1, id.length - 1, n);
    }

    int maxIndex() {
        int hi = id.length - 1;
        int lo = 0;
        int mid = (hi + lo) / 2;
        while (hi - lo > 1) { 
            int diff = id[mid] - id[mid + 1];
            if (diff < 0) { // lg N
                lo = mid;
            } else {
                hi = mid;
            }
            mid = (hi + lo) / 2;
        }
        return hi;
    }

    public static void main(String[] args) {
        int[] array = new int[] {1, 3, 5, 6, 22, 18, 12};
        Integer[] a = new Integer[] {1, 3, 5, 6, 22, 18, 12};
        List<Integer> arrayL = new ArrayList<>(Arrays.asList(a));
        Bitonic bit = new Bitonic(array);
        System.out.println(arrayL);
        System.out.println("max: " + bit.maxIndex() + " " + array[bit.maxIndex()]);
        System.out.println("5: " + bit.search(5));
        System.out.println("7: " + bit.search(7));
        System.out.println("22: " + bit.search(22));
        Bitonic bit2 = new Bitonic(new int[] { 1, 2, 3, 5, 6 });
        System.out.println("test find 5: " + bit2.ascendingBinarySearch(0, 4, 5));
    }
}
