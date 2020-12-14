import java.util.Arrays;

boolean ascendingBinarySearch(int[] a, int n) {
    int hi = a.length - 1;
    int lo = 0;
    int mid = (hi + lo)/2;
    int diff = n - a[mid];

    while (hi - lo > 1) {
        if (diff == 0) {
            return true;
        } else if (diff < 0) {
            hi = mid;
        } else {
            lo = mid;
        }
        mid = (hi + lo) / 2;
    }
    return a[hi] == n;
}

boolean descendingBinarySearch(int[] a, int n) {
    int hi = a.length - 1;
    int lo = 0;
    int mid = (hi + lo)/2;
    int diff = n - a[mid];

    while (hi - lo > 1) {
        if (diff == 0) {
            return true;
        } else if (diff > 0) {
            hi = mid;
        } else {
            lo = mid;
        }
        mid = (hi + lo) / 2;
    }
    return a[hi] == n;
}

//not working due to issues with arr
boolean bitonicSearch(int[] a, int n) {
    int hi = a.length - 1;
    int lo = 0;
    int mid = (hi + lo)/2;
    // note: a is an array of DISTINCT integers

    int diff = n - a[mid];
    int incr = a[mid] - a[mid + 1];
    if (diff == 0) {
        return true;
    } else if (diff > 0) { // n > a[mid]
        if (incr > 0) { // max on the right
            int[] arr = Arrays.asList(a)
                            .subList(mid, hi)
                            .stream().mapToInt(i -> i).toArray();
            return bitonicSearch(arr, n);
        } else { // max on the left
            int[] arr = Arrays.asList(a)
                            .subList(lo, mid)
                            .stream().mapToInt(i -> i).toArray();
            return bitonicSearch(arr, n);
        }
    } else { // n < a[mid]
        int[] arrleft = Arrays.asList(a)
                        .subList(lo, mid)
                        .stream().mapToInt(i -> i).toArray();
        int[] arrright = Arrays.asList(a)
                        .subList(mid, hi)
                        .stream().mapToInt(i -> i).toArray();
        return ascendingBinarySearch(arrleft, n) ||
            descendingBinarySearch(arrright, n);
    }
}

System.out.println(bitonicSearch(new int[]{1,3,4,18,3,2,1}, 18));
System.out.println(bitonicSearch(new int[]{1,2,4,18,4,3,1}, 2));
System.out.println(bitonicSearch(new int[]{1,2,4,18,4,3,1}, 3));
System.out.println(bitonicSearch(new int[]{1,3,4,18,3,2,1}, 5));
System.out.println(ascendingBinarySearch(new int[]{1,3,4,21}, 2));
/exit
