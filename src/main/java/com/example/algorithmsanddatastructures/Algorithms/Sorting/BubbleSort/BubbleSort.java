package com.example.algorithmsanddatastructures.Algorithms.Sorting.BubbleSort;

public class BubbleSort {

    public int[] sort(int[] arr){
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = arr.length - 1; j > i ; j--) {
                if(arr[j] > arr[j - 1]){
                    int tmp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = tmp;
                }
            }
        }
        return arr;
    }
}
