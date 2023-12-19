package com.example.algorithmsanddatastructures.Algorithms.Sorting.Quicksort;

public class RandomizedQuicksort {
    public int[] randomizedQuicksort(int[] A, int p, int r){
        if (p < r){
            int q = randomizedPartition(A, p, r);
            randomizedQuicksort(A, p, q - 1);
            randomizedQuicksort(A, q + 1, r);
        }
        return A;
    }

    public int randomizedPartition(int[] A, int p, int r){
        int i = (int) ((Math.random() * (r - p + 1)) + p);
        int tmp = A[i];
        A[i] = A[r];
        A[r] = tmp;

        return partition(A, p, r);
    }

    public int partition(int[] A, int p, int r){
        int x = A[r];
        int i = p - 1;
        for (int j = p; j <= r - 1; j++){
            if (A[j] <= x){
                i++;
                int temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            }
        }
        int tmp = A[i + 1];
        A[i + 1] = A[r];
        A[r] = tmp;
        return i + 1;
    }
}
