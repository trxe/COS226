## Mergesort

|            | ``` less ``` | array access | Time          | Space    |
|------------|:------------:|:------------:|:-------------:|:--------:|
| worst case | n lg n       | 6n lg n      | Theta(n lg n) | Theta(n) |

``` less ```: recursive divide and conquer function. solve with either

- telescoping recurrence (dividing both sides by N)
- proof by induction (just for n = 2^a)

**space analysis**: Mergesort needs extra space proportional to N for the ```
merge ``` method.

> Def: a sorting algorithm is in-place if it uses less than c lg N memory.
> see Insertion, Selection, Shellsorts.

**improvements**: using insertion sort for smallest subarrays to avoid overhead
from recursive calls. implement a threshold (e.g., 7).

#### Bottom-up Mergesort

instead sorting array sizes from n to 2, we'll go from 2 to n this time. This
can actually help us get rid of recursion overhead! (see ``` MergeBU ```.)

#### Complexity

- Model of computation: operations
- Cost model: number of calls to operations
- Upper bound: cost guaranteed by *some* algo
- Lower bound: proven lower limit on cost guarantee for *all* algos
- Optimal algo: algo with best cost guarantee (i.e. upper bound of this algo ~ lower bound)

**Decision tree**: for n distinct items, how many compares are necessary for us
to know the order of these n items? **the height of the tree** presents to us the
worst case number of operations. the height of the tree is given to us by lg(n!)
as n! is the number of possible outcomes (leaves), and lg(# leaves) gives us the
height of the binary tree.

> why lg ?
> a full binary tree has 2^h - 1 nodes. thus 2^h - 1 >= # nodes >= n! >= 2^(h - 1).
> so lg(n!) gives us the height of the tree

By *Stirling's formula*, lg(n!) is approximately nlg(n). Therefore with
mergesort (O(n lg(n))), we have an optimal algorithm! BUT that is only with
respect to # compares.

##### Complexity given some more context

lower bound may not hold (we may have better way to sort) if we know
- initial order of input  (e.g. partially ordered)
- distribution of key values
- representation of the keys (e.g. digital properites of keys)

#### Stability 

e.g., when we have a table with multiple values per entry, and we want to sort
by 2 columns (sort by A then by B) and sorts that have this property have
stabilty.

| stable    | not stable |
|-----------|------------|
| insertion | selection  |
| merge     | shell      |


