int[] a = new int[] {2, 3, 6, 8, 9, 12, 14, 20};
int[] b = new int[] {3, 6, 7, 10, 12, 14, 15, 18};

double median(int[] x, int[] y, int xp, int yp) {
    if (xp < 0 || yp < 0 || xp >= x.length || yp >= y.length)
        throw new IllegalArgumentException();
    int xpVyp1 = x[xp] - y[yp + 1];
    int ypVxp1 = y[yp] - x[xp + 1];
    System.out.println(x[xp] + " " + y[yp]);
    if (xpVyp1 <= 0 && ypVxp1 <= 0) {
        int n = x.length + y.length;
        System.out.println(x[xp] + " " + y[yp]);
        if (n % 2 == 0) 
            return (double) (x[xp] + y[yp]) / 2;
        return Math.max(x[xp], y[yp]);
    } else if (xpVyp1 > 0) {
        int k = xp / 2;
        return median(x, y, xp - k + 1, yp + k);
    } else {
        int k = yp / 2;
        return median(x, y, xp + k, yp - k + 1);
    }
}

int medA = (a.length - 1)/ 2;
int medB = (a.length + b.length + 1) / 2 - medA;

System.out.println(median(a, b, medA, medB - 1));

/exit
