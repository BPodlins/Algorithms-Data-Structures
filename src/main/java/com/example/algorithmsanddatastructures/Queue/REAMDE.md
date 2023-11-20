# ***Queue***

Queue is a data structure that models after real life queues.
Imagine that you want to go to shop but there is a line of people waiting, to get into the store, you as a last customer,
stand on the back of the line, than the customer that came in first is served first, and you came last, so you are served last.
With this analogy we have that, when we add something to the queue we __enqueue__ and when we take something off we __dequeue__.
__Enqueue__ happens from the back and __Dequeue__ happens from the front.
Queue works as __First In First Out__

Queue Build on Linked list

| Operation                   | Time complexity | 
|-----------------------------|-----------------|
| Enqueue                     | O(1)            |               
| Dequeue                     | O(1)            |               
| Peek                        | O(1)            |               
| Searching (containing)      | O(n)            |
| Removing (due to searching) | O(n)            |
