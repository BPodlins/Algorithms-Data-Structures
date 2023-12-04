package com.example.algorithmsanddatastructures.DataStructures.Queue.PriorityQueue;

import java.util.*;

public class PriorityQueue <T extends Comparable<T>>{

    // number of elements that are currently inside the heap
    private int heapSize = 0;

    // the internal capacity of the heap
    private int heapCapacity = 0;

    // our actual heap that we build queue on
    private List<T> heap = null;

    // hash map to keep in memory indexes of our nodes,
    // so we have faster removals and much faster element check
    private HashMap <T, TreeSet<Integer>> map = new HashMap<>();

    // empty constructor
    public PriorityQueue(){
        this(1);
    }

    // constructor that takes initial size
    public PriorityQueue(int size){
        heap = new ArrayList<>(size);
    }

    public PriorityQueue(T[] elements){
        heapSize = heapCapacity = elements.length;
        heap = new ArrayList<T>( heapCapacity );

        // places all elements in heap
        for(int i = 0; i < heapSize; i++){
            mapAdd(elements[i], i);
            heap.add(elements[i]);
        }
        // heapify process, really important, it is O(n)!!!!
        for (int i = Math.max(0, (heapSize/2)-1); i >=0; i--){
            sink(i);
        }
    }


    // constructing priority queue
    // this is O(n log(n))
    public PriorityQueue (Collection<T> elements){
        this(elements.size());
        for(T elem : elements){
            add(elem);
        }
    }

    // true if empty, false if not
    public boolean isEmpty() {
        return heapSize == 0;
    }

    // clears everything inside the heap
    // also we need to clear the memory of the map
    public void clear(){
        for (int i = 0; i < heapCapacity; i++){
            heap.set(i, null);
        }
        heapSize = 0;
        map.clear();
    }

    // return the size of the heap
    public int size(){
        return heapSize;
    }

    // returns the value of the element with the lowest priority
    public T peek(){
        if (isEmpty()){
            return null;
        }
        return heap.get(0);
    }

    // removes the root of the heap
    public T poll(){
        return removeAt(0);
    }

    // this is what our HashMap is for, we check if our heap node is inside the map
    public boolean contains(T elem){
        if(elem == null){
            return false;
        }
        return map.containsKey(elem);
    }

    // adds an element to the priority queue,
    // the element cannot be null
    public void add(T elem){
        if(elem == null){
            throw new IllegalArgumentException();
        }

        if (heapSize < heapCapacity){
            heap.set(heapSize, elem);
        } else {
            heap.add(elem);
            heapCapacity++;
        }

        mapAdd(elem, heapSize); // adding to the end

        swim(heapSize); // adjusting where it should be
        heapSize++;
    }

    // tests if the value of node i <= node j
    // this method assumes i & j are valid indices, O(1)
    private boolean less(int i, int j){
        T node1 = heap.get(i);
        T node2 = heap.get(j);
        return node1.compareTo(node2) <= 0;
    }

    // bubbling up, O(log(n))
    private void swim(int k){
        int parent = (k-1) / 2; // getting index of parent node, reversing the (2 * i) + 1

        while (k > 0 && less(k, parent)){ // we go up until we reach the root and while we're less than our parent
            swap(parent, k);
            k = parent;

            parent = (k-1) / 2; // grabbing the index of the next parent
        }
    }

    // bubbling down, same O(log(n))
    private void sink(int k) {
        while (true) {
            // taking nodes as in the description in readme
            int left = 2 * k + 1;
            int right = 2 * k + 2;
            int smallest = left; // assuming that left is the smaller one

            // switching if incorrect
            if (right < heapSize && less(right, left)) {
                smallest = right;
            }

            // stop if we're outside the bounds of the tree
            // or stop if we
            if (left >= heapSize || less(k, smallest)) {
                break;
            }

            // move down the tree
            swap(smallest, k);
            k = smallest;
        }
    }

    private void swap(int i, int j){
        T i_elem = heap.get(i);
        T j_elem = heap.get(j);

        heap.set(i, j_elem);
        heap.set(j, i_elem);

        mapSwap(i_elem, j_elem, i, j);
    }

    public boolean remove(T element){
        if(element == null){
            return false;
        }

        Integer index = mapGet(element);
        if (index != null){
            removeAt(index);
        }
        return index != null;
    }

    private T removeAt(int i){
        if (isEmpty()){
            return null;
        }

        heapSize--;
        T removed_data = heap.get(i);
        swap(i, heapSize);

        // deleting the value
        heap.set(heapSize, null);
        mapRemove(removed_data, heapSize);

        // removed data
        if (i == heapSize){
            return removed_data;
        }

        T elem = heap.get(i);

        // trying to sink the element
        sink(i);

        // if sinking did not work
        if(heap.get(i).equals(elem)){
            swim(i);
        }

        return removed_data;
    }

    // recursively checks if this heap is a min heap
    // this is more of an internal method, to check the integrity
    public boolean isMinHeap(int k){

        if(k >= heapSize){
            return true;
        }

        int left = 2 * k + 1;
        int right = 2 * k + 2;

        // make sure that the current node k is less than both children
        if(left < heapSize && !less(k, left)){
            return false;
        }
        if(right < heapSize && !less(k, left)){
            return false;
        }

        // then going also to the children
        return isMinHeap(left) && isMinHeap(right);
    }

    private void mapAdd(T value, int index){

        TreeSet<Integer> set = map.get(value); // int java implementation it is a balanced binary search tree

        // new value being inserted in the map
        if(set == null){
            set = new TreeSet<>();
            set.add(index);
            map.put(value, set);
        } else {
            set.add(index);
        }
    }

    // map helper methods
    private void mapRemove(T value, int index){
        TreeSet<Integer> set = map.get(value);
        set.remove(index); // TreeSets take O(log(n))
        if(set.size() == 0){
            map.remove(value);
        }
    }

    // returning index of a given value, if serveral instances
    // then returns the highest one (how it is already implemented)
    private Integer mapGet(T value){
        TreeSet<Integer> set = map.get(value);
        if(set!= null){
            return set.last();
        }
        return null;
    }

    private void mapSwap(T val1, T val2, int val1Index, int val2Index){
        Set <Integer> set1 = map.get(val1);
        Set <Integer> set2 = map.get(val2);

        set1.remove(val1Index);
        set2.remove(val2Index);
    }

    @Override public String toString(){
        return heap.toString();
    }
}
