package edu.miracosta.cs113;

/**
 * AVLTree.java : Extends BinarySearchTreeWithRotate to implement a self-balancing property to binary search tree
 *
 * @author Aaron McCully
 * @version 1.0
 *
 * @param <E> The type of data stored in the tree nodes
 */
public class AVLTree<E extends Comparable<E>> extends BinarySearchTreeWithRotate<E> {
    // data fields
    private boolean increase;

    /**
     * Class to represent an AVL Node. It extends the BinaryTree.Node by adding the balance field.
     *
     * @param <E> The type of data stored in the nodes
     */
    private static class AVLNode<E> extends Node<E> {
        // Constant to indicate left‐heavy
        public static final int LEFT_HEAVY = -1;
        // Constant to indicate balanced
        public static final int BALANCED = 0;
        // Constant to indicate right‐heavy
        public static final int RIGHT_HEAVY = 1;
        // balance is right subtree height – left subtree height
        private int balance;
        // Methods
        /**
         * Construct a node with the given item as the data field.
         * @param item The data field
         */
        public AVLNode(E item) {
            super(item);
            balance = BALANCED;
        }
        @Override
        public String toString() {
            return balance + ": " + super.toString();
        }
    }

    @Override
    public boolean add(E item) {
        increase = false;
        root = add((AVLNode<E>) root, item);
        return addReturn;
    }

    /**
     * Recursive add method. Inserts the given object into the tree.
     * @post addReturn is set true if the item is inserted, false if the item is already in the tree.
     * @param localRoot The local root of the subtree
     * @param item The object to be inserted
     * @return The new local root of the subtree with the item inserted
     */
    private AVLNode<E> add(AVLNode<E> localRoot, E item) {
        if (localRoot == null) {
            addReturn = true;
            increase = true;
            return new AVLNode<E>(item);
        }
        if (item.compareTo(localRoot.data) == 0) {
            // Item is already in the tree.
            increase = false;
            addReturn = false;
            return localRoot;
        }
        else if (item.compareTo(localRoot.data) < 0) {
            // item < data
            localRoot.left = add((AVLNode<E>) localRoot.left, item);
            if (increase) {
                decrementBalance(localRoot);
                if (localRoot.balance < AVLNode.LEFT_HEAVY) {
                    increase = false;
                    return rebalanceLeft(localRoot);
                }
            }
            return localRoot; // Rebalance not needed.
        }
        else {
            // item > data
            localRoot.right = add((AVLNode<E>) localRoot.right, item);
            if (increase) {
                incrementBalance(localRoot);
                if (localRoot.balance > AVLNode.RIGHT_HEAVY) {
                    increase = false;
                    return rebalanceRight(localRoot);
                }
            }
            return localRoot; // Rebalance not needed
        }
    }

    /**
     * Method to rebalance left.
     * @pre localRoot is the root of an AVL subtree that is critically left‐heavy.
     * @post Balance is restored.
     * @param localRoot Root of the AVL subtree that needs rebalancing
     * @return a new localRoot
     */
    private AVLNode<E> rebalanceLeft(AVLNode<E> localRoot) {
        // Obtain reference to left child.
        AVLNode<E> leftChild = (AVLNode<E>) localRoot.left;
        // See whether left‐right heavy.
        if (leftChild.balance > AVLNode.BALANCED) {
            // Obtain reference to left‐right child.
            AVLNode<E> leftRightChild = (AVLNode<E>) leftChild.right;
            // Adjust the balances to be their new values after the rotations are performed.
            if (leftRightChild.balance < AVLNode.BALANCED) {
                leftChild.balance = AVLNode.BALANCED;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.RIGHT_HEAVY;
            }
            else if(leftRightChild.balance > AVLNode.BALANCED) {
                // testing this out
                leftChild.balance = AVLNode.LEFT_HEAVY;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }
            else {
                // new else added here
                leftChild.balance = AVLNode.BALANCED;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }
            // Perform left rotation.
            localRoot.left = rotateLeft(leftChild);
        }
        else {
            // Left‐Left case
            // In this case the leftChild (the new root) and the root
            // (new right child) will both be balanced after the rotation.
            leftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
        // Now rotate the local root right.
        return (AVLNode<E>) rotateRight(localRoot);
    }

    /**
     * Method to rebalance right.
     * @pre localRoot is the root of an AVL subtree that is critically right‐heavy.
     * @post Balance is restored.
     * @param localRoot Root of the AVL subtree that needs rebalancing
     * @return a new localRoot
     */
    private AVLNode<E> rebalanceRight(AVLNode<E> localRoot) {
        AVLNode<E> rightChild = (AVLNode<E>) localRoot.right;
        // See whether right‐left heavy.
        if (rightChild.balance < AVLNode.BALANCED) {
            // Obtain reference to right‐left child.
            AVLNode<E> rightLeftChild = (AVLNode<E>) rightChild.left;
            // Adjust the balances to be their new values after the rotations are performed.
            if (rightLeftChild.balance > AVLNode.BALANCED) {
                rightChild.balance = AVLNode.BALANCED;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.LEFT_HEAVY;
            }
            else if(rightLeftChild.balance < AVLNode.BALANCED) {
                // testing this out
                rightChild.balance = AVLNode.RIGHT_HEAVY;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }
            else {
                //new else added here
                rightChild.balance = AVLNode.BALANCED;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }
            // Perform right rotation.
            localRoot.right = rotateRight(rightChild);
        }
        else {
            // Right-Right case
            // In this case the rightChild (the new root) and the root
            // (new left child) will both be balanced after the rotation.
            rightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
        // Now rotate the local root left.
        return (AVLNode<E>) rotateLeft(localRoot);
    }

    /**
     * Method to decrease the balance of a node by 1
     *
     * @param node The node to change the balance of
     */
    private void decrementBalance(AVLNode<E> node) {
        // Decrement the balance.
        node.balance--;
        if (node.balance == AVLNode.BALANCED) {
            // If now balanced, overall height has not increased.
            increase = false;
        }
    }

    /**
     * Method to increase the balance of a node by 1
     *
     * @param node The node to change the balance of
     */
    private void incrementBalance(AVLNode<E> node) {
        // Increment the balance.
        node.balance++;
        if (node.balance == AVLNode.BALANCED) {
            // If now balanced, overall height has not increased.
            increase = false;
        }
    }
}
