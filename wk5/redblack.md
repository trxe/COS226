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

see the following illustrations for why.

#### Balance and performance analysis

> Proposition: Height of tree is <= 2lgN in worst case.
    > (Hint: every path from root to null link has same number of black links.
    > so 
