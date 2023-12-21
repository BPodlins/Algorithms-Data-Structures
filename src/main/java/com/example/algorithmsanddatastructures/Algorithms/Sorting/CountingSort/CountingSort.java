package com.example.algorithmsanddatastructures.Algorithms.Sorting.CountingSort;

public class CountingSort {
    
    public int[] countingSort(int[] A, int B[], int k){
        //Initialization of count array
        int[] C = new int[k + 1];

        //Counting occurrences of each element in A
        for (int i = 0; i < A.length; i++) {
            C[A[i]]++;
        }

        //Modifying count array C to store the position of each element in the output
        for (int i = 1; i <= k; i++) {
            C[i] += C[i - 1];
        }

        //Build the output array B
        for (int i = A.length - 1; i >= 0; i--) {
            B[C[A[i]] - 1] = A[i];
            C[A[i]]--;
        }

        return B;
    }
}
