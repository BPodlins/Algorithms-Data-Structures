package com.example.algorithmsanddatastructures.Algorithms.Sorting;

public class InsertionSort {
    public int[] sortAsc(int[] arr){
        for(int i = 2; i < arr.length; i++){ // i represents the "card" that you want to insert
            int key = arr[i];
            int j = i - 1; // j represents the cards that are already after sorting

            while (i>0 && arr[i] > key){
                arr[i + 1] = arr[i]; // i + 1 are cards that still need to be sorted
                i = i - 1;
            }
            arr[i+1] = key;
        }
        return arr;
    }

    public int[] sortDesc(int[] arr){

        return arr;
    }
}
