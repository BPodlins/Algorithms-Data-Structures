package com.example.algorithmsanddatastructures.Stack;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.LinkedList;

public class Stack <T> implements Iterable<T>{

    LinkedList<T> list = new LinkedList<>();

    // empty constructor for empty stack
    public Stack(){};

    // constructor with first element
    public Stack(T firstElem){
        push(firstElem);
    }

    // returning size of the internal linked list
    public int size(){
        return list.size();
    }

    // checking if the stack is empty
    public boolean isEmpty(){
        return size() == 0;
    }

    // to push the element we add an element to the back od our internal list (remember last in first out)
    public void push(T elem){
        list.addLast(elem);
    }

    public T pop(){
        if(isEmpty()){
            throw new EmptyStackException();
        }
        return list.removeLast();
    }

    public T peek(){
        if (isEmpty()){
            throw new EmptyStackException();
        }
        return list.peekLast();
    }


    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }
}
