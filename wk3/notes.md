## What algo should I use?

what are my needs (some) of the considerations

- stable?
- parallel?
- random order?
- deterministic?
- large or small items?
- linked list or arrays?
- guaranteed performance?
- are all keys distinct or not?

due to different needs, our system sort (merge for objects, quick for
primitives) may not be good enough. this is why we may need to developing our
own sorts.

#### Some sorting algorithms

- Internal sorts
    - Insertion sort, selection sort, bubblesort, shaker sort,
    - Quicksort, mergesort, heapsort, samplesort, shellsort,
    - Solitaire sort, red-black sort, splaysort, Yaroslavkiy sort, psort, ...
- External sorts
    - poly-phase mergesort, cascade-merge, oscillating sort
- String/radix sort
    - distribution, MSD, LSD, 3-way string quicksort
- parallel sorts
    - bitonic sort, batcher even-odd sort
    - smooth sort, cube sort, column sort
    - GPUsort

