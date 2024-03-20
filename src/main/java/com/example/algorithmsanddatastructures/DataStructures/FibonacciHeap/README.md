# Fibonacci Heap

Fibonacci Heaps are a collection of trees satisfying the minimum heap property, designed to optimize operations like insert and decrease-key, which are critical in algorithms such as Dijkstra's shortest path. They allow for faster amortized running times for these operations compared to other priority queue structures, such as binary or binomial heaps, particularly when the number of extract-min and delete operations is small relative to the number of other operations.

| Operation        | Amortized Time Complexity |
|------------------|---------------------------|
| Insert           | O(1)                      |
| Find Minimum     | O(1)                      |
| Delete Minimum   | O(log n)                  |
| Decrease Key     | O(1)                      |
| Merge            | O(1)                      |
