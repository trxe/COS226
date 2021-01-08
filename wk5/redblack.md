## Summary of important invariants

1. Every path from the root to the leaf is of length k.
2. If a node has n children, it contains n - 1 keys.
3. The elements stored in a given subtree have keys between the keys on the
   parent node on either side of the subtree pointer.

These are important for our ST to have balanced structure. Red-black BSTs will
have the above, but so should the B-trees, which have to make sure that every
node is at least half full as well.

## Red-Black BST

representing the 2-3 tree as a BST, with "internal" left-leaning links as glue
for 3-nodes. a red-black BST, by definition, is a BST such that: 
    - No node has two red links connected
    - every path from root to null link has equal number of black links
    - red links always lean left.

The ``` get() ``` code is exactly the same as that of BST, doesn't matter if
links are red or not. Same for any searching functions (e.g. ``` floor() ``` and
``` ceiling() ```).

##### Implementation

Add a new field ``` boolean color ``` in ``` Node ```. can let RED be true and
BLACK be false. Then what are the helper functions necessary? They help to
maintain symmetric order and perfect black balance.

![before rotation](rotate-bef.png)
![after rotation](rotate-aft.png)

```java
private Node rotateLeft(Node h) {
    assert isRed(h.right);
    Node x = h.right;
    h.right = x.left;       // between E and S
    x.left = h;             // let E be left node of S
    x.color = h.color;      // taking over h original color
    h.color = RED;          // E becomes a red linked node
    // attach to parent node, is root of newly rearranged subtree.
    return x; 
}
``` 

paradoxically  we also need a ```rotateRight``` helping us to reorientate.
we also need a color flipping ``` flipColors(Node h) ``` in case that there's a
Node with 2 red links, i.e. a temporary 4-Node. change both to black.

```java
private Node rotateRight(Node h) {
    assert isRed(h.left);
    Node x = h.left;
    h.left = x.right;
    x.right = h;
    x.color = h.color;
    h.color = RED;
    return x;
}

private Node flipColors(Node h) {
    assert isRed(h.left) && isRed(h.right);
    h.color = RED;
    h.left.color = BLACK;
    h.right.color = BLACK;
}
```

how do we work with this? notice the following patterns

```java
private Node put(Node h, K key, V val) {
    if (h == null) return new Node(key, val, RED);
    int cmp = key.compareTo(h.key);
    if (cmp < 0)    h.left = put(h.left, key, val);
    if (cmp < 0)    h.right = put(h.right, key, val);
    else            h.val = val; // replacement

    // rebalance
    if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
    if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
    if (isRed(h.right) && isRed(h.right)) h = flipColors(h);
}
```

![insertion into root 3-node](redblack-ins.png)
![insertion into leaf 3-node](redblack-ins2.png)

#### Balance and performance analysis

> **Proposition**: Height of tree is <= 2lgN in worst case.
> (Hint: every path from root to null link has same number of black links, and
> no 2 consecutive links are red. so at most if red and black links alternate
> through every path. but draw any tree and you'll see that the average case has
> 1.00lgN as height)

## B-trees

Often we need to store externally a great amount of data, in file system for
example. Then *locality would be more crucial than memory*.

> **Page.** Contiguous block of data (e.g. a file, of 4096 byte chunk)
> **Probe.** First access to a page (from disk to memory).
> We want to access data using minimum number of probes to access data as fast
> as possible (i.e. tree has to be as shallow as possible!)

A B-tree is a generalised form of the (2,3)-tree, where we have trees of nodes
of size k, where 2 <= k < b, where b, k are integers. We want to make sure that
every node has at least b/2 values.

> **Proposition**: Height of tree k is lg_(m-1)N <= k <= lg_(m/2)N in worst case.
> (because every node has between m/2 and m-1 links.)
