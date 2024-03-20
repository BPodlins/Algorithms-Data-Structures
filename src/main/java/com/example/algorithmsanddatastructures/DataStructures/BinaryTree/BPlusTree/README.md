# B+ Tree

A B+ Tree is an extension of the B-Tree optimized for systems that read and write large blocks of data, like databases and filesystems. Unlike B-Trees, in B+ Trees, all data is stored in the leaf nodes, with internal nodes only storing keys. This design allows for efficient range queries and sequential access, as leaf nodes are linked, facilitating quick traversal. B+ Trees are ideal for storage systems due to their efficient disk space utilization and ability to minimize disk I/O operations.

| Operation       | Average Time Complexity | Worst Time Complexity |
|-----------------|-------------------------|-----------------------|
| Search          | O(log n)                | O(log n)              |
| Insert          | O(log n)                | O(log n)              |
| Delete          | O(log n)                | O(log n)              |
| Traverse        | O(n)                    | O(n)                  |
| Find Minimum    | O(log n)                | O(log n)              |
| Find Maximum    | O(log n)                | O(log n)              |

## B Tree vs B+ Tree

| B Tree                                                                                                              | B+ Tree                                                                                              | 
|---------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------|
| Search keys can not be repeatedly stored.                                                                           | Redundant search keys can be present.                                                                |
| Data can be stored in leaf nodes as well as internal nodes                                                          | Data can only be stored on the leaf nodes.                                                           | 
| Searching for some data is a slower process since data can be found on internal nodes as well as on the leaf nodes. | Searching is comparatively faster as data can only be found on the leaf nodes.                       | 
| Deletion of internal nodes are so complicated and time consuming.                                                   | Deletion will never be a complexed process since element will always be deleted from the leaf nodes. | 
| Leaf nodes can not be linked together.                                                                              | Leaf nodes are linked together to make the search operations more efficient.                         | 
