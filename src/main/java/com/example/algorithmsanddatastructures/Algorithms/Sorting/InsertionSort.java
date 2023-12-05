package com.example.algorithmsanddatastructures.Algorithms.Sorting;

public class InsertionSort {
    public int[] sortAsc(int[] arr){
        for(int i = 1; i < arr.length; i++){ // i represents the "card" that you want to insert
            int key = arr[i]; // key represents the number that you are sorting

            int j = i - 1; // j represents the number of positions that are already sorted

            // this loop works while j is bigger than 0, so there are still cards to go through, and number we are currently on
            // is bigger than the number we want to insert, this is because of the already sorted cards property
            while (j>0 && arr[j] > key){
                arr[j + 1] = arr[j]; // we shift the numbers to the right
                j = j - 1;
            }
            arr[j+1] = key; // when we stop shifting, so when we encounter number smaller than the key, we insert the key
        }                   // in the next position
        return arr;
    }

    public int[] sortDesc(int[] arr){
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];

            int j = i - 1;

            while (j > 0 && arr[j] < key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
        return arr;
    }
}
