# Treap

A Treap (Tree + Heap) is a randomized binary search tree data structure that combines the properties of a binary search tree (BST) with the randomness of a heap. 
In a Treap, each node is assigned a priority value, and the tree is structured based on both the keys and priorities. This randomness provides a balance that helps prevent degeneration into an unbalanced structure, 
ensuring efficient search, insertion, and deletion operations.

## Key properties
- Node Priority: Each node is associated with a priority value, typically generated randomly. The priority is used to maintain the heap property during tree operations.
- BST Property: Similar to a regular binary search tree, the keys in the left subtree of a node are smaller than the key of the node, and the keys in the right subtree are larger.
- Heap Property: The priority of each node is greater than or equal to the priorities of its children.


| Operation    | Time Complexity |
|--------------|-----------------|
| Construction | O(n)            | 
| Search       | O(log n)        | 
| Insertion    | O(log n)        |    
| Deletion     | O(log n)        | 