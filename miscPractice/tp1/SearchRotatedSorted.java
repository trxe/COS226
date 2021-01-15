class SearchRotatedSorted {

    private static int min(int[] arr, int lo, int hi) {
        assert arr[hi] < arr[lo];
        int mid = (lo + hi) / 2;
        if (mid == arr.length - 1 && mid < arr[mid - 1]) return mid;
        if (arr[mid] < arr[mid + 1] && arr[mid] < arr[mid - 1])
            return mid;
        if (arr[mid] < arr[lo]) return min(arr, lo, mid);
        else return min(arr, mid, hi);
    }

    public static int search(int[] arr, int k) {
        int min = min(arr, 0, arr.length - 1);
        int index;
        if (k > arr[arr.length - 1]) index = binSearch(arr, k, 0, min - 1);
        else index = binSearch(arr, k, min, arr.length - 1);
        return index;
    }

    private static int binSearch(int[] arr, int k, int lo, int hi) {
        int mid = (lo + hi) / 2;
        if (hi - lo <= 1) {
            if (arr[hi] == k) return hi;
            if (arr[lo] == k) return lo;
            return -1;
        }
        if (arr[mid] < k) return binSearch(arr, k, mid, hi);
        else if (arr[mid] > k) return binSearch(arr, k, lo, mid);
        else return mid;
    }

    public static void main(String[] args) {
        int[] array = new int[] {15, 36, 1, 7, 12, 13, 14};
        int[] toFind = new int[] {12, 16, 14};

        for (int i = 0; i < toFind.length; i++) {
            int index = SearchRotatedSorted.search(array, toFind[i]);
            if (index > -1)
                System.out.println(toFind[i] + " at " + index);
            else
                System.out.println(toFind[i] + " not found");
        }
    }
}
