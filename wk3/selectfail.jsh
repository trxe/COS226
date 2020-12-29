int[] a = new int[] {2, 3, 6, 8, 9, 12, 14, 20};
int[] b = new int[] {3, 6, 7, 10, 12, 14, 15, 18};

int k = 3;

int median(int[] a, int[] b, int loA, int hiA, int loB, int hiB) {
    int medA = (loA + hiA) / 2;
    int medB = (loB + hiB) / 2;
    int diff = a[medA] - b[medB];

    if (diff == 0) {
        return a[medA];
    } else if (diff < 0) {
        return median(a, b, medA, hiA, loB, medB);
    } else {
        return median(a, b, loA, medA, medB, hiB);
    }
}

int median(int[] a, int[] b) {
    return median(a, b, 0, a.length, 0, b.length);
}

median(a, b);

/exit
