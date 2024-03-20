package com.example.algorithmsanddatastructures.DataStructures.BinaryTree.BTree;

public class BTree<Key extends Comparable<Key>, Value> {

    private static final int M = 4; // max children per B-tree node = M-1 (must be even and greater than 2)

    private Node root; // root of the B-tree
    private int height; // height of the B-tree
    private int n; // number of key-value pairs in the B-tree

    // helper B-tree node data type
    private static final class Node {
        private int m; // number of children
        private Entry[] children = new Entry[M]; // array of children

        private Node(int k) {
            m = k; // create a node with k children
        }
    }

    // internal nodes: only use key and next, external nodes: only use key and value
    private static class Entry {
        private Comparable key;
        private Object val;
        private Node next; // helper field to iterate over array entries

        public Entry(Comparable key, Object val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    public BTree() {
        root = new Node(0); // initializes an empty B-tree
    }

    public boolean isEmpty() {
        return size() == 0; // checks if the symbol table is empty
    }

    public int size() {
        return n; // returns the number of key-value pairs in the symbol table
    }

    public int height() {
        return height; // returns the height of the B-tree, useful for debugging
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        return search(root, key, height); // returns the value associated with the given key
    }

    private Value search(Node x, Key key, int ht) {
        Entry[] children = x.children;

        // search in external node
        if (ht == 0) {
            for (int j = 0; j < x.m; j++) {
                if (eq(key, children[j].key)) return (Value) children[j].val;
            }
        } else { // search in internal node
            for (int j = 0; j < x.m; j++) {
                if (j + 1 == x.m || less(key, children[j + 1].key))
                    return search(children[j].next, key, ht - 1);
            }
        }
        return null; // return null if key not found
    }

    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("argument key to put() is null");
        Node u = insert(root, key, val, height); // insert the key-value pair
        n++;
        if (u == null) return;

        // split root if necessary
        Node t = new Node(2);
        t.children[0] = new Entry(root.children[0].key, null, root);
        t.children[1] = new Entry(u.children[0].key, null, u);
        root = t;
        height++;
    }

    private Node insert(Node h, Key key, Value val, int ht) {
        int j;
        Entry t = new Entry(key, val, null);

        // insert into external node
        if (ht == 0) {
            for (j = 0; j < h.m; j++) {
                if (less(key, h.children[j].key)) break;
            }
        } else { // insert into internal node
            for (j = 0; j < h.m; j++) {
                if ((j + 1 == h.m) || less(key, h.children[j + 1].key)) {
                    Node u = insert(h.children[j++].next, key, val, ht - 1);
                    if (u == null) return null;
                    t.key = u.children[0].key;
                    t.next = u;
                    break;
                }
            }
        }

        // shift children to make room for new entry
        for (int i = h.m; i > j; i--)
            h.children[i] = h.children[i - 1];
        h.children[j] = t;
        h.m++;
        if (h.m < M) return null; // no split needed
        else return split(h); // split node and return new node
    }

    // splits the node in half
    private Node split(Node h) {
        Node t = new Node(M / 2);
        h.m = M / 2;
        for (int j = 0; j < M / 2; j++)
            t.children[j] = h.children[M / 2 + j];
        return t;
    }

    public String toString() {
        return toString(root, height, "") + "\n"; // returns a string representation of the B-tree for debugging
    }

    private String toString(Node h, int ht, String indent) {
        StringBuilder s = new StringBuilder();
        Entry[] children = h.children;

        if (ht == 0) { // print external nodes
            for (int j = 0; j < h.m; j++) {
                s.append(indent).append(children[j].key).append(" ").append(children[j].val).append("\n");
            }
        } else { // print internal nodes
            for (int j = 0; j < h.m; j++) {
                if (j > 0) s.append(indent).append("(").append(children[j].key).append(")\n");
                s.append(toString(children[j].next, ht - 1, indent + "     "));
            }
        }
        return s.toString();
    }

    // comparison functions - avoids casts by making Comparable instead of Key
    private boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }

    private boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }
}