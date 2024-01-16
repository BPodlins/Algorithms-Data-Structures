package com.example.algorithmsanddatastructures.DataStructures.BinaryTree.AVL;

import java.util.Iterator;

import static java.lang.Math.max;

public class AVLTree<T extends Comparable<T>> implements Iterable<T>{
    public Node root;

    //tracks the number of nodes inside the tree.
    private int nodeCount = 0;

    //special token value used as an alternative to returning null.
    //the TOKEN is used to indicate special return value signals. For example,
    //we can return the TOKEN instead of null when we're inserting a new item
    //and discover the value we were inserting already exists in the tree.
    private Node TOKEN = new Node(null);

    //the height of a rooted tree is the number of edges between the tree's
    //root and its furthest leaf. This means that a tree containing a single
    //node has a height of 0.
    public int height() {
        if (root == null) {
            return 0;
        }
        return root.height;
    }

    //returns the number of nodes inside the tree
    public int size() {
        return nodeCount;
    }

    //returns if tree is empty
    public boolean isEmpty() {
        return size() == 0;
    }

    //returns if the tree already contains the value
    public boolean contains(T value) {

        Node node = root;
        while (node != null) {

            //compare current value to the value in the node
            int cmp = value.compareTo((T) node.value);

            if (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else return true;
        }
        return false;
    }

    //insert/add a value to the AVL tree. The value must not be null, O(log(n))
    public boolean insert(T value) {
        if (value == null) return false;
        Node newRoot = insert(root, value);
        boolean insertedNode = (newRoot != TOKEN);
        if (insertedNode) {
            nodeCount++;
            root = newRoot;
        }
        return insertedNode;
    }

    //inserts a value inside the AVL tree. This method returns 'TOKEN' if
    //the value we tried to insert was already inside the tree, otherwise
    //the new (or old) root node is returned.
    private Node insert(Node node, T value) {

        //base case.
        if (node == null){
            return new Node(value);
        }

        //compare current value to the value in the node.
        int cmp = value.compareTo((T) node.value);

        //insert node in left subtree.
        if (cmp < 0) {
            Node newLeftNode = insert(node.left, value);
            if (newLeftNode == TOKEN) return TOKEN;
            node.left = newLeftNode;

            //insert node in right subtree.
        } else if (cmp > 0) {
            Node newRightNode = insert(node.right, value);
            if (newRightNode == TOKEN) return TOKEN;
            node.right = newRightNode;

            //return 'TOKEN' to indicate a duplicate value in the tree.
        } else return TOKEN;

        //update balance factor and height values.
        update(node);

        //re-balance tree.
        return balance(node);
    }

    //update a node's height and balance factor.
    private void update(Node node) {

        int leftNodeHeight = (node.left == null) ? -1 : node.left.height;
        int rightNodeHeight = (node.right == null) ? -1 : node.right.height;

        //update this node's height.
        node.height = 1 + Math.max(leftNodeHeight, rightNodeHeight);

        //update balance factor.
        node.bf = rightNodeHeight - leftNodeHeight;
    }

    //re-balance a node if its balance factor is +2 or -2.
    private Node balance(Node node) {

        //left heavy subtree case
        if (node.bf == -2) {

            //left-left case
            if (node.left.bf <= 0) {
                return leftLeftCase(node);

                //left-right case
            } else {
                return leftRightCase(node);
            }

            //right heavy subtree needs balancing
        } else if (node.bf == +2) {

            //right-right case
            if (node.right.bf >= 0) {
                return rightRightCase(node);

                //right-left case
            } else {
                return rightLeftCase(node);
            }
        }

        //node either has a balance factor of 0, +1 or -1 which is fine.
        return node;
    }

    // rotation functions and helpers to those functions
    private Node leftLeftCase(Node node) {
        return rightRotation(node);
    }

    private Node leftRightCase(Node node) {
        node.left = leftRotation(node.left);
        return leftLeftCase(node);
    }

    private Node rightRightCase(Node node) {
        return leftRotation(node);
    }

    private Node rightLeftCase(Node node) {
        node.right = rightRotation(node.right);
        return rightRightCase(node);
    }

    private Node leftRotation(Node node) {
        Node newParent = node.right;
        node.right = newParent.left;
        newParent.left = node;
        update(node);
        update(newParent);
        return newParent;
    }

    private Node rightRotation(Node node) {
        Node newParent = node.left;
        node.left = newParent.right;
        newParent.right = node;
        update(node);
        update(newParent);
        return newParent;
    }

    //remove a value from this binary tree if it exists, O(log(n))
    public boolean remove(T elem) {

        Node newRoot = remove(root, elem);
        boolean removedNode = (newRoot != TOKEN) || (newRoot == null);

        if (removedNode) {
            root = newRoot;
            nodeCount--;
            return true;
        }

        return false;
    }

    //removes a value from the AVL tree. If the value we're trying to remove
    //does not exist in the tree then the 'TOKEN' value is returned. Otherwise,
    //the new (or old) root node is returned.
    private Node remove(Node node, T elem) {

        //return 'TOKEN' to indicate value to remove was not found.
        if (node == null){
            return TOKEN;
        }

        int cmp = elem.compareTo((T) node.value);

        //dig into left subtree, the value we're looking
        //for is smaller than the current value.
        if (cmp < 0) {
            Node newLeftNode = remove(node.left, elem);
            if (newLeftNode == TOKEN) return TOKEN;
            node.left = newLeftNode;

            //dig into right subtree, the value we're looking
            //for is greater than the current value.
        } else if (cmp > 0) {
            Node newRightNode = remove(node.right, elem);
            if (newRightNode == TOKEN) return TOKEN;
            node.right = newRightNode;

            //found the node we wish to remove.
        } else {

            //this is the case with only a right subtree or no subtree at all.
            //in this situation just swap the node we wish to remove
            //with its right child.
            if (node.left == null) {
                return node.right;

                //this is the case with only a left subtree or
                //no subtree at all. In this situation just
                //swap the node we wish to remove with its left child.
            } else if (node.right == null) {
                return node.left;

                //when removing a node from a binary tree with two links the
                //successor of the node being removed can either be the largest
                //value in the left subtree or the smallest value in the right
                //subtree. As a heuristic, I will remove from the subtree with
                //the most nodes in hopes that this may help with balancing.
            } else {

                //choose to remove from left subtree
                if (node.left.height > node.right.height) {

                    //swap the value of the successor into the node.
                    T successorValue = findMax(node.left);
                    node.value = successorValue;

                    //find the largest node in the left subtree.
                    Node replacement = remove(node.left, successorValue);
                    if (replacement == TOKEN) return TOKEN;
                    node.left = replacement;

                } else {

                    //swap the value of the successor into the node.
                    T successorValue = findMin(node.right);
                    node.value = successorValue;

                    //go into the right subtree and remove the leftmost node we
                    //found and swapped data with. This prevents us from having
                    //two nodes in our tree with the same value.
                    Node replacement = remove(node.right, successorValue);
                    if (replacement == TOKEN) return TOKEN;
                    node.right = replacement;
                }
            }
        }

        //update balance factor and height values.
        update(node);

        //re-balance tree.
        return balance(node);
    }

    //helper method to find the leftmost node (which has the smallest value)
    private T findMin(Node node) {
        while (node.left != null) node = node.left;
        return (T) node.value;
    }

    //helper method to find the rightmost node (which has the largest value)
    private T findMax(Node node) {
        while (node.right != null) node = node.right;
        return (T) node.value;
    }

    //implemented iterator
    @Override
    public Iterator<T> iterator() {

        final int expectedNodeCount = nodeCount;
        final java.util.Stack<Node> stack = new java.util.Stack<>();
        stack.push(root);

        return new java.util.Iterator<T>() {
            Node trav = root;

            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {

                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();

                while (trav != null && trav.left != null) {
                    stack.push(trav.left);
                    trav = trav.left;
                }

                Node node = stack.pop();

                if (node.right != null) {
                    stack.push(node.right);
                    trav = node.right;
                }

                return (T) node.value;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    //make sure all left child nodes are smaller in value than their parent and
    //make sure all right child nodes are greater in value than their parent.
    //(Used only for testing)
    boolean validateBSTInvarient(Node node) {
        if (node == null) return true;
        T val = (T) node.value;
        boolean isValid = true;
        if (node.left != null) isValid = isValid && node.left.value.compareTo(val) < 0;
        if (node.right != null) isValid = isValid && node.right.value.compareTo(val) > 0;
        return isValid && validateBSTInvarient(node.left) && validateBSTInvarient(node.right);
    }
}
class Node<T extends Comparable<T>> {

    //'bf' is short for Balance Factor
    public int bf;

    public T value;

    //the height of this node in the tree.
    public int height;

    //the left and the right children of this node.
    public Node<T> left, right; // Specify the type parameter for left and right nodes.

    //constructor
    public Node(T value) {
        this.value = value;
    }
}
