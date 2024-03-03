package com.example.algorithmsanddatastructures.Algorithms.Searching.InterpolationSearch;

public class InterpolationSearch {

    public static int search(int[] arr, int num){
        int highEnd = (arr.length - 1);
        int lowEnd = 0;
        int index = -1;

        while (num >= arr[lowEnd] && num <= arr[highEnd] && lowEnd <= highEnd) {

            int probe = lowEnd + (highEnd - lowEnd) * (num - arr[lowEnd]) / (arr[highEnd] - arr[lowEnd]);

            if (highEnd == lowEnd) {
                if (arr[lowEnd] == num) {
                    index = lowEnd;
                } else {
                    index = -1;
                }
                return index;
            }

            if (arr[probe] == num) {
                index = probe;
                return index;
            }

            if (arr[probe] < num) {
                lowEnd = probe + 1;
            } else {
                highEnd = probe - 1;
            }
        }
        return index;
    }
}