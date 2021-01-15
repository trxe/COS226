import java.util.Arrays;

class Search {

    public static int search(int[] a, int k) {
        if (a[0] > k || a[a.length - 1] < k) return -1;
        return search(a, 0, a.length - 1, k);
    }

    private static int search(int[] a, int lo, int hi, int k) {
        int i = (hi + lo) / 2;
        if (hi - lo <= 1) return -1;
        if (a[i] < k) {
            return search(a, i, hi, k);
        } else {
            if (a[i] == k && a[i] > a[i - 1])
                return i;
            else
                return search(a, lo, i, k);
        }
    }

    public static void majority(int[] aOrig) {
        int n = aOrig.length;
        int[] a = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = aOrig[i];
        int k = 0;
        int total = n;
        while (k < n - 1) {
            if (a[k] != a[k+1]) {
                a[k] = -1;
                a[k+1] = -1;
                k += 2;
                total -= 2;
            } else {
                k++;
            }
        }
        for (int i = 0; i < n; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }


    public static void main(String[] args) {

        int[] test = new int[] {1, 4, 1, 5, 2, 1, 2, 1};
        majority(test);

        int[] a = new int[] { 13, 100, 37, 29, 69, 19, 39, 64, 33, 5, 88, 39, 95, 58,
        31, 85, 42, 58, 53, 27, 10, 18, 32, 15, 56, 64, 80, 47, 32, 2, 59, 6, 8, 4, 42,
        8, 1, 15, 31, 36, 30, 81, 96, 96, 22, 44, 56, 97, 28, 45, 15, 32, 29, 20, 1, 88,
        2, 10, 22, 82, 92, 52, 46, 8, 74, 23, 71, 82, 90, 55, 92, 51, 62, 23, 83, 73,
        65, 30, 37, 46, 21, 73, 19, 21, 51, 34, 12, 34, 6, 28, 18, 39, 94, 82, 29, 98,
        62, 89, 98, 51};
        majority(a);
        Arrays.sort(a);

        int find = 3;

        System.out.println(search(a, find));
        int i = 0;
        while (i < a.length && a[i] != find) {
            i++;
        }
        System.out.println(i);

    }
}
