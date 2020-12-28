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
//start: lt = 0; i = 1; j = a.length - 1;
while (i < gt) {
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
```
