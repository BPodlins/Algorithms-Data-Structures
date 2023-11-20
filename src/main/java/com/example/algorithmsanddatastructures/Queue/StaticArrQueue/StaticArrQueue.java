package com.example.algorithmsanddatastructures.Queue.StaticArrQueue;

public class StaticArrQueue {

    // creating array that we base our queue on
    private Object[] arr = new Object[64];

    // first element pointer
    private int first = 0;

    // last element pointer
    private int last = 0;

    private int size = 0;

    // initializing empty array
    public StaticArrQueue(){}

    // initializing queue with one value
    public StaticArrQueue(Object obj){
        queue(obj);
    }

    // initializing queue with array
    public StaticArrQueue(Object[] arr){
        if(arr.length > this.arr.length){
            throw new RuntimeException();
        }
        this.arr = arr;
    }

    // returning size
    public int size(){
        return this.size;
    }

    // is empty when size is 0 but also element is null
    // this is because during the start of the cycle both can be 0
    public boolean isEmpty(){
        return size() == 0;
    }

    // if size is arr.length then we are at capacity
    public boolean isFull(){
        return size() == arr.length;
    }

    // peeking first element in the queue
    public Object peek(){
        if(isEmpty()){
            throw new RuntimeException();
        }
        return arr[first];
    }

    // taking the element off
    public Object dequeue(){
        if(isEmpty()){
            throw new RuntimeException();
        }
        Object ret = arr[first];
        first++;
        size--;
        return ret;
    }

    // putting the element to the back
    public void queue(Object object){
        if (isFull()){
            last = 0;
            size--;
            if(first == 0){
                dequeue();
            }
        }
        arr[last++] = object;
        size++;
    }
}