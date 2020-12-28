import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdRandom;

class MergeBU {

    public static <T extends Comparable<T>> void sort(T[] a, T[] aux) { 
        int n = a.length;
        int probe = 0;

        for (int sz = 1; sz < n; sz *= 2) { // double the RADIUS of merge interval; sz = radius
            probe++;
            for (int lo = 0; lo < n - sz; lo += 2 * sz) { // iterate through each valid lo
                merge(a, aux, lo, lo + sz - 1, Math.min(lo + 2*sz - 1, n - 1)); 
                // mid is either after 2 sz or the end of the array
            }
        }
        System.out.println("number of passes: " + probe);
        System.out.println("lg n: " + Math.log(n)/Math.log(2));
    }

    private static <T extends Comparable<T>> void merge(T[] a, T[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid + 1, hi);
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) 
                a[k] = aux[j++];
            else if (j > hi)
                a[k] = aux[i++];
            else if (less(aux, j, i))
                a[k] = aux[j++];
            else
                a[k] = aux[i++];
        }
        for (int k = lo; k < hi + 1; k++) {
            aux[k] = null;
        }

        assert isSorted(a, lo, hi);
    }

    private static <T extends Comparable<T>> boolean isSorted(T[] a, int lo, int hi) {
        for (int k = lo + 1; k <= hi; k++) {
            if (less(a, k, k - 1))
                return false;
        }
        return true;
    }

    public static <T> void print(T[] a) {
        String out = "";
        for (T item : a) {
            out += item + " ";
        }
        System.out.println(out);
    }

    private static <T extends Comparable<T>> void exch(T[] a, int i, int j) {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static <T extends Comparable<T>> boolean less(T[] a, int i, int j) {
        return a[i].compareTo(a[j]) < 0;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            List<String> aList = new ArrayList<>();
            System.out.println("input strings: ");
            Scanner sc = new Scanner(System.in);
            int n = 0;
            while (sc.hasNext()) {
                aList.add(sc.next());
                n++;
            }
            String[] a = aList.toArray(new String[1]);
            System.out.println("original");
            MergeBU.print(a);
            System.out.println("merge sorting from bottom-up...");
            MergeBU.sort(a, new String[n]);
            System.out.println("sorted");
            MergeBU.print(a);
        } else {
            int length = Integer.parseInt(args[0]);
            Integer[] a = new Integer[length];
            for (int i = 0; i < length; i++) {
                a[i] = StdRandom.uniform(length);
            }
            System.out.println("original");
            MergeBU.print(a);
            System.out.println("merge sorting from bottom-up...");
            MergeBU.sort(a, new Integer[length]);
            System.out.println("sorted");
            MergeBU.print(a);
        }
    }

}
