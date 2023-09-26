package org.example;

public class RedBlackTree<K extends Comparable<K>, V> {
    private Node<K, V> root;

    public void put(K key, V value) {
        root = put(key, value, root);
    }

    public V get(K key) {
        return get(root, key);
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private V get(Node<K, V> n, K key) {
        while (n != null) {
            int cmp = key.compareTo(n.key);
            if (cmp < 0)
                n = n.left;
            else if (cmp > 0)
                n = n.right;
            else
                return n.value;
        }
        return null;
    }

    private Node<K, V> put(K key, V value, Node<K, V> root) {
        if (root == null) {
            return new Node<>(key, value, 1, false);
        }

        int cmp = key.compareTo(root.key);
        if (cmp < 0)
            root.left = put(key, value, root.left);
        else if (cmp > 0)
            root.right = put(key, value, root.right);
        else root.value = value;

        return balance(root);
    }

    private Node<K,V> rotateLeft(Node<K,V> root) {
        Node<K, V> x = root.right;
        root.right = x.left;
        x.left = root;
        x.isRed = root.isRed;
        root.isRed = true;
        x.size = root.size;
        root.size = size(root.left) + size(root.right) + 1;
        return x;
    }

    private Node<K,V> rotateRight(Node<K,V> root) {
        Node<K, V> x = root.left;
        root.left = x.right;
        x.right = root;
        x.isRed = root.isRed;
        root.isRed = true;
        x.size = root.size;
        root.size = size(root.left) + size(root.right) + 1;
        return x;
    }

    private void flipColors(Node<K,V> root) {
        root.isRed = !root.isRed;
        if (root.left != null)
            root.left.isRed = !root.left.isRed;
        if (root.right != null)
        root.right.isRed = !root.right.isRed;
    }

    private boolean isRed(Node<K, V> node) {
        if (node == null)
            return false;
        return node.isRed;
    }

    private int size(Node<K, V> node) {
        if (node == null)
            return 0;
        else return node.size;
    }

    // delete the key-value pair with the minimum key rooted at h
    private Node<K, V> deleteMin(Node<K,V> root) {
        if (root.left == null)
            return null;

        if (!isRed(root.left) && !isRed(root.left.left))
            root = moveRedLeft(root);

        root.left = deleteMin(root.left);
        return balance(root);
    }

    private Node<K, V> moveRedLeft(Node<K, V> root) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

        flipColors(root);
        if (root.right != null && isRed(root.right.left)) {
            root.right = rotateRight(root.right);
            root = rotateLeft(root);
            flipColors(root);
        }
        return root;
    }

    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private Node<K, V> moveRedRight(Node<K, V> root) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
        flipColors(root);
        if (root.left != null && isRed(root.left.left)) {
            root = rotateRight(root);
            flipColors(root);
        }
        return root;
    }

    private Node<K, V> balance(Node<K, V> root) {
        // assert (h != null);

        if (isRed(root.right) && !isRed(root.left))
            root = rotateLeft(root);
        if (isRed(root.left) && isRed(root.left.left))
            root = rotateRight(root);
        if (isRed(root.left))
            flipColors(root);

        root.size = size(root.left) + size(root.right) + 1;
        return root;
    }

    public void delete(K key) {

        if (root == null)
            return;

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.isRed = true;

        root = delete(root, key);
        if (this.root != null) this.root.isRed = false;
    }

    // delete the key-value pair with the given key rooted at h
    private Node<K, V> delete(Node<K, V> root, K key) {
        // assert get(h, key) != null;

        if (root == null)
            return null;

        if (key.compareTo(root.key) < 0)  {
            //convert 2 node to a 3 node
            if (!isRed(root.left) && root.left != null && !isRed(root.left.left))
                root = moveRedLeft(root);
            root.left = delete(root.left, key);
        }
        else {
            if (isRed(root.left))
                root = rotateRight(root);
            if (key.compareTo(root.key) == 0 && (root.right == null))
                return null;
            if (!isRed(root.right) && root.right != null && !isRed(root.right.left))
                root = moveRedRight(root);
            if (key.compareTo(root.key) == 0) {
                Node<K, V> x = min(root.right);
                root.key = x.key;
                root.value = x.value;
                // h.val = get(h.right, min(h.right).key);
                // h.key = min(h.right).key;
                root.right = deleteMin(root.right);
            }
            else root.right = delete(root.right, key);
        }
        return balance(root);
    }

    private Node<K, V> min(Node<K, V> x) {
        // assert x != null;
        if (x.left == null) return x;
        else                return min(x.left);
    }
}
