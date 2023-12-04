# ***Array based Queue***

This implementation differs from the Linked list one, mainly because 
it is built on top of an array. It is faster but when we go over the limit,
than the old data becomes overwritten.

Queue Build on Array list

| Operation                   | Time complexity | 
|-----------------------------|-----------------|
| Enqueue                     | O(1)            |               
| Dequeue                     | O(1)            |               
| Peek                        | O(1)            |               
| Searching (containing)      | O(n)            |
| Removing (due to searching) | O(n)            |
