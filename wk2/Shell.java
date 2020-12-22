import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Shell {
    public static <T extends Comparable<T>> void sort(T[] a) { 
        int N = a.length;

        int incr = 1;
        while (incr < N/3) {
            incr = incr*3 + 1;
        }

        while (incr >= 1) {
            for (int i = incr; i < N; i++) {
                for (int j = i; j > 0 && less(a, j, j - 1); j -= incr) {
                    exch(a, j, j - 1);
                }
            }
            incr /= 3;
        }

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
            while (sc.hasNext()) {
                aList.add(sc.next());
            }
            String[] a = aList.toArray(new String[1]);
            Shell.sort(a);

            String out = "";
            for (String item : a) {
                out += item + " ";
            }
            System.out.println(out);
        }
    }

}
