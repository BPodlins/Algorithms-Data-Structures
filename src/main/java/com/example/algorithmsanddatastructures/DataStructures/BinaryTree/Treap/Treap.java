package com.example.algorithmsanddatastructures.DataStructures.BinaryTree.Treap;

import java.util.Random;

public class Treap<T extends Comparable<T>>{
    static final int MAX_RAND_NUM = 100;

    private Random random;

    public Node root;

    //number of nodes inside the treap
    private int nodeCount = 0;

    //constructor
    public Treap() {
        random = new Random();
    }

    //returns the size ;)))
    public int size() {
        return this.nodeCount;
    }

    public boolean contains(T value) {
        return contains(root, value);
    }


    private boolean contains(Node node, T value) {
        if (node == null){
            return false;
        }

        int cmp = value.compareTo((T) node.getValue());

        if (cmp < 0){
            return contains(node.left, value);
        } else if (cmp > 0){
            return contains(node.right, value);
        } else{
            return true;
        }
    }

    public boolean isEmpty() {
        return nodeCount == 0;
    }

    public boolean insert(T val, int priority) {
        if (val == null) {
            throw new IllegalArgumentException("Treap does not allow null values");
        }
        if (!contains(root, val)) {
            root = insert(this.root, val, priority);
            nodeCount++;
            return true;
        }
        return false;
    }

    public boolean insert(T val) {
        return insert(val, random.nextInt(MAX_RAND_NUM));
    }

    private Node insert(Node node, T value, int priority) {
        if (node == null) {
            return new Node(value, priority);
        }

        int cmp = value.compareTo((T) node.value);

        if (cmp < 0) {
            node.left = insert(node.left, value, priority);
            if (node.left.priority > node.priority) {
                node = rightRotation(node);
            }
        } else if (cmp > 0) {
            node.right = insert(node.right, value, priority);
            if (node.right.priority > node.priority) {
                node = leftRotation(node);
            }
        }
        return node;
    }

    private Node leftRotation(Node node) {
        Node newParent = node.right;
        node.right = newParent.left;
        newParent.left = node;
        return newParent;
    }

    private Node rightRotation(Node node) {
        Node newParent = node.left;
        node.left = newParent.right;
        newParent.right = node;
        return newParent;
    }

    public boolean remove(T elem) {
        if (elem == null){
            return false;
        }
        if (contains(root, elem)) {
            root = remove(root, elem);
            nodeCount--;
            return true;
        }
        return false;
    }

    private Node remove(Node t, T x) {
        if (t == null) {
            return t;
        }

        int cmp = x.compareTo((T) t.value);
        if (cmp < 0) t.left = remove(t.left, x);
        else if (cmp > 0) t.right = remove(t.right, x);
        else {
            if (t.left == null) return t.right;
            if (t.right == null) return t.left;

            if (t.left.priority > t.right.priority) {
                t = rightRotation(t);
                t.right = remove(t.right, x);
            } else {
                t = leftRotation(t);
                t.left = remove(t.left, x);
            }
        }

        return t;
    }
}
class Node<T>  {

    public T value;

    //the int priority of this node for Treap
    public int priority;

    public Node left, right;

    public Node(T value, int priority) {
        this.value = value;
        this.left = this.right = null;
        this.priority = priority;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public String getText() {
        return value.toString();
    }
}