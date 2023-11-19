package com.example.algorithmsanddatastructures.List;

import java.util.Iterator;

public class DoublyLinkedList <T> implements Iterable<T> {
    // node used in this Doubly Linked List
    private class Node <T> {
        T data;
        Node<T> prev, next;
        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
        @Override
        public String toString(){
            return data.toString();
        }
    }
    private int size = 0;
    private Node <T> head = null;
    private Node<T> tail = null;

    public void clear() {
        Node<T> trav = head; // setting starting node
        while (trav != null) { // clearing whole list
            Node<T> next = trav.next; // setting next node
            trav.prev = null;
            trav.next = null;
            trav.data = null; // clearing node that I'm currently at
            trav = next; // setting current node as next, to advance
        }
        head = tail = null;
        size = 0; // resetting class values
    }

    // returning size of this List
    public int size(){
        return this.size;
    }

    // checking if list is empty
    public boolean isEmpty(){
        return size() == 0;
    }

    // adding element to the Doubly Linked List, O(1);
    public void add(T elem){
        addLast(elem);
    }

    public void addFirst(T elem){
        if(isEmpty()){ // if list is empty, we create an instance
            head = tail = new Node<T>( elem, null, null);
        } else {
            head.prev = new Node<>(elem, null, head);
            head = head.prev;
        }
        size++;
    }

    // basically the same as adding first
    public void addLast(T elem){
        if(isEmpty()){ // if list is empty, we create an instance
            head = tail = new Node<T>( elem, null, null);
        } else {
            tail.next = new Node<T>(elem, tail, null);
            tail = tail.next;
        }
        size++;
    }

    // both O(1)
    public T peekFirst(){
        if(isEmpty()){
            throw new RuntimeException("List is empty");
        }
        return head.data;
    }

    public T peekLast(){
        if(isEmpty()){
            throw new RuntimeException("List is empty");
        }
        return tail.data;
    }

    // removing first element
    public T removeFirst(){
        if (isEmpty()){ // checking if the list is empty
            throw new RuntimeException("List is empty");
        }

        T data = head.data; // extracting the data so we can return it
        head = head.next;   //setting new head
        --size;

        if(isEmpty()){
            tail = null; // if the list is empty, the tail should be null as well
        } else {
            head.prev = null; // cleaning previous node REMEMBER SUPER IMPORTANT FOR C++
        }
        return data; // returning data
    }

    // similarly as above
    public T removeLast(){
        if (isEmpty()){
            throw new RuntimeException("List is empty");
        }

        T data = tail.data;
        tail = tail.prev;
        --size;

        if(isEmpty()){
            head = null;
        } else {
            tail.next = null;
        }
        return data;
    }

    // this is privet due to node being private
    private T remove(Node<T> node) {

        // if node is first or last, we use functions
        // that we have had written above
        if (node.prev == null){
            return removeFirst();
        }
        if (node.next == null){
            return removeLast();
        }

        // make the pointers of neighbouring nodes, skip the node
        // which we are removing
        node.next.prev = node.prev;
        node.prev.next = node.next;

        // storing data of node that we are deleting
        // ,so we can return it
        T data = node.data;

        // classic at this point, cleaning up memory
        node.data = null;
        node = node.prev = node.next = null;

        --size;

        return data;
    }

    // removing node at index
    public T removeAt(int index){
        if (index < 0 || index >= size){ // checking if index is valid
            throw new IllegalArgumentException();
        }

        int i;
        Node<T> trav;

        // searching from the begging or from the back
        // we have pointers to previous nodes so why
        // shouldn't we take advantage of it, still tho O(n)
        if (index < size/2){
            for (i = 0, trav = head; i < 1+ index; i++) {
                trav = trav.next;
            }
        } else {
            for (i = size-1, trav = tail; i != index ; i--) {
                trav = trav.prev;
            }
        }
        return remove(trav); // we are using the function that we have had already created
    }

    //removing value (if multiple instances, we remove the first that we encounter)
    public boolean remove(Object obj) {

        Node<T> trav = head;

        // support for null values
        if (obj == null){
            for (trav = head; trav != null ; trav = trav.next){ // traversing the list
                if(trav.data == null){ // until we find null element (the first one)
                    remove(trav);
                    return true;
                }
            }
        } else { // basically searching for not null
            for (trav = head; trav != null; trav = trav.next){
                if(obj.equals(trav.data)){ // same as above but we check passed object value with node value
                    remove(trav);
                    return true;
                }
            }
        }
        return false;
    }

    // really similar to the code above, same thought behind
    public int indexOf(Object obj){
        int index = 0;
        Node<T> temp = head;

        // also supports searching for null
        if (obj == null){
            for (temp = head; temp != null ; temp = temp.next, index++) {
                if(temp.data == null){
                    return index;
                }
            }
        }else {
            for (temp = head; temp != null; temp = temp.next, index++){
                if(obj.equals(temp.data)){
                    return index;
                }
            }
        }
        return -1;
    }

    // checks if value is inside the list
    public boolean contains(Object obj){
        return indexOf(obj) != -1;
    }



    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> temp = head; // start pointer
            @Override
            public boolean hasNext() {
                return temp != null; // traverse untill null
            }

            @Override
            public T next() {
                T data = temp.data;
                temp = temp.next;
                return data;
            }
        };
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        Node<T> temp = head;
        while(temp != null){
            sb.append(temp.data +", ");
            temp = temp.next;
        }
        sb.append(" ]");
        return sb.toString();
    }
}
