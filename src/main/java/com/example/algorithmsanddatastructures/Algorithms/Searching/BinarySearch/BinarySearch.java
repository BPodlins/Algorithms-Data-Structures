package com.example.algorithmsanddatastructures.Algorithms.Searching.BinarySearch;

public class BinarySearch {
    public static int search(int[] arr, int num){
        int left = 0;
        int right = arr.length;
        int middle = (left + right) / 2;
        int index = -1;

        while (left < right){
            if (arr[middle] == num) {
                index = middle;
                return index;
            } else if (arr[middle] > num) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return index;
    }
}
