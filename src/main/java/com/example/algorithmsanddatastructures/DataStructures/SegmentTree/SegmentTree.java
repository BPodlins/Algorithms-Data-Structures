package com.example.algorithmsanddatastructures.DataStructures.SegmentTree;

public class SegmentTree {
    // for now several arrays to support each operation, in future it should be probably constructed with nodes
    // this is superbly inefficient
    private final int[] treeMax;
    private final int[] treeMin;
    private final long[] treeSum;
    private final int n; // length of the input array

    // constructor initializes the segment tree to support max, min, and sum operations.
    public SegmentTree(int[] ar) {
        n = ar.length;
        treeMax = new int[n * 2];
        treeMin = new int[n * 2];
        treeSum = new long[n * 2]; // initialize tree for sum

        // initialize trees with input array values at the leaves
        System.arraycopy(ar, 0, treeMax, n, n);
        System.arraycopy(ar, 0, treeMin, n, n);
        for (int i = 0; i < n; i++) {
            treeSum[i + n] = ar[i]; // set initial sum values at leaves
        }

        // build the trees by calculating max, min, and sum from the leaves to the root
        for (int i = n - 1; i > 0; i--) {
            treeMax[i] = Math.max(treeMax[2 * i], treeMax[2 * i + 1]);
            treeMin[i] = Math.min(treeMin[2 * i], treeMin[2 * i + 1]);
            treeSum[i] = treeSum[2 * i] + treeSum[2 * i + 1]; // Calculate sum
        }
    }

    // updates the value at index i and recalculates max, min, and sum up the tree.
    public void update(int i, int value) {
        i += n; // Shift index to the leaf
        treeMax[i] = value;
        treeMin[i] = value;
        treeSum[i] = value; // update sum at the leaf

        while (i > 1) {
            i >>= 1; // move up the tree
            treeMax[i] = Math.max(treeMax[2 * i], treeMax[2 * i + 1]);
            treeMin[i] = Math.min(treeMin[2 * i], treeMin[2 * i + 1]);
            treeSum[i] = treeSum[2 * i] + treeSum[2 * i + 1]; // recalculate sum
        }
    }


    public int max(int from, int to) {
        from += n; // adjust index to start from leaves
        to += n;
        int max = Integer.MIN_VALUE;

        while (from < to) {
            if ((from & 1) == 1) { // check if 'from' is the right child
                max = Math.max(max, treeMax[from++]);
            }
            if ((to & 1) == 1) { // check if 'to' is the right child
                max = Math.max(max, treeMax[--to]);
            }
            from >>= 1; // move up the tree
            to >>= 1;
        }

        return max;
    }

    // returns the minimum value within range [from, to).
    public int min(int from, int to) {
        from += n; // adjust index to start from leaves
        to += n;
        int min = Integer.MAX_VALUE;

        while (from < to) {
            if ((from & 1) == 1) { // check if 'from' is the right child
                min = Math.min(min, treeMin[from++]);
            }
            if ((to & 1) == 1) { // check if 'to' is the right child
                min = Math.min(min, treeMin[--to]);
            }
            from >>= 1; // move up the tree
            to >>= 1;
        }

        return min;
    }

    // returns the sum of values within range [from, to).
    public long sum(int from, int to) {
        from += n;
        to += n;
        long sum = 0; // initialize sum for the range

        while (from < to) {
            if ((from & 1) == 1) {
                sum += treeSum[from++];
            }
            if ((to & 1) == 1) {
                sum += treeSum[--to];
            }
            from >>= 1;
            to >>= 1;
        }
        return sum;
    }
}
