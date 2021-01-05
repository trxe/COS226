import edu.princeton.cs.algs4.Queue;
import java.util.Iterator;

public class BST<K extends Comparable<K>,V> {

    private Node root;

    class Node {
        K key;
        V val;
        Node left;
        Node right;
        int size = 0;

        Node(K key, V val, int size) { 
            this.key = key;
            this.val = val;
            this.size = size;
        }

    }

    public void put(K key, V val) {
        root = put(root, key, val);
    }

    private Node put(Node x, K key, V val) {
        if (x == null) {
            return new Node(key, val, 1);
        }
        int diff = x.key.compareTo(key);
        if (diff == 0) {
            x.val = val;
        } else if (diff > 0) {
            x.left = put(x.left, key, val);
        } else {
            x.right = put(x.right, key, val);
        }
        x.size = (1 + size(x.left) + size(x.right));
        return x;
    }

    private int size(Node x) {
        if (x == null)
            return 0;
        return x.size;
    }
    
    public int size() {
        return size(root);
    }

    public V get(K key) {
        Node x = root;
        while (x != null) {
            int diff = x.key.compareTo(key);
            if (diff == 0) {
                return x.val;
            } else if (diff > 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return null;
    }

    public K floor(K key) {
        Node x = root;
        K out = x.key;
        while (x != null) {
            int diff = x.key.compareTo(key);
            if (diff == 0)
                return x.key;
            else if (diff > 0) {
                x = x.left;
            } else {
                out = x.key;
                x = x.right;
            }
        }
        return out;
    }

    public K ceiling(K key) {
        Node x = root;
        K out = x.key;
        while (x != null) {
            int diff = x.key.compareTo(key);
            if (diff == 0)
                return x.key;
            else if (diff > 0) {
                out = x.key;
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return out;
    }

    public K max() {
        return max(root).key;
    }

    public K min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left != null) 
            return min(x.left);
        return x;
    }
    
    private Node max(Node x) {
        if (x.right != null) 
            return max(x.right);
        return x;
    }

    public int rank(K key) { return rank(root, key); }

    private int rank(Node x, K key) {
        if (x != null) {
            int diff = x.key.compareTo(key);
            if (diff == 0)
                return size(x.left);
            else if (diff > 0) {
                return rank(x.left, key);
            } else {
                return 1 + size(x.left) + rank(x.right, key);
            }
        }
        return 0;
    }

    public K select(int rank) {
        Node x = root;
        while (x != null) {
            int diff = rank - size(x.left);
            if (diff == 0) {
                return x.key;
            } else if (diff < 0) {
                x = x.left;
            } else {
                rank -= size(x.left);
                x = x.right;
            }
        }
        return null;
    }

    public Iterable<K> keys() {
        Queue<K> q = new Queue<>();
        inorder(root, q);
        return q;
    }

    private void inorder(Node x, Queue<K> q) {
        if (x != null) {
            inorder(x.left, q);
            q.enqueue(x.key);
            inorder(x.right, q);
        }
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    // hibbard deletion - allowing this results in sqrt(n) time for all operations.
    // important open problem: to look for an algorithm that gives a balanced
    // tree with deletions.
    public void delete(K key) {
        root = delete(root, key);
    }

    private Node delete(Node x, K key) {
        if (x == null) return null;
        int diff = x.key.compareTo(key);
        if (diff > 0) x.left = delete(x.left, key);
        else if (diff < 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;

            Node t = x; // keeps a pointer to the existing node.
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left; // brings back the left branch of the existing node.
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public boolean contains(K key) { return get(key) != null; }
    public boolean isEmpty() { return root == null; }

    public static void main(String[] args) {
        BST<Integer, String> treenums = new BST<>();
        treenums.put(7, "g");
        treenums.put(3, "c");
        treenums.put(1, "a");
        treenums.put(5, "e");
        treenums.put(11, "x");
        treenums.put(9, "r");
        treenums.put(13, "z");
        System.out.print(treenums.get(13));
        System.out.print(treenums.floor(7));
        System.out.print(treenums.floor(2));
        System.out.print(treenums.ceiling(2));
        System.out.print(treenums.size());
        System.out.print(treenums.rank(7));
        System.out.print("\nselect - 1 : " + treenums.select(4));
        System.out.println();

        treenums.put(8, "h");
        Iterator<Integer> it = treenums.keys().iterator();
        while (it.hasNext()) {
            int k = it.next();
            System.out.println(k + " - " + treenums.get(k));
        }

        System.out.println(treenums.size());
        System.out.println(treenums.min());
        System.out.println(treenums.max());
        treenums.delete(8);

        it = treenums.keys().iterator();
        while (it.hasNext()) {
            int k = it.next();
            System.out.println(k + " - " + treenums.get(k));
        }
    }
}
