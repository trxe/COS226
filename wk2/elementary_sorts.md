## Elementary Sort 

Note: all our sort classes (we can use a ```Sort``` interface that we'll explore
here ```Selection, Insertion, Shell, Merge, Quick ``` will need two important
methods: ``` less ``` and ``` swap ```.

### Selection sort

|            | ``` less ``` | ``` swap ``` | Total      |
|------------|--------------|--------------|------------|
| best case  | Theta(n^2)   | Theta(n)     | Theta(n^2) |
| worst case | Theta(n^2)   | Theta(n)     | Theta(n^2) |

iterating over k = 0 to k = n - 1, we find the index of the max from k to n - 1,
and swap with k.

### Insertion sort

|            | ``` less ``` | ``` swap ``` | Total      |
|------------|--------------|--------------|------------|
| best case  | n - 1        | 0            | n - 1      |
| worst case | Theta(n^2)   | Theta(n^2)   | Theta(n^2) |

iterating over k = 1 to k = n - 1, we loop through j = k - 1 to 0 and swap j
with j + 1 if it a[j + 1] is less than a[j], until a[j + 1] > a[j].

approaches linear time on partially sorted arrays. (number of ``` swaps ``` =
number of inversions = cn.)

**invariant**: index is sorted from 0 to k - 1.

**worst case**: reverse order

### Shellsort

**h-sorting** items with insertion sort. we will stick to Knuth's 3x + 1
increment sequence for a good performance.

quite complex, but works because: 

- larger h : less items to sort
- smaller h: arrays are already partially sorted.

### Shuffling

|           | ``` uniform ``` | ``` swap ``` | Time |
|-----------|-----------------|--------------|------|
| all cases | n               | n            | 2n   |

iterating over k = 0 to n - 1; swap k with ``` StdRandom.uniform(k, n) ```.

**IMPORTANT.** choosing from the whole array, i.e., ``` StdRandom.uniform(n)
``` will not give a uniformly random result unlike this.

### Convex Hull

holy shit...
