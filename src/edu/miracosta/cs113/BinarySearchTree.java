package edu.miracosta.cs113;

/**
 * BinarySearchTree.java : Extends from BinaryTree to allow for data to be stored more sensibly
 * All values to the right of the node must be greater, all values to the left of the node must be less
 *
 * @author Aaron McCully
 * @version 1.0
 *
 * @param <E> The type of data stored in the tree nodes
 */
public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E> implements SearchTree<E> {
    // data fields (protected)
    protected boolean addReturn;
    protected E deleteReturn;

    @Override
    public E delete(E item) {
        root = delete(root, item);
        return deleteReturn;
    }

    /**
     * Recursive delete method.
     * post: The item is not in the tree;
     * deleteReturn is equal to the deleted item as it was stored in the tree or null
     * if the item was not found.
     * @param localRoot The root of the current subtree
     * @param item The item to be deleted
     * @return The modified local root that does not contain the item
     */
    private Node<E> delete(Node<E> localRoot, E item) {
        if (localRoot == null) {
            // item is not in the tree.
            deleteReturn = null;
            return null;
        }
        // Search for item to delete.
        int compResult = item.compareTo(localRoot.data);
        if (compResult < 0) {
            // item is smaller than localRoot.data.
            localRoot.left = delete(localRoot.left, item);
            return localRoot;
        } else if (compResult > 0) {
            // item is larger than localRoot.data.
            localRoot.right = delete(localRoot.right, item);
            return localRoot;
        } else {
            // item is at local root.
            deleteReturn = localRoot.data;
            if (localRoot.left == null) {
                // If there is no left child, return right child which can also be null.
                return localRoot.right;
            } else if (localRoot.right == null) {
                // If there is no right child, return left child.
                return localRoot.left;
            } else {
                // Node being deleted has 2 children, replace the data with inorder predecessor.
                if (localRoot.left.right == null) {
                    // The left child has no right child.
                    // Replace the data with the data in the left child.
                    localRoot.data = localRoot.left.data;
                    // Replace the left child with its left child
                    localRoot.left = localRoot.left.left;
                    return localRoot;
                }
                else {
                    // Search for the inorder predecessor (ip) and replace deleted node's data with ip.
                    localRoot.data = findLargestChild(localRoot.left);
                    return localRoot;
                }
            }
        }
    }

    /**
     * Find the node that is the inorder predecessor and replace it
     * with its left child (if any).
     * post: The inorder predecessor is removed from the tree.
     * @param parent The parent of possible inorder predecessor (ip)
     * @return The data in the ip
     */
    private E findLargestChild(Node<E> parent) {
        // If the right child has no right child, it is the inorder predecessor.
        if (parent.right.right == null) {
            E returnValue = parent.right.data;
            parent.right = parent.right.left;
            return returnValue;
        }
        return findLargestChild(parent.right);
    }

    @Override
    public E find(E item) {
        return find(root, item);
    }

    /**
     * Recursive find method.
     * @param localRoot The local subtree's root
     * @param item The object being sought
     * @return The object, if found, otherwise null
     */
    private E find(Node<E> localRoot, E item) {
        if(localRoot == null) {
            return null;
        }
        int compResult = item.compareTo(localRoot.data);
        if(compResult == 0) {
            return localRoot.data;
        }
        else if(compResult < 0) {
            return find(localRoot.left, item);
        }
        else {
           return find(localRoot.right, item);
        }
    }

    @Override
    public boolean contains(E item) {
        if(find(item) == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean remove(E item) {
        if(delete(item) != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(E item) {
        root = add(root, item);
        return addReturn;
    }

    /**
     * Recursive add method.
     * post: The data field addReturn is set true if the item is added to the tree,
     * false if the item is already in the tree.
     * @param localRoot The local root of the subtree
     * @param item The object to be inserted
     * @return The new local root that now contains the inserted item
     */
    private Node<E> add(Node<E> localRoot, E item) {
        if (localRoot == null) {
            // item is not in the tree — insert it.
            addReturn = true;
            return new Node<E>(item);
        }
        else if (item.compareTo(localRoot.data) == 0) {
            // item is equal to localRoot.data
            addReturn = false;
            return localRoot;
        }
        else if (item.compareTo(localRoot.data) < 0) {
            // item is less than localRoot.data
            localRoot.left = add(localRoot.left, item);
            return localRoot;
        }
        else {
            // item is greater than localRoot.data
            localRoot.right = add(localRoot.right, item);
            return localRoot;
        }
    }
}
