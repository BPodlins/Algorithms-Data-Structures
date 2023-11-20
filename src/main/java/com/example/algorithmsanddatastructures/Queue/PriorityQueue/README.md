# ***Priority Queue***

Priority queue is a queue with a special property, that an element with a higher priority comes out first.
It supports only objects that are comparable. (we need to check the priority and determine which element has higher value).
We can set what determines the priority ex.: higher numbers have bigger priority.

Priority queue build on binary heap

| Operation                 | Time complexity                                 | 
|---------------------------|-------------------------------------------------|
| Constructing Binary Heap  | O(n)(although there is a linear time algorithm) |               
| Polling                   | O(log(n))                                       |               
| Peeking                   | O(1)                                            |               
| Adding                    | O(log(n))                                       |
| "Brute force" Removing    | O(n)                                            |
| "Brute force" Contains    | O(n)                                            |
| Hash table based Removing | O(log(n))                                       |
| Hash table based Contains | O(1)                                            |
