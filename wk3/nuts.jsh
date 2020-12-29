class Nut {
    int x;
    Nut(int x) { this.x = x; }
    public String toString() { return x + ""; }
}

class Bolt {
    int x;
    Bolt(int x) { this.x = x; }
    public String toString() { return x + ""; }
}

int fit(Nut n, Bolt b) {
    int c = n.x - b.x;
    //System.out.printf("%d - %d == %d \n", n.x, b.x, c);
    if (c == 0) { return 0; }
    else if (c > 0) { return 1; } // nut bigger
    else { return -1; } // bolt bigger
}

Nut[] nutz = Arrays.stream(new int[] {1, 63, 75, 80, 62, 8, 62, 63, 29}).mapToObj(x -> new
    Nut(x)).collect(Collectors.toList()).toArray(new Nut[1]);
Bolt[] boltz = Arrays.stream(new int[] {62, 8, 63, 62, 29, 75, 63, 80, 1}).mapToObj(x -> new 
    Bolt(x)).collect(Collectors.toList()).toArray(new Bolt[1]);

<T> void swap(T[] z, int i, int j) {
    T temp = z[i];
    z[i] = z[j];
    z[j] = temp;
}

void print(Nut[] nutz, Bolt[] boltz) {
    System.out.print("nutz: \t");
    for (Nut n : nutz) {
        System.out.print(n + " ");
    }
    System.out.println();
    System.out.print("boltz: \t");
    for (Bolt b : boltz) {
        System.out.print(b + " ");
    }
    System.out.println();
}

print(nutz, boltz);

int k = 0;

void sortN(Nut[] nutz, Bolt[] boltz, int bth, int lo, int hi) {
    if (lo > hi)
        return;

    int lt = lo;
    int i = lo;
    int gt = hi;

    Bolt b = boltz[bth];
    
    while (i <= gt) {
        int c = fit(nutz[i], b);
        if (c == 0) {
            i++;
        } else if (c > 0) {
            swap(nutz, gt--, i);
        } else {
            swap(nutz, lt++, i++);
        }
        //print(nutz, boltz);
    }

    swap(boltz, bth, lt);
    sortB(nutz, boltz, lt, lo, hi);
}

void sortB(Nut[] nutz, Bolt[] boltz, int nth, int lo, int hi) {
    if (lo > hi)
        return;

    int lt = lo;
    int i = lo;
    int gt = hi;

    Nut n = nutz[nth];
    
    while (i <= gt) {
        int c = fit(n, boltz[i]);
        if (c == 0) {
            i++;
        } else if (c < 0) {
            swap(boltz, gt--, i);
        } else {
            swap(boltz, lt++, i++);
        }
    }

    sortN(nutz, boltz, (lo + lt - 1) / 2, lo, lt - 1);
    sortN(nutz, boltz, (gt + 1 + hi) / 2, gt + 1, hi);
}


void sorting(Nut[] nutz, Bolt[] boltz) {
    //assume shuffled.
    int bth = nutz.length / 2;
    sortN(nutz, boltz, bth, 0, nutz.length - 1);
}

sorting(nutz, boltz);
print(nutz, boltz);

/exit

