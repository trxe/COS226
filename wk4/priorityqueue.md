## Priority Queue

Removal of elements is in its natural order. Useful for many problems,
especially when database is too large to keep track of all the elements for
comparing.

##### Example problem: largest M items in a stream of N

strategy: keep only max M items in a PriorityQueue. ``` delMax() ``` upon each
node addition upon queue reaching M capacity.

### How to make Priority Queue insertion and removal lgN time?

data structure: binary heap.

- largest (or smallest) node is at the a[1] position.
- children nodes can never be larger than its parent nodes.
- due to the nature of binary numbers, in an array we can just have the keys be
  arranged in left to right top to bottom order of the tree
    - so parent of index x is at x/2,
    - children of index x is at 2*x, 2*x + 1.
- so insertion requires a promotion method (we'll call it ``` swim ```) and 
- also deletion also requires swapping with the last element and then a a
  demotion method (we'll call it ``` sink ```).

``` 
private void swim(int s) {
    int k = s;
    while (less(k / 2, k)) {
        exch(k / 2, k);
        k /= 2;
    }
}

public void insert(T key) {
    a[++last] = key;
    swim(last);
}

private void sink(int s) {
    int k = s;
    while (k < last / 2 && (less(2*k, k) || less(2*k + 1, k))) {
        if (less(2*k, 2*k + 1)) {
            exch(k, 2*k + 1);
            k = 2*k + 1;
        } else {
            exch(k, 2*k);
            k *= 2;
        }
    }
}

public T delMax() {
    T out = a[last];
    exch(1, last);
    a[last--] = null;
    sink(1);
    return out;
}
``` 

### Heapsort - with binary sorting structure

strategy: create a max-heap, and then repeatedly remove the max-key.

##### Heap creation

- create array in arbitrary order.
- go from the bottom elements (rightmost) and sink each downwards 
    - demotion used for elements already in array.
- this will make heap creation nlgn compares.

##### Heap emptying
- repeatedly ``` delMax ``` to remove the max element. takes nlgn compares
