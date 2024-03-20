
# ***B-Tree***
A B-Tree is a self-balancing tree data structure that keeps data sorted and allows searches, sequential access, insertions, and deletions in logarithmic time. It is optimized for systems that read and write large blocks of data. B-Trees are well-suited for storage systems like databases and file systems.

Its main characteristic is that it can have more than two children per node, which is determined by the order of the tree (M). The operations are designed to maintain the tree's balanced nature, ensuring that all leaf nodes are at the same level.

| Operation          | Average Time Complexity | Worst Time Complexity |
|--------------------|-------------------------|-----------------------|
| Search             | O(log n)                | O(log n)              |
| Insert             | O(log n)                | O(log n)              |
| Delete             | O(log n)                | O(log n)              |
| Traverse           | O(n)                    | O(n)                  |
| Find Minimum       | O(log n)                | O(log n)              |
| Find Maximum       | O(log n)                | O(log n)              |

- **Search**: Finds a value within the B-Tree based on its key.
- **Insert**: Adds a new key-value pair to the B-Tree, maintaining the tree's sorted order and balance.
- **Delete**: Removes a key-value pair from the B-Tree, adjusting the structure to preserve balance.
- **Traverse**: Goes through each element in the B-Tree in sorted order.
- **Find Minimum**: Retrieves the minimum key value in the B-Tree.
- **Find Maximum**: Retrieves the maximum key value in the B-Tree.

Note: `n` represents the number of key-value pairs in the B-Tree. The time complexity for search, insert, and delete operations reflects the balanced nature of B-Trees, ensuring that operations remain efficient even as the tree grows.
