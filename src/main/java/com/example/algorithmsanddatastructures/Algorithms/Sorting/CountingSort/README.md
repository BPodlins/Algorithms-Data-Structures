# ***Counting Sort***
Counting Sort is a linear-time sorting algorithm designed for integers within a known and limited range. Instead of comparing elements, 
it counts the occurrences of each element, creating a count array. The algorithm then adjusts the counts to find the cumulative count, 
providing the correct positions for each element in the sorted output. Counting Sort is stable and efficient for small, 
discrete ranges but may not be suitable for larger or floating-point datasets due to its space requirements.