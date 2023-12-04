package com.example.algorithmsanddatastructures.DataStructures.Queue;

import java.util.Iterator;
import java.util.LinkedList;

public class Queue <T> implements Iterable{

    // list that we build our queue on
    private LinkedList<T> list = new LinkedList<>();

    // initializing empty queue
    public Queue(){}

    // queue with first element
    public Queue(T element){
        queue(element);
    }

    // returning size of the queue based on the list property
    public int size(){
        return list.size();
    }

    // checking for size, if 0 than it is empty
    public boolean isEmpty(){
        return size() == 0;
    }

    // looking for our first element to go out
    public T peek(){
        if (isEmpty()){
            throw new RuntimeException();
        }
        return list.peekFirst();
    }

    public T poll(){
        if (isEmpty()){
            throw new RuntimeException();
        }
        return list.removeFirst();
    }

    // adding element to the queue
    private void queue(T element) {
        list.addLast(element);
    }

    @Override
    public Iterator iterator() {
        return list.iterator();
    }
}