# Red-Black Tree

A Red-Black Tree is a self-balancing binary search tree data structure that maintains balance during insertion and deletion operations. 
It is named after the coloring scheme applied to each node, which can be either red or black. The properties of a Red-Black Tree ensure that it remains approximately balanced,
preventing degeneration into a skewed structure and providing efficient search, insertion, and deletion operations.

## Key properties
-    Node Color: Each node in the tree is colored either red or black.
-    Root and Leaves: The root and leaves (null or sentinel nodes) are always black.
-    Red Nodes: Red nodes cannot have red children. In other words, there are no consecutive red nodes along any path.
-    Depth Property: For each node, any simple path from this node to any of its descendant leaves contains the same number of black nodes. This property ensures that the tree remains balanced.


| Operation    | Time Complexity |
|--------------|-----------------|
| Construction | O(n)            | 
| Search       | O(log n)        | 
| Insertion    | O(log n)        |    
| Deletion     | O(log n)        | 
