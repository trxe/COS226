class Bitonic {
    private int[] id;

    Bitonic(int[] id) {
        this.id = id;
    }

    boolean search(int n) {
        int max = maxIndex();
        int hi, lo, mid;
        if (n == id[max]) {
            return true;
        } else if (n < id[max]) {
            hi = max;
            lo = 0;
        } else {
            hi = id.length;
            lo = max;
        }
        while (hi - lo > 1) {
            mid = (hi + lo) / 2;
            if (id[mid] == n) {
                return true;
            } else if (id[mid] < n) {
                hi = mid;
            } else {
                lo = mid;
            }
        }
        return false;
    }

    int maxIndex() {
        int hi = id.length - 1;
        int lo = 0;
        int mid = (hi + lo) / 2;
        while (hi - lo > 1) { // lg N
            int diff = id[mid] - id[mid + 1];
            if (diff < 0) {
                lo = mid;
            } else {
                hi = mid;
            }
            mid = (hi + lo) / 2;
        }
        if (hi - lo == 0) {
            return mid;
        } else {
            return mid + 1;
        }
    }

    public static void main(String[] args) {
        int[] array = new int[] {1, 3, 5, 6, 22, 18, 12};
        Bitonic bit = new Bitonic(array);
        System.out.println(bit.maxIndex() + " " + array[bit.maxIndex()]);
        System.out.println(bit.search(5));
        System.out.println(bit.search(22));
        System.out.println(bit.search(7));
    }
}
