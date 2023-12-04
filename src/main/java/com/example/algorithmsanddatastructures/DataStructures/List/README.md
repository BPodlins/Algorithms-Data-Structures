# ***Linked list & Doubly linked list***

Linked list is a list of nodes (you can imagine them as containers), that point to the next node.
Those nodes have some value inside, it can be an **int** (number), **boolean** or even another **Linked list**.
Doubly linked list is different in that way, that nodes point also to the previous node, which gives us the possibility to traverse backwards.
Because of this property, Doubly linked list is much faster with removing elements that are at the end, but it also takes x2 space due to two "links" instead of one.

| Operation          | Linked list | Doubly linked list |
|--------------------|-------------|--------------------|
| Inserting at head  | O(1)        | O(1)               |
| Inserting at tail  | O(1)        | O(1)               |
| Removing at head   | O(1)        | O(1)               |
| Removing at tail   | O(n)        | O(1)               |
| Removing at middle | O(n)        | O(n)               |
| Searching          | O(n)        | O(n)               |



