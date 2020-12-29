## QuickSort

### The plan

1. Shuffle
2. Sort to partition so that
    - entry a[j] is in place,
    - to the left of j, all keys are less than a[j]
    - to the right of j, all keys are more than a[j]
3. recursively sort the partitions (lo to mid - 1, mid + 1 to hi)

### Time complexity:
- worst case: n^2 (BUT resolved by shuffle, makes probability of sorted from low
  to hi/hi to low extremely low.)
- average case: solve the recurrence relation (hint, use perturbation, and
  "extending" the telescope)
- IMPT: if partition stops on equal keys, the algo goes quadratic!
    - putting all the items equal to the partition item onto one side takes
      extra time. then if all keys are equal, on each iteration, only keep
      removing one element

### Selection

### 3-way partition: Dutch Flag Problem

3 pointers: **lt** for partition element (less than); **i** for the end of the
subarray of elements equal to a[lt]; **gt** for the last element (greater than)

```
sort(T[] a, int lo, int hi) {
    if (hi <= lo)
        return;

    //start: lt = lo; i = 1; gt = hi;
    while (i <= gt) {
        if (a[lt] == a[i]) i++;
        else if (a[lt] > a[i]) {
            swap(a, lt, i);
            lt++;
            i++;
        } else {
            swap(a, i, gt);
            gt--;
        }
    }
    
    sort(a, lo, lt - 1);
    sort(a, gt + 1, hi);
    // everything else between lt and gt inclusive are untouched.
}
```
QuickSort with 3-way partition is *entropy-optimal*, i.e., proportional to lower
bound. Its time complexity is lg(N!/(x1!x2!...xN!)) ~ sum_{i=1}^n xi * lg(xi/N).
in many applications, e.g. when there is only a constant number of distinct
keys, the running time can become linear!

### System Sorts

Java uses a tuned quicksort for primitives, and tuned mergesort for objects.
(possibly because mergesort requires extra space, and dev believes that if
dealing with objects/instances there will be enough such space.)

use ``` Arrays.sort() ``` ! its allowed

#### System sorts may not be perfect: C ``` qsort() ``` function problem

quadratic time is necessary to sort
- organ-pipe arrays *Version 7 Unix - 1979*
- random binary arrays *BSD Unix - 1983*

thus this motivated the Unix engineers to find better partitioning item
(together with a better partitioning scheme involving 3-way).

- small arrays: middle entry
- medium arrays: median of 3
- large arrays: Tukey's ninther (9 equally spaced elements, 3 groups of 3, take
  the median of their medians. only 12 compares!)

this was developed by Jon Bentley and Doug McIlroy. also there are hundreds of
sorting algorithms...

