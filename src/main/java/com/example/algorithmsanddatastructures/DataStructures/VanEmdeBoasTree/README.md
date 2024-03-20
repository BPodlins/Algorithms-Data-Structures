# Van Emde Boas Tree

The Van Emde Boas tree is a specialized tree structure designed to efficiently handle operations on a set of integers. It's particularly effective in scenarios where the universe of possible keys (U) is known and finite. The vEB tree supports operations like search, insert, and delete in O(log log U) time, offering significant performance improvements over traditional binary search trees or hash tables for certain applications.

| Operation   | Time Complexity    |
|-------------|--------------------|
| Search      | O(log log U)       |
| Insert      | O(log log U)       |
| Delete      | O(log log U)       |
| Predecessor | O(log log U)       |
| Successor   | O(log log U)       |
