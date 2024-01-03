package com.example.algorithmsanddatastructures.Algorithms.Sorting.BucketSort;

public class BucketSort {
    int getMax(int[] a) {
            int n = a.length;
            int max = a[0];
            for (int i = 1; i < n; i++)
                if (a[i] > max)
                    max = a[i];
            return max;
    }
    void bucket(int[] a)
    {
        int n = a.length;
        int max = getMax(a);
        int[] bucket = new int[max+1];
        for (int i = 0; i <= max; i++)
        {
            bucket[i] = 0;
        }
        for (int k : a) {
            bucket[k]++;

        }
        for (int i = 0, j = 0; i <= max; i++)
        {
            while (bucket[i] > 0)
            {
                a[j++] = i;
                bucket[i]--;
            }
        }
    }
}
