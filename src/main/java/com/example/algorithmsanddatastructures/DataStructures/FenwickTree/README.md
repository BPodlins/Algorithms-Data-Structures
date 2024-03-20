# Fenwick Tree (Binary Indexed Tree)

A Fenwick Tree, also known as a Binary Indexed Tree (BIT), is a data structure that provides efficient methods for performing range sum queries and updates. It is particularly well-suited for calculating cumulative frequency tables and is widely used in competitive programming and various applications requiring fast updates and queries on mutable sequences of numbers.

Fenwick Trees allow querying cumulative sums from the beginning of an array up to a specified element, and updating values of elements in logarithmic time. This makes it an excellent choice for algorithms that need to frequently perform both types of operations, such as calculating the sum of a range in an array after some of its elements have been modified.

| Operation | Time Complexity    |
|-----------|--------------------|
| Build     | O(n)               |
| Query     | O(log n)           |
| Update    | O(log n)           |

The key advantages of using a Fenwick Tree include its relatively simple implementation and its efficiency in handling range update and query operations, making it a powerful tool in the algorithm designer's toolkit.
