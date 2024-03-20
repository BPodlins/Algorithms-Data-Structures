package com.example.algorithmsanddatastructures.DataStructures.SplayTree;

// Implements a splay tree, a self-adjusting binary search tree with the additional property
// that recently accessed elements are quick to access again.
public class SplayTree<T extends Comparable<T>> {

    private Node<T> root;

    // inserts a new data element into the splay tree and then splays the tree.
    public SplayTree<T> insert(T data) {
        root = insert(root, new Node<>(data));
        return this;
    }

    // helper method to recursively insert a new node into the splay tree, encapsulation.
    private Node<T> insert(Node<T> node, Node<T> nodeToInsert) {
        // base case: insert node here
        if (node == null) {
            return nodeToInsert;
        }
        // recursive case: traverse the tree according to the binary search tree property
        if (nodeToInsert.getData().compareTo(node.getData()) < 0) {
            node.setLeftChild(insert(node.getLeftChild(), nodeToInsert));
            node.getLeftChild().setParent(node);
        } else if (nodeToInsert.getData().compareTo(node.getData()) > 0) {
            node.setRightChild(insert(node.getRightChild(), nodeToInsert));
            node.getRightChild().setParent(node);
        }
        return node;
    }

    // deletes a node with the specified data from the splay tree.
    public void delete(T data) {
        root = delete(data, root);
    }

    // helper method to recursively delete a node from the splay tree.
    private Node<T> delete(T data, Node<T> node) {
        // base case: node not found
        if (node == null){
            return null;
        }

        // recursive case: traverse the tree to find the node to delete
        if (data.compareTo(node.getData()) < 0) {
            // node to delete is in the left subtree
            node.setLeftChild(delete(data, node.getLeftChild()));
            if (node.getLeftChild() != null){
                node.getLeftChild().setParent(node);
            }
        } else if (data.compareTo(node.getData()) > 0) {
            // node to delete is in the right subtree
            node.setRightChild(delete(data, node.getRightChild()));
            if (node.getRightChild() != null){
                node.getRightChild().setParent(node);
            }
        } else {
            // found the node to delete
            if (node.getLeftChild() == null){
                return node.getRightChild();
            }
            else if (node.getRightChild() == null){
                return node.getLeftChild();
            }

            // node to delete has two children
            node.setData(getMax(node.getLeftChild()));
            node.setLeftChild(delete(node.getData(), node.getLeftChild()));
            if (node.getLeftChild() != null){
                node.getLeftChild().setParent(node);
            }
        }
        return node;
    }

    // searches for a node with the specified data in the splay tree and splays it to the root.
    public Node<T> find(T data) {
        Node<T> node = root;
        while (node != null) {
            if (node.getData().compareTo(data) == 0) {
                splay(node);
                return node;
            }
            node = data.compareTo(node.getData()) < 0 ? node.getLeftChild() : node.getRightChild();
        }
        return null;
    }

    // searches for a node with the specified data in the splay tree recursively and splays it to the root.
    public Node<T> findRecursively(T data) {
        return find(root, data);
    }

    // helper method to recursively find a node in the splay tree.
    public Node<T> find(Node<T> node, T data) {
        if (node != null) {
            if (node.getData().compareTo(data) == 0) {
                splay(node);
                return node;
            }
            Node<T> nextNode = data.compareTo(node.getData()) > 0 ? node.getRightChild() : node.getLeftChild();
            find(nextNode, data);
        }
        return null;
    }

    // splays the specified node to the root of the splay tree.
    private void splay(Node<T> node) {
        while(node != root) {
            Node<T> parent = node.getParent();
            // perform tree rotations to bring node to the root
            if (node.getGrandParent() == null) {
                // zig step
                if (node.isLeftChild()) {
                    rotateRight(parent);
                } else {
                    rotateLeft(parent);
                }
            } else if (node.isLeftChild() && parent.isLeftChild()) {
                // zig-zig step
                rotateRight(node.getGrandParent());
                rotateRight(parent);
            } else if (node.isRightChild() && parent.isRightChild()) {
                // zig-zig step
                rotateLeft(node.getGrandParent());
                rotateLeft(parent);
            } else if (node.isLeftChild() && parent.isRightChild()) {
                // zig-zag step
                rotateRight(parent);
                rotateLeft(parent);
            } else {
                // zig-zag step
                rotateLeft(parent);
                rotateRight(parent);
            }
        }
    }

    // performs a right rotation on the specified node.
    private void rotateRight(Node<T> node) {
        Node<T> leftNode = node.getLeftChild();
        node.setLeftChild(leftNode.getRightChild());
        if (node.getLeftChild() != null) {
            node.getLeftChild().setParent(node);
        }
        updateChildrenOfParentNode(node, leftNode);
        leftNode.setParent(node.getParent());
        leftNode.setRightChild(node);
        node.setParent(leftNode);
    }

    // performs a left rotation on the specified node.
    private void rotateLeft(Node<T> node) {
        Node<T> rightNode = node.getRightChild();
        node.setRightChild(rightNode.getLeftChild());
        if (node.getRightChild() != null) {
            node.getRightChild().setParent(node);
        }
        updateChildrenOfParentNode(node, rightNode);
        rightNode.setParent(node.getParent());
        rightNode.setLeftChild(node);
        node.setParent(rightNode);
    }

    // updates the parent node's child references during rotations.
    private void updateChildrenOfParentNode(Node<T> node, Node<T> tempNode) {
        if (node.getParent() == null) {
            root = tempNode;
        } else if (node.isLeftChild()) {
            node.getParent().setLeftChild(tempNode);
        } else {
            node.getParent().setRightChild(tempNode);
        }
    }

    // performs an in-order traversal of the splay tree and prints each node.
    public void traverse() {
        traverseInOrder(root);
    }

    // helper method for in-order traversal.
    private void traverseInOrder(Node<T> node) {
        if (node != null) {
            traverseInOrder(node.getLeftChild());
            System.out.println(node);
            traverseInOrder(node.getRightChild());
        }
    }

    // retrieves the maximum value in the splay tree.
    public T getMax() {
        if (isEmpty()) {
            return null;
        }
        return getMax(root);
    }

    // helper method to find the maximum value in a subtree.
    private T getMax(Node<T> node) {
        if (node.getRightChild() != null) {
            return getMax(node.getRightChild());
        }
        return node.getData();
    }

    // retrieves the minimum value in the splay tree.
    public T getMin() {
        if (isEmpty()) {
            return null;
        }
        return getMin(root);
    }

    // helper method to find the minimum value in a subtree.
    private T getMin(Node<T> node) {
        if (node.getLeftChild() != null) {
            return getMin(node.getLeftChild());
        }
        return node.getData();
    }

    // checks if the splay tree is empty.
    public boolean isEmpty() {
        return root == null;
    }

    // placeholder for compareTo method. Specific comparison logic based on generic type T not implemented.
    public int compareTo(T o) {
        return 0;
    }
}

// node class for SplayTree, encapsulating the data and tree structure.
class Node<T extends Comparable<T>> {
    private T data; // the data element stored in the node.
    private Node<T> leftChild, rightChild, parent; // pointers to the node's children and parent.

    // constructor to create a new node with given data.
    public Node(T data) {
        this.data = data;
    }

    // returns the node's grandparent (if any).
    public Node<T> getGrandParent() {
        return parent != null ? parent.getParent() : null;
    }

    // determines if this node is a left child of its parent.
    public boolean isLeftChild() {
        return this == parent.getLeftChild();
    }

    // determines if this node is a right child of its parent.
    public boolean isRightChild() {
        return this == parent.getRightChild();
    }

    // getters and setters for the node's data and pointers to its children and parent.
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node<T> leftChild) {
        this.leftChild = leftChild;
    }

    public Node<T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node<T> rightChild) {
        this.rightChild = rightChild;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }
}