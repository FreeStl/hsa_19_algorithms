package org.example;

public class Node<K, V> {
    public static final boolean BLACK = false;
    public static final boolean RED = true;
    K key;
    V value;
    Node<K, V> left, right;
    int size;
    boolean isRed;

    public Node(K key, V value, int size, boolean isRed) {
        this.key = key;
        this.value = value;
        this.size = size;
        this.isRed = isRed;
    }
}
