package com.example.algorithmsanddatastructures.DataStructures.BinaryTree.RedBlackTree;

import static com.example.algorithmsanddatastructures.DataStructures.BinaryTree.RedBlackTree.RedBlackTree.BLACK;

public class RedBlackTree {
    private Node root;
    static final boolean RED = false;
    static final boolean BLACK = true;

    public Node searchNode(int key) {
        Node node = root;
        while (node != null) {
            if (key == node.data) {
                return node;
            } else if (key < node.data) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        return null;
    }

    public Node getRoot() {
        return root;
    }

    //Insert
    public void insertNode(int key) {
        Node node = root;
        Node parent = null;

        while (node != null) {  //traversing the tree to the left or right depending on the key
            parent = node;
            if (key < node.data) {
                node = node.left;
            } else if (key > node.data) {
                node = node.right;
            } else {
                throw new IllegalArgumentException("Node with key: " +key +" already exists");
            }
        }

        //inserting new
        Node newNode = new Node(key);
        newNode.color = RED;
        if (parent == null) {
            root = newNode;
        } else if (key < parent.data) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        newNode.parent = parent;

        fixRedBlackPropertiesAfterInsert(newNode); //after insertion fixing colors on whole tree
    }

    private void fixRedBlackPropertiesAfterInsert(Node node) {
        Node parent = node.parent;

        //reaching the root
        if (parent == null) {
            node.color = BLACK;
            return;
        }

        //parent is black, so nothing to do
        if (parent.color == BLACK) {
            return;
        }

        //other cases parent is red
        Node grandparent = parent.parent;

        //get the uncle (may be null/nil, in which case its color is BLACK)
        Node uncle = getUncle(parent);

        // Case 3: uncle is red -> recolor parent, grandparent and uncle
        if (uncle != null && uncle.color == RED) {
            parent.color = BLACK;
            grandparent.color = RED;
            uncle.color = BLACK;

            //call recursively for grandparent, which is now red.
            //it might be root or have a red parent, in which case we need to fix more...
            fixRedBlackPropertiesAfterInsert(grandparent);
        }

        // Note on performance:
        //it would be faster to do the uncle color check within the following code. This way
        //we would avoid checking the grandparent-parent direction twice (once in getUncle()
        //and once in the following else-if). But for better understanding of the code,
        //I left the uncle color check as a separate step.

        //parent is left child of grandparent
        else if (parent == grandparent.left) {
            // Case 4a: Uncle is black and node is left->right "inner child" of its grandparent
            if (node == parent.right) {
                rotateLeft(parent);

                //let "parent" point to the new root node of the rotated sub-tree.
                //it will be recolored in the next step, which we're going to fall-through to.
                parent = node;
            }

            // Case 5a: Uncle is black and node is left->left "outer child" of its grandparent
            rotateRight(grandparent);

            //recolor original parent and grandparent
            parent.color = BLACK;
            grandparent.color = RED;
        }

        //parent is right child of grandparent
        else {
            // Case 4b: Uncle is black and node is right->left "inner child" of its grandparent
            if (node == parent.left) {
                rotateRight(parent);

                //let "parent" point to the new root node of the rotated sub-tree.
                //it will be recolored in the next step, which we're going to fall-through to.
                parent = node;
            }

            // Case 5b: uncle is black and node is right->right "outer child" of its grandparent
            rotateLeft(grandparent);

            //recolor original parent and grandparent
            parent.color = BLACK;
            grandparent.color = RED;
        }
    }

    //helper function to look for uncle
    private Node getUncle(Node parent) {
        Node grandparent = parent.parent;
        if (grandparent.left == parent) {
            return grandparent.right;
        } else if (grandparent.right == parent) {
            return grandparent.left;
        } else {
            throw new IllegalStateException("Parent is not a child of its grandparent");
        }
    }
    //helper function to look for sibling
    private Node getSibling(Node node) {
        Node parent = node.parent;
        if (node == parent.left) {
            return parent.right;
        } else if (node == parent.right) {
            return parent.left;
        } else {
            throw new IllegalStateException("Parent is not a child of its grandparent");
        }
    }

    //Deleting
    public void deleteNode(int key) {
        Node node = root;

        //find the node to be deleted
        while (node != null && node.data != key) {
            //traverse the tree to the left or right depending on the key
            if (key < node.data) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        //node not found
        if (node == null) {
            return;
        }

        //in this variable, we'll store the node at which we're going to start to fix the R-B
        //properties after deleting a node.
        Node movedUpNode;
        boolean deletedNodeColor;

        //node has zero or one child
        if (node.left == null || node.right == null) {
            movedUpNode = deleteNodeWithZeroOrOneChild(node);
            deletedNodeColor = node.color;
        }

        //node has two children
        else {
            //find minimum node of right subtree
            Node inOrderSuccessor = findMinimum(node.right);

            //copy inorder successor's data to current node
            node.data = inOrderSuccessor.data;

            //delete inorder successor just as we would delete a node with 0 or 1 child
            movedUpNode = deleteNodeWithZeroOrOneChild(inOrderSuccessor);
            deletedNodeColor = inOrderSuccessor.color;
        }

        if (deletedNodeColor == BLACK) {
            fixRedBlackPropertiesAfterDelete(movedUpNode);

            //remove the temporary NIL node
            if (movedUpNode.getClass() == NilNode.class) {
                replaceParentsChild(movedUpNode.parent, movedUpNode, null);
            }
        }
    }

    private Node deleteNodeWithZeroOrOneChild(Node node) {
        //if node has only a left child, then replace by its left child
        if (node.left != null) {
            replaceParentsChild(node.parent, node, node.left);
            return node.left; // moved-up node
        }

        //if node has only a right child, then replace by its right child
        else if (node.right != null) {
            replaceParentsChild(node.parent, node, node.right);
            return node.right; // moved-up node
        }

        //node has no children -->
        //if node is red --> just remove it
        //if node is black --> replace it by a temporary NIL node to fix the red black properties
        else {
            Node newChild = node.color == BLACK ? new NilNode() : null;
            replaceParentsChild(node.parent, node, newChild);
            return newChild;
        }
    }

    private Node findMinimum(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    //function to fix the color properties after deleting
    private void fixRedBlackPropertiesAfterDelete(Node node) {
        // Case 1: Examined node is root, we can exit the recursion
        if (node == root) {
            node.color = BLACK;
            return;
        }

        Node sibling = getSibling(node);

        // Case 2: Red sibling
        if (sibling.color == RED) {
            handleRedSibling(node, sibling);
            sibling = getSibling(node); //get new sibling for cases 3-6
        }

        // Cases 3 and 4: Black sibling with two black children
        if (isBlack(sibling.left) && isBlack(sibling.right)) {
            sibling.color = RED;

            // Case 3: Black sibling with two black children + red parent
            if (node.parent.color == RED) {
                node.parent.color = BLACK;
            }

            // Case 4: Black sibling with two black children + black parent
            else {
                fixRedBlackPropertiesAfterDelete(node.parent);
            }
        }
        //otherwise function to handle cases 5 and 6
        else {
            handleBlackSiblingWithAtLeastOneRedChild(node, sibling);
        }
    }

    //handling cases 5 and 6
    private void handleBlackSiblingWithAtLeastOneRedChild(Node node, Node sibling) {
        boolean nodeIsLeftChild = node == node.parent.left;

        // Case 5: Black sibling with at least one red child + "outer nephew" is black
        // --> recolor sibling and its child, and rotate around sibling
        if (nodeIsLeftChild && isBlack(sibling.right)) {
            sibling.left.color = BLACK;
            sibling.color = RED;
            rotateRight(sibling);
            sibling = node.parent.right;
        } else if (!nodeIsLeftChild && isBlack(sibling.left)) {
            sibling.right.color = BLACK;
            sibling.color = RED;
            rotateLeft(sibling);
            sibling = node.parent.left;
        }

        // Case 6: Black sibling with at least one red child + "outer nephew" is red
        // --> recolor sibling + parent + sibling's child, and rotate around parent
        sibling.color = node.parent.color;
        node.parent.color = BLACK;
        if (nodeIsLeftChild) {
            sibling.right.color = BLACK;
            rotateLeft(node.parent);
        } else {
            sibling.left.color = BLACK;
            rotateRight(node.parent);
        }
    }

    private void handleRedSibling(Node node, Node sibling) {
        //recoloring
        sibling.color = BLACK;
        node.parent.color = RED;

        //rotating
        if (node == node.parent.left) {
            rotateLeft(node.parent);
        } else {
            rotateRight(node.parent);
        }
    }


    //helper function to check if node is black
    private boolean isBlack(Node node) {
        return node == null || node.color == BLACK;
    }

    //rotation functions
    private void rotateRight(Node node) {
        Node parent = node.parent;
        Node leftChild = node.left;

        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }

        leftChild.right = node;
        node.parent = leftChild;

        replaceParentsChild(parent, node, leftChild);
    }

    private void rotateLeft(Node node) {
        Node parent = node.parent;
        Node rightChild = node.right;

        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }

        rightChild.left = node;
        node.parent = rightChild;

        replaceParentsChild(parent, node, rightChild);
    }

    //helper function to replace parents
    private void replaceParentsChild(Node parent, Node oldChild, Node newChild) {
        if (parent == null) {
            root = newChild;
        } else if (parent.left == oldChild) {
            parent.left = newChild;
        } else if (parent.right == oldChild) {
            parent.right = newChild;
        } else {
            throw new IllegalStateException("node is not a child of its parent");
        }

        if (newChild != null) {
            newChild.parent = parent;
        }
    }

    public void appendNodeToString(Node node, StringBuilder builder) {
        builder.append(node.data).append(node.color == RED ? "[R]" : "[B]");
    }

    private void appendNodeToStringRecursive(Node node, StringBuilder builder) {
        appendNodeToString(node, builder);
        if (node.left != null) {
            builder.append(" L{");
            appendNodeToStringRecursive(node.left, builder);
            builder.append('}');
        }
        if (node.right != null) {
            builder.append(" R{");
            appendNodeToStringRecursive(node.right, builder);
            builder.append('}');
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        appendNodeToStringRecursive(getRoot(), builder);
        return builder.toString();
    }
}

class Node {
    int data;
    Node left;
    Node right;
    Node parent;

    boolean color;
    public Node(int data){
        this.data = data;
    }
}

//guard node
class NilNode extends Node {
    public NilNode() {
        super(0);
        this.color = BLACK;
    }
}
