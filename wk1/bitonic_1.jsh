boolean bitonicSearch(int[] a, int n) {
    int hi = a.length - 1;
    int low = 0;
    int mid = (hi + low)/2;
    // note: a is an array of DISTINCT integers

    while (hi - low > 1) {
        int diff = n - a[mid];
        int incr = a[mid] - a[mid + 1];
        if (diff == 0) {
            return true;
        } else if (diff * incr > 0) {
            low = mid;
            mid = (hi + low)/2;
        } else {
            hi = mid;
            mid = (hi + low)/2;
        }
    }

    return false;
}

System.out.println(bitonicSearch(new int[]{1,3,4,18,3,2,1}, 18));
System.out.println(bitonicSearch(new int[]{1,2,4,18,4,3,1}, 2));
System.out.println(bitonicSearch(new int[]{1,2,4,18,4,3,1}, 3));
System.out.println(bitonicSearch(new int[]{1,3,4,18,3,2,1}, 5));
/exit
