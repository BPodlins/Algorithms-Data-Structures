package com.example.algorithmsanddatastructures.Algorithms.Sorting.MergeSort;

public class MergeSort {

    public int[] sortRec(int[] arr, int firstNum, int lastNum){
        if (firstNum < lastNum){
            int middle = (firstNum + lastNum) / 2;
            sortRec(arr, firstNum, middle);
            sortRec(arr, middle + 1, lastNum);

            sortIter(arr, firstNum, middle, lastNum);
        }
        return arr;
    }

    public int[] sortIter(int[] arr, int firstNum, int middle, int lastNum){
        int n1 = middle - firstNum + 1;
        int n2 = lastNum - 1;
        int[] L = new int[n1 + 1];
        int[] R = new int[n2 + 1];

        for (int i = 0; i <= n1; i++) {
            L[i] = arr[firstNum + i - 1];
        }

        for (int i = 0; i <= n2; i++) {
            R[i] = arr[middle + i];
        }

        L[n1 + 1] = Integer.MAX_VALUE;
        R[n2 + 1] = Integer.MAX_VALUE;

        int i = 0;
        int j = 0;

        for(int k = firstNum; i <= lastNum; k++){
            if(L[i] <= R[j]){
                arr[k] = L[i];
                i = i + 1;
            } else {
                arr[k] = R[j];
                j = j + 1;
            }
        }
        return arr;
    }

    public int[] sortIter2(int[] arr, int firstNum, int middle, int lastNum){
        int n1 = middle - firstNum + 1;
        int n2 = lastNum - 1;
        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i <= n1; i++) {
            L[i] = arr[firstNum + i - 1];
        }

        for (int i = 0; i <= n2; i++) {
            R[i] = arr[middle + i];
        }

        int i = 0;
        int j = 0;

        for(int k = firstNum; k <= lastNum; k++){
            if (i < n1 && j < n2) {
                if (L[i] <= R[j]) {
                    arr[k] = L[i];
                    i = i + 1;
                } else {
                    arr[k] = R[j];
                    j = j + 1;
                }
            } else if (i < n1) {
                arr[k] = L[i];
                i = i + 1;
            } else {
                arr[k] = R[j];
                j = j + 1;
            }
        }
        return arr;
    }
}
