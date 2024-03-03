package com.example.algorithmsanddatastructures.Algorithms.Searching.LinearSearch;

public class LinearSearch {

    public static int search(int[] arr, int num){
        int index = - 1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == num){
                index = i;
                return index;
            }
        }
        return index;
    }
}
