package com.example.algorithmsanddatastructures.DynamicArray;

import java.util.Iterator;

public class DynamicArrImpl<T> implements Iterable<T> {

    private T[] arr;
    private int len = 0;
    private int size = 0;

    public DynamicArrImpl() {
        this(8);
    }

    public DynamicArrImpl(int size) {
        if (size < 0){
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        this.size = size;
        this.arr = (T[]) new Object[size];
    }

    public int size() {
        return this.len;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public T get(int index){
        if (index < 0 || index >= arr.length){
            throw new IndexOutOfBoundsException("Index provided is not in scope");
        }
        return arr[index];
    }

    public void set(int index, T elem){
        if (index < 0 || index >= arr.length){
            throw new IndexOutOfBoundsException("Index provided is not in scope");
        }
        arr[index] = elem;
    }

    public void clear() {
        for(int i = 0; i < size; i++){
            arr[i] = null;
        }
        len = 0;
    }

    public void add(T elem){
        if (len+1 >= size) { // if length +1 is greater or equal to size, we need to resize
            if (size == 0) {
                size = 1;
            } else {
                size *= 2;
            }               // both ifs are resizing
            T[] temp = (T[]) new Object[size]; // when we resize, we need to create a new static array, with the new size
            for (int i = 0; i < len; i++){
                temp[i] = arr[i];
            } // copying old values to the new array
            arr = temp; // sets old array as new array
        }
        arr[len++] = elem; // adding new element at the end of new array (at the end of reachable length, not actual size reserved)
    }

    // removing at index
    public T remove(int index){
        if (index < 0 || index >= arr.length){
            throw new IndexOutOfBoundsException("Index provided is not in scope");
        }
        T data = arr[index];
        T[] temp = (T[]) new Object[len-1];
        for (int i = 0, j = 0; i < len; i++, j++) { // the whole loop works like that, i is index of old array and j is index of new array
            if (i == index){                        // so normally the loop writes old array to new one, with an exception that is an Object
                j--;                                // that is indexed at the place we want to remove. In that case we do not write newArr[j] == Arr[i]
            }else {                                 // we just decrement j, so when i goes further, j is still at the index we want to remove, so it is
                temp[j] = arr[i];                   // written with next Object that stands next in the old array.
            }
        }
        arr = temp;
        size = --len;
        return data;
    }

    // removing object (if array has multiple instances it will delete the first one occurring)
    public boolean remove(Object obj){
        for (int i = 0; i < len; i++) {
            if(arr[i].equals(obj)){
                remove(i);
                return true;
            }
        }
        return false;
    }

    // similar to removing object, but instead it returns it (or -1 if not found)
    public int indexOf(Object obj){
        for (int i = 0; i < len; i++) {
            if(arr[i].equals(obj)){
                return i;
            }
        }
        return -1;
    }

    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < len;
            }

            @Override
            public T next() {
                return arr[index++];
            }
        };
    }

    @Override
    public String toString() {
        if(len == 0) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder(len).append("[");
            for (int i = 0; i < len-1; i++) {
                sb.append(arr[i] +", ");
            }
            return sb.append(arr[len - 1] +"]").toString();
        }
    }
}
