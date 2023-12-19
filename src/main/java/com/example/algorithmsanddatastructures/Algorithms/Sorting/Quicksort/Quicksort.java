package com.example.algorithmsanddatastructures.Algorithms.Sorting.Quicksort;

public class Quicksort {

    public int[] quicksort(int[] A, int p, int r){
        if (p < r){
            int q = partition(A, p, r);
            quicksort(A, p, q - 1);
            quicksort(A, q + 1, r);
        }
        return A;
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
