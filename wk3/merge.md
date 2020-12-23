## Mergesort

|            | ``` less ``` | array access | Time          | Space    |
|------------|--------------|--------------|---------------|----------|
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
can actually help us get rid of recursion overhead!
