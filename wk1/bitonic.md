## Bitonic

### \\( \sim 3\lg(n) \\) version

- find max (lg n compares). this process can be done by repeatedly narrowing the
  search to 2 elements.
- ascending binary search to the left (lg n)
- descending binary search to the right (lg n)

### \\( \sim 2\lg(n) \\) version

- avoid finding max (to come up with how to do this, set up recursive function)
- n > middle element : search can simply be narrowed to one side
    - max to the left of centre: (!incr) --> ```bitonicSearch``` left
    - max to the right of centre: (incr) --> ```bitonicSearch``` right
    - not worst case.
- n < middle element : search must go both ways.
    - max to the left of centre: (!incr) --> ```ascBinSearch``` left + ```bitonicSearch``` right
    - max to the right of centre: (incr) --> ```bitonicSearch``` left + ```ascBinSearch``` left 
    - worst case: lg(n/2) * lg(n/2) due to f(n) = f(n/2) + lg(n/2) + c
    - can this be further optimized? yes.
        - ```ascBinSearch``` left + ```desBinSearch``` right
        - we can treat those between max and mid as bigger than max, therefore
          eliminate. this is because id[max] > id[mid] > n.
        - worst case: lg(n/2) + lg(n/2) from each BinSearch

