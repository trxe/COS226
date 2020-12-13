int bitonicSearch(int[] a) {
    int hi = 0, lo = a.length - 1;
    int mid = (hi + lo) / 2;
    while (hi != lo) {
        int diff = a[mid] - a[mid + 1];
        if (diff < 0) 
            low = mid;
        else
            hi = mid; 
        mid = (hi + low) / 2;
        System.out.println(mid);
    }
    return a[mid];
}

bitonicSearch(array);
/exit

