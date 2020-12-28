import edu.princeton.cs.algs4.StdRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Scanner;

class Quick {
    
    public static <T extends Comparable<T>> void sort(T[] a) { 
        Quick.shuffle(a);
        Quick.sort(a, 0, a.length - 1);
    }

    private static <T extends Comparable<T>> void sort(T[] a, int lo, int hi) { 
        if (lo >= hi) {
            return;
        }
        int mid = partition(a, lo, hi);
        sort(a, lo, mid - 1);
        sort(a, mid + 1, hi);
    }

    private static <T extends Comparable<T>> int partition(T[] a, int lo, int hi) { 
        int i = lo;
        int j = hi + 1;
        while (true) {
            while (less(a[++i], a[lo])) {
                if (i == hi)
                    break;
            }

            while (less(a[lo], a[--j])) {
                if (j == lo)
                    break;
            }

            if (i >= j)
                break;

            swap(a, i ,j);

        }
        swap(a, j, lo);
        return j;
    }

    private static <T> void shuffle(T[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            int j = StdRandom.uniform(i+1, n);
            swap(a, i, j);
        }
    }

    private static <T extends Comparable<T>> boolean less(T x, T y) {
        int comp = x.compareTo(y);
        if (comp > 0)
            return false;
        return true;
    }

    public static <T> void print(T[] a) {
        String out = "";
        for (T item : a) {
            out += item + " ";
        }
        System.out.println(out);
    }

    private static <T> void swap(T[] a, int i, int j) {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static <T extends Comparable<T>> T select(T[] a, int kthHighest) {
        Quick.shuffle(a);
        if (a.length - kthHighest < 0)
            throw new IllegalArgumentException();
        return Quick.select(a, a.length - kthHighest, 0, a.length - 1);
    }

    private static <T extends Comparable<T>> T select(T[] a, int kth, int lo, int hi) {
        int mid = partition(a, lo, hi);
        if (mid == kth) {
            return a[mid];
        } else if (mid > kth) {
            return Quick.select(a, kth, lo, mid - 1);
        } else {
            return Quick.select(a, kth, mid + 1, hi);
        }
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
            Quick.print(a);
            Quick.sort(a);
            System.out.println("sorted");
            Quick.print(a);
        } else if (args.length == 1) {
            int length = Integer.parseInt(args[0]);
            Integer[] a = new Integer[length];
            for (int i = 0; i < length; i++) {
                a[i] = StdRandom.uniform(length);
            }
            System.out.println("original");
            Quick.print(a);
            Quick.sort(a);
            System.out.println("sorted");
            Quick.print(a);
        } else if (args.length == 2) {
            int length = Integer.parseInt(args[0]);
            int kthHighest = Integer.parseInt(args[1]);
            Integer[] a = new Integer[length];
            for (int i = 0; i < length; i++) {
                a[i] = StdRandom.uniform(length);
            }
            System.out.println("list");
            Quick.print(a);
            int value = Quick.select(a, kthHighest);
            System.out.printf("%dth highest: %d\n", kthHighest, value);
            System.out.println("---------------------");
            System.out.println("sort for verification");
            Quick.sort(a);
            Quick.print(a);
        }

    }
}
