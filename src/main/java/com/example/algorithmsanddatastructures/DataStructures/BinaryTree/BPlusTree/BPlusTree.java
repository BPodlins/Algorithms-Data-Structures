package com.example.algorithmsanddatastructures.DataStructures.BinaryTree.BPlusTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a B+ Tree data structure.
 */
public class BPlusTree {
    Node root;

    // initializes a B+Tree with a specified order. The root is initially a leaf node.
    public BPlusTree(int order) {
        this.root = new Node(order);
        this.root.isLeaf = true;
    }


    // inserts a new value and associated node into the B+Tree.
    public void insert(String value, Node key) {
        // search for the leaf node where the new value should be inserted
        Node oldNode = this.search(value);
        oldNode.insertAtLeaf(value, key);

        // if the node's size equals its order, it needs to be split
        if (oldNode.values.size() == oldNode.order) {
            Node newNode = new Node(oldNode.order);
            newNode.isLeaf = true;
            newNode.parent = oldNode.parent;

            int mid = (int) Math.ceil(oldNode.order / 2.0) - 1;

            // distribute values and keys between old and new nodes
            newNode.values = new ArrayList<>(oldNode.values.subList(mid + 1, oldNode.values.size()));
            newNode.keys = new ArrayList<>(oldNode.keys.subList(mid + 1, oldNode.keys.size()));
            newNode.nextKey = oldNode.nextKey;

            oldNode.values = new ArrayList<>(oldNode.values.subList(0, mid + 1));
            oldNode.keys = new ArrayList<>(oldNode.keys.subList(0, mid + 1));
            oldNode.nextKey = newNode;

            // insert the new node into the parent
            this.insertInParent(oldNode, newNode.values.get(0), newNode);
        }
    }

    // searches for the leaf node where a given value should be inserted.

    public Node search(String value) {
        Node currentNode = this.root;
        while (!currentNode.isLeaf) {
            boolean found = false;
            for (int i = 0; i < currentNode.values.size(); i++) {
                // navigating through the nodes based on the value comparison
                if (value.compareTo(currentNode.values.get(i)) < 0 || value.equals(currentNode.values.get(i))) {
                    currentNode = currentNode.keys.get(i).get(0);
                    found = true;
                    break;
                }
            }
            if (!found) {
                // move to the rightmost child if value is greater than all node values
                currentNode = currentNode.keys.get(currentNode.values.size()).get(0);
            }
        }
        return currentNode;
    }

    // checks if a value exists in the B+Tree
    public boolean find(String value, Node key) {
        Node leaf = this.search(value);
        for (int i = 0; i < leaf.values.size(); i++) {
            if (leaf.values.get(i).equals(value) && leaf.keys.get(i).contains(key)) {
                return true;
            }
        }
        return false;
    }

     // inserts a new value and node into the parent node after a split operation.
     // @param n the original node being split
     // @param value the value to insert into the parent node
     // @param ndash the new node created from splitting

    public void insertInParent(Node n, String value, Node ndash) {
        if (this.root == n) {
            // create a new root node if the current node is the root
            Node rootNode = new Node(n.order);
            rootNode.values.add(value);
            rootNode.keys.add(new ArrayList<>());
            rootNode.keys.add(new ArrayList<>());
            rootNode.keys.get(0).add(n);
            rootNode.keys.get(1).add(ndash);
            this.root = rootNode;
            n.parent = rootNode;
            ndash.parent = rootNode;
            return;
        }

        // insert the new node and value into the existing parent node
        Node parentNode = n.parent;
        int insertionIndex = parentNode.keys.indexOf(n) + 1;
        parentNode.values.add(insertionIndex - 1, value);
        parentNode.keys.add(insertionIndex, new ArrayList<>(List.of(ndash)));
        ndash.parent = parentNode;

        // if the parent node exceeds its order, split the parent node
        if (parentNode.values.size() > parentNode.order) {
            splitParentNode(parentNode);
        }
    }


     // Splits a parent node that exceeds its order after an insertion.
     // @param parentNode the parent node to split

    private void splitParentNode(Node parentNode) {
        Node newParent = new Node(parentNode.order);
        int midIndex = parentNode.order / 2;

        // distribute values and keys to the new parent node
        newParent.values = new ArrayList<>(parentNode.values.subList(midIndex + 1, parentNode.values.size()));
        newParent.keys = new ArrayList<>(parentNode.keys.subList(midIndex + 1, parentNode.keys.size() + 1));

        String midValue = parentNode.values.get(midIndex);
        parentNode.values = new ArrayList<>(parentNode.values.subList(0, midIndex));
        parentNode.keys = new ArrayList<>(parentNode.keys.subList(0, midIndex + 1));

        // update parent references for the nodes moved to the new parent
        newParent.keys.forEach(list -> list.get(0).parent = newParent);

        if (parentNode == this.root) {
            // if the parent node is the root, create a new root
            Node newRoot = new Node(parentNode.order);
            newRoot.values.add(midValue);
            newRoot.keys.add(new ArrayList<>(List.of(parentNode)));
            newRoot.keys.add(new ArrayList<>(List.of(newParent)));
            this.root = newRoot;
            parentNode.parent = newRoot;
            newParent.parent = newRoot;
        } else {
            // otherwise, insert the mid value and new parent into the grandparent
            insertInParent(parentNode, midValue, newParent);
        }
    }

    public void printTree() {
        if (root == null) {
            System.out.println("The tree is empty.");
            return;
        }

        List<Node> currentLevelNodes = new ArrayList<>();
        currentLevelNodes.add(root); // Start with the root.

        while (!currentLevelNodes.isEmpty()) {
            List<Node> nextLevelNodes = new ArrayList<>();
            for (Node node : currentLevelNodes) {
                // print all values in the current node separated by spaces.
                System.out.print(node.values.toString() + " ");

                // if this node is not a leaf, add its children to the list for the next level.
                if (!node.isLeaf) {
                    for (List<Node> childList : node.keys) {
                        // assuming the child nodes are the first elements of the lists in 'keys'.
                        nextLevelNodes.add(childList.get(0));
                    }
                }
            }
            System.out.println(); // move to the next line before printing nodes of the next level.

            // prepare for the next level.
            currentLevelNodes = nextLevelNodes;
        }
    }
}


 // represents a node within a B+Tree.

class Node {
    int order;
    List<String> values;
    List<List<Node>> keys;
    Node nextKey;
    Node parent;
    boolean isLeaf;

    /**
     * constructs a new Node with a specified order.
     *
     * @param order the order of the node
     */
    public Node(int order) {
        this.order = order;
        this.values = new ArrayList<>();
        this.keys = new ArrayList<>();
        this.isLeaf = false; // default to non-leaf until set otherwise
    }


    // inserts a value and key at this leaf node. If the value already exists, adds the key to the existing list.
    // @param key the key associated with the value

    public void insertAtLeaf(String value, Node key) {
        int i = 0;
        for (; i < this.values.size(); i++) {
            if (value.compareTo(this.values.get(i)) < 0) break;
            if (value.equals(this.values.get(i))) {
                this.keys.get(i).add(key);
                return;
            }
        }
        this.values.add(i, value);
        List<Node> newList = new ArrayList<>();
        newList.add(key);
        this.keys.add(i, newList);
    }
}
