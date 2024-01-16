# AVL Tree

An AVL (Adelson-Velsky and Landis) Tree is a self-balancing binary search tree that maintains strict balance during insertion and deletion operations. 
The balance is achieved by ensuring that the heights of the left and right subtrees of any node differ by at most one. 
This balance property guarantees efficient search, insertion, and deletion operations by preventing the tree from becoming skewed.

## Key properties
- Balance Factor: For each node in the tree, the height of the left subtree and the height of the right subtree differ by at most one.
- Binary Search Tree Property: In addition to the balance property, AVL Trees maintain the ordering property of binary search trees, where all elements in the left subtree are less than the node, and all elements in the right subtree are greater.


| Operation    | Time Complexity |
|--------------|-----------------|
| Construction | O(n)            | 
| Search       | O(log n)        | 
| Insertion    | O(log n)        |    
| Deletion     | O(log n)        | 

