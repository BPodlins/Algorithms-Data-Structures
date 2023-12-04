# ***Union Find***
This is a data structure that merges smaller elements into bigger groups.
We usually do not un-union. It basically stacks elements that are split ino sets.
It has two primary operations: find - it says what group does the element belongs to, and union - merges two 
groups together. Normally the elements are connected by pointing the smaller element to the bigger element group, but we can
amortize it. Every time we "union", we make all the connected nodes point to the root node of the set.

| Operation          | Time complexity | 
|--------------------|-----------------|
| Construction       | O(n)            |               
| Count components   | O(1)            |               
| Union              | Amortized (n)   |               
| Find               | Amortized (n)   |               
| Get component size | Amortized (n)   |               
| Check if connected | Amortized (n)   |               
