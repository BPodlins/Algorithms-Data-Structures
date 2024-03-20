package com.example.algorithmsanddatastructures.DataStructures.FibonacciHeap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.*;

public final class FibonacciHeap<E> implements Queue<E> {

    // mathematical constant used in calculating the size of degree table
    private static final double LOG_PHI = Math.log((1 + Math.sqrt(5)) / 2);

    // index of elements for faster search operations
    private final Set<E> elementsIndex = new HashSet<>();

    // custom comparator for heap elements, null by default for natural ordering
    private final Comparator<? super E> comparator;

    // tracks the current size of the heap
    private int size = 0;

    // counts the number of trees in the heap
    private int trees = 0;

    // tracks the number of marked nodes within the heap
    private int markedNodes = 0;

    // pointer to the minimum node in the heap
    private FibonacciHeapNode<E> minimumNode;

    // constructs an empty Fibonacci heap with natural ordering of elements
    public FibonacciHeap() {
        this(null);
    }

    // constructs an empty Fibonacci heap with a custom comparator for elements
    public FibonacciHeap(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    // internal helper method to move a node to the root list of the heap
    private void moveToRoot(FibonacciHeapNode<E> node) {
        if (isEmpty()) {
            minimumNode = node;
        } else {
            // removes node from its current position and adds it to the root list
            node.getLeft().setRight(node.getRight());
            node.getRight().setLeft(node.getLeft());

            // inserts node into the root list
            node.setLeft(minimumNode);
            node.setRight(minimumNode.getRight());
            minimumNode.setRight(node);
            node.getRight().setLeft(node);

            // updates the minimum node if necessary
            if (compare(node, minimumNode) < 0) {
                minimumNode = node;
            }
        }
    }

    // adds an element to the Fibonacci heap
    @Override
    public boolean add(E e) {
        if (e == null) {
            throw new IllegalArgumentException("Null elements not allowed in this FibonacciHeap implementation.");
        }

        FibonacciHeapNode<E> node = new FibonacciHeapNode<>(e);
        moveToRoot(node);
        size++;
        elementsIndex.add(e);
        return true;
    }

    // adds all elements in the collection to the Fibonacci heap
    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E element : c) {
            add(element);
        }
        return true;
    }

    // clears the Fibonacci heap
    @Override
    public void clear() {
        minimumNode = null;
        size = 0;
        trees = 0;
        markedNodes = 0;
        elementsIndex.clear();
    }

    // checks if an object is contained in the Fibonacci heap
    @Override
    public boolean contains(Object o) {
        return elementsIndex.contains(o);
    }

    // checks if the heap contains all elements in the collection
    @Override
    public boolean containsAll(Collection<?> c) {
        return elementsIndex.containsAll(c);
    }

    // checks if the Fibonacci heap is empty
    @Override
    public boolean isEmpty() {
        return minimumNode == null;
    }

    // unsupported operation: iterator
    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Iterator not supported by FibonacciHeap.");
    }

    // unsupported operation: remove
    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Remove operation not supported by FibonacciHeap.");
    }

    // unsupported operation: removeAll
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("RemoveAll operation not supported by FibonacciHeap.");
    }

    // unsupported operation: retainAll
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("RetainAll operation not supported by FibonacciHeap.");
    }

    // returns the size of the Fibonacci heap
    @Override
    public int size() {
        return size;
    }

    // unsupported operation: toArray
    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("ToArray operation not supported by FibonacciHeap.");
    }

    // unsupported operation: toArray(T[] a)
    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("ToArray(T[] a) operation not supported by FibonacciHeap.");
    }

    // returns the minimum element in the Fibonacci heap without removing it
    @Override
    public E element() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }
        return peek();
    }

    // adds an element to the Fibonacci heap
    @Override
    public boolean offer(E e) {
        return add(e);
    }

    // returns the minimum element in the Fibonacci heap without removing it
    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return minimumNode.getElement();
    }

    // removes and returns the minimum element from the Fibonacci heap
    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }

        FibonacciHeapNode<E> z = minimumNode;
        if (z != null) {
            if (z.getChild() != null) {
                FibonacciHeapNode<E> x = z.getChild();
                do {
                    // detach the child from z and move it to the root list
                    FibonacciHeapNode<E> nextX = x.getRight();
                    moveToRoot(x);
                    x.setParent(null);
                    x = nextX;
                } while (x != z.getChild());
            }

            // remove z from the root list
            removeNodeFromRootList(z);

            // if z was the only node in the root list, clear the minimum node
            if (z == z.getRight()) {
                minimumNode = null;
            } else {
                // otherwise, remove z and perform a consolidation to restructure the heap
                minimumNode = z.getRight();
                consolidate();
            }

            size--;
            elementsIndex.remove(z.getElement());
            return z.getElement();
        }

        return null;
    }

    // removes the minimum element from the Fibonacci heap
    @Override
    public E remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }
        return poll();
    }

    // consolidates the root list of the Fibonacci heap to maintain its properties
    // removes a node from the root list and adjusts pointers accordingly.
    private void removeNodeFromRootList(FibonacciHeapNode<E> z) {
        z.getLeft().setRight(z.getRight());
        z.getRight().setLeft(z.getLeft());
    }

    // consolidates the trees in the root list to maintain the properties of the Fibonacci heap.
    private void consolidate() {
        int arraySize = (int) Math.floor(Math.log(size) / LOG_PHI) + 1;
        List<FibonacciHeapNode<E>> aux = new ArrayList<>(Collections.nCopies(arraySize, null));

        // for each node in the root list
        FibonacciHeapNode<E> x = minimumNode;
        if (x != null) {
            do {
                FibonacciHeapNode<E> next = x.getRight();
                int d = x.getDegree();

                // if there exists another node with the same degree, merge them
                while (aux.get(d) != null) {
                    FibonacciHeapNode<E> y = aux.get(d);
                    if (compare(x, y) > 0) {
                        FibonacciHeapNode<E> temp = x;
                        x = y;
                        y = temp;
                    }
                    link(y, x);
                    aux.set(d, null);
                    d++;
                }
                aux.set(d, x);
                x = next;
            } while (x != minimumNode);
        }

        // reconstruct the root list from the aux array
        minimumNode = null;
        for (FibonacciHeapNode<E> n : aux) {
            if (n != null) {
                if (minimumNode == null) {
                    minimumNode = n;
                    minimumNode.setLeft(minimumNode);
                    minimumNode.setRight(minimumNode);
                } else {
                    moveToRoot(n);
                    if (compare(n, minimumNode) < 0) {
                        minimumNode = n;
                    }
                }
            }
        }
    }


    // links node y below node x
    private void link(FibonacciHeapNode<E> y, FibonacciHeapNode<E> x) {
        removeNodeFromRootList(y);
        y.setParent(x);
        if (x.getChild() == null) {
            x.setChild(y);
            y.setRight(y);
            y.setLeft(y);
        } else {
            y.setLeft(x.getChild());
            y.setRight(x.getChild().getRight());
            x.getChild().setRight(y);
            y.getRight().setLeft(y);
        }
        x.incraeseDegree();
        y.setMarked(false);
    }

    // cuts node x from its parent y and moves x to the root list.
    private void cut(FibonacciHeapNode<E> x, FibonacciHeapNode<E> y) {
        // remove x from the child list of y and decrement y's degree
        if (x.getRight() == x) {
            y.setChild(null);
        } else {
            x.getLeft().setRight(x.getRight());
            x.getRight().setLeft(x.getLeft());
            if (y.getChild() == x) {
                y.setChild(x.getRight());
            }
        }
        y.decraeseDegree();
        moveToRoot(x);
        x.setParent(null);
        x.setMarked(false);
    }

    // performs a cascading cut operation on node y
    private void cascadingCut(FibonacciHeapNode<E> y) {
        FibonacciHeapNode<E> z = y.getParent();
        if (z != null) {
            if (!y.isMarked()) {
                y.setMarked(true);
            } else {
                cut(y, z);
                cascadingCut(z);
            }
        }
    }

    public int potential() {
        return trees + 2 * markedNodes;
    }

    // compares two elements using the heap's comparator or their natural ordering.
    private int compare(FibonacciHeapNode<E> o1, FibonacciHeapNode<E> o2) {
        if (comparator != null) {
            return comparator.compare(o1.getElement(), o2.getElement());
        } else {
            Comparable<? super E> c1 = (Comparable<? super E>) o1.getElement();
            return c1.compareTo(o2.getElement());
        }
    }
}


final class FibonacciHeapNode<E> {

    private final E element;
    private FibonacciHeapNode<E> parent;
    private FibonacciHeapNode<E> left = this;
    private FibonacciHeapNode<E> right = this;
    private FibonacciHeapNode<E> child;
    private int degree;
    private boolean marked;

    public FibonacciHeapNode(E element) {
        // 1  degree[x] &larr; 0
        degree = 0;
        // 2  p[x] <- NIL
        setParent(null);
        // 3  child[x] <- NIL
        setChild(null);
        // 4  left[x] <- x
        setLeft(this);
        // 5  right[x] <- x
        setRight(this);
        // 6  mark[x] <- FALSE
        setMarked(false);

        // set the adapted element
        this.element = element;
    }

    public FibonacciHeapNode<E> getParent() {
        return parent;
    }

    public void setParent(FibonacciHeapNode<E> parent) {
        this.parent = parent;
    }

    public FibonacciHeapNode<E> getLeft() {
        return left;
    }

    public void setLeft(FibonacciHeapNode<E> left) {
        this.left = left;
    }

    public FibonacciHeapNode<E> getRight() {
        return right;
    }

    public void setRight(FibonacciHeapNode<E> right) {
        this.right = right;
    }

    public FibonacciHeapNode<E> getChild() {
        return child;
    }

    public void setChild(FibonacciHeapNode<E> child) {
        this.child = child;
    }

    public int getDegree() {
        return degree;
    }

    public void incraeseDegree() {
        degree++;
    }

    public void decraeseDegree() {
        degree--;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public E getElement() {
        return element;
    }

    @Override
    public String toString() {
        return element.toString();
    }
}