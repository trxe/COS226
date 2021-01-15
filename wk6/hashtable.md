## Hash tables

If we have integer keys, an array can implement an unordered symbol table.

If our keys are non-integers, we do the same by having a **hash function** that
converts keys into array indices.

<div class="mermaid">
graph LR
    id1([key array]) --> id2[key]
    id2 --> id3[hash function]
    id3 --> id4[i]
    id4 --> id5([value array])
    id5 --> id6[val]
    classDef item fill:#bbf,stroke-width:3px;
    classDef func fill:#add,stroke:#abc;
    class id2,id4,id6 item;
    class id3 func;
</div>
    
Hashing usually requires two steps:
1. computation of the **hash function**
2. **collision** resolution *(different keys hashing to the same index)*

#### Why use hashing and not a balanced ST?

Provides a *(probabilistic) amortized* O(1) time. How does it do so? Analysing
the **time-space tradeoff**, if we have

- unlimited memory: every key is its own unique index.
- unlimited time: every key hashes to the same index, then sequential search for
  value

Talk about unattainable standards. Hashing gives us a way to strike a balance.

### Hash Functions
If our array is of size $M$, the hash function is described by:
$$ f : \text{keys} \rightarrow \{0, \cdots, M - 1\} $$

#### Modular Hashing
See Horner's method. We compute the key as an integer of arbitrary base-k (so k
must be greater than the max number of possbilities per field). So for n fields:

$$ a_0 + a_1x^1 + \cdots + a_nx^n = a_0 + x(a_1 + x(\cdots + x(a_n)))$$

```java
int hashCode() {
    int code = 0;
    for (int i = a.length - 1; i >= a.length; i--)
        code = code*k + a[i];
    return code % m; // to give a value between 0 and m - 1
}
``` 
How do we deal with floating points then? Instead of multiplying by m and
rounding down, we can do modular hashing on the binary key (cos the former leads
to more collisions, since it gives weight to the significant figures, i.e., its
not uniformly using the info in the float)

There is only one unique base-k representation of any natural number k.

```java
int hash(Key key) { return key.hashCode() & 0x7ffffff % M; }
// this prevents corner case bugs e.g. -2^31
``` 

Each key must be equallty likely to hash an integer between 0 and M - 1 (the
**uniform hashing assumption**). Imagine throwing balls uniformly at random into
M bins.

> Birthday problem. Expect two balls in the same bin after $\sim \sqrt{\pi M / 2}$
> tosses. i.e. collisions happen!
 
> Coupon collector problem. Every bin has $>=$ 1 ball after $\sim M ln M$ tosses.

### Hash Tables

What do we do when two keys hash to the same index?

#### Separate chaining

Separate chaining builds a linked list for each hash code.
O(1) access table, about 3 to 5 keys per linked list. Thus amortized O(1).

*Proposition.* Under the uniform hashing assumption, the number of keys per list
is within a constant factor of N/M close to 1. (Proof sketch: distribution
obeys a binomial distribution.)

#### Linear probing

concept of *Open addressing*: use an array. try to put the key at the index of
its hash first, if its not that put it in the next available index. (loop back
to 0 if reached arr.length - 1)

clearly there is the problem of **clustering** so when the table gets fuller and
fuller, there are more and more collisions and new keys hash into big clusters.

So how close can we get the table to full without compromising amortized O(1)
performance? This was studied by Knuth in the 60s, as the *parking problem*. can
use a classical balls and bins type analysis.

result: with M/2 cars, mean displacement is $\sim 3/2$

full: with M cars, mean displacement is $\sim \sqrt{\pi m / 8}$
