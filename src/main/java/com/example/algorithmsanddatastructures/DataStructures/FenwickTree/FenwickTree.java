package com.example.algorithmsanddatastructures.DataStructures.FenwickTree;

// implementation of the fenwick tree (binary indexed tree) for efficient query and update of prefix sums
public class FenwickTree {
    // the size of the array holding the fenwick tree values
    final int N;

    // this array contains the fenwick tree ranges
    private long[] tree;

    // create an empty fenwick tree with 'sz' zero-based
    public FenwickTree(int sz) {
        tree = new long[(N = sz + 1)];
    }

    // construct a fenwick tree with an initial set of values
    // the 'values' array must be one based meaning values[0] does not get used, O(n) construction
    public FenwickTree(long[] values) {
        if (values == null) {
            throw new IllegalArgumentException("values array cannot be null!");
        }

        N = values.length;
        values[0] = 0L; // ensure the first element is zero for one-based indexing

        // make a clone of the values array to avoid modifying the original array
        tree = values.clone();

        // construct the tree by updating all parent nodes
        for (int i = 1; i < N; i++) {
            int parent = i + lsb(i);
            if (parent < N) {
                tree[parent] += tree[i];
            }
        }
    }

    // returns the value of the least significant bit (LSB)
    private static int lsb(int i) {
        // isolates the lowest one bit value
        return i & -i;
    }

    // computes the prefix sum from [1, i], O(log(n))
    private long prefixSum(int i) {
        long sum = 0L;
        while (i != 0) {
            sum += tree[i];
            i &= ~lsb(i); // equivalently, i -= lsb(i)
        }
        return sum;
    }

    // returns the sum of the interval [left, right], O(log(n))
    public long sum(int left, int right) {
        if (right < left) {
            throw new IllegalArgumentException("make sure right >= left");
        }
        return prefixSum(right) - prefixSum(left - 1);
    }

    // get the value at index i
    public long get(int i) {
        return sum(i, i);
    }

    // add 'v' to index 'i', O(log(n))
    public void add(int i, long v) {
        while (i < N) {
            tree[i] += v;
            i += lsb(i);
        }
    }

    // set index i to be equal to v, O(log(n))
    public void set(int i, long v) {
        add(i, v - get(i));
    }

    @Override
    public String toString() {
        return java.util.Arrays.toString(tree);
    }
}
