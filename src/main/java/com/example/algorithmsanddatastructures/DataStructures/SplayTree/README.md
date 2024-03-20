# Splay Tree

A Splay Tree is a self-adjusting binary search tree with the additional operation of splaying. Splaying a node means to move it to the root of the tree through a series of tree rotations. This ensures that frequently accessed elements can be accessed quickly over time, making the Splay Tree particularly useful for applications where the access pattern is not uniform and might have temporal locality.

## Features

- **Self-Adjusting Tree**: The tree adjusts itself according to access patterns, moving frequently accessed elements closer to the root.
- **Efficient Search Operations**: Allows for efficient search, insertion, and deletion operations.
- **No Need for Explicit Balance**: Unlike AVL trees or Red-Black trees, Splay Trees do not need to explicitly maintain balance, as the splay operation implicitly takes care of balancing.

## Operations and Their Time Complexity

| Operation | Average Case (Amortized case) | Worst Case  |
|-----------|-------------------------------|-------------|
| Search    | O(log n)                      | O(n)        |
| Insert    | O(log n)                      | O(n)        |
| Delete    | O(log n)                      | O(n)        |
| Splay     | -                             | O(log n)    |