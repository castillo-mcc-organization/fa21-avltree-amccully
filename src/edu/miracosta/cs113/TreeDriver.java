package edu.miracosta.cs113;

/**
 * TreeDriver.java : Builds two trees, one AVL, one BST, using random values
 * Both trees will be printed to see how the values are stored in each
 *
 * @author Aaron McCully
 * @version 1.0
 */
public class TreeDriver {

    /**
     * Driver method for storing 20+ random values into a binary search tree and an avl tree
     *
     * @param args not used for driver
     */
    public static void main(String[] args) {
        int size = 25;
        BinarySearchTree<Integer> bstObj = new BinarySearchTree<Integer>();
        AVLTree<Integer> avlTreeObj = new AVLTree<Integer>();

        for(int i = 0; i < size; i++) {
            int randomNum = (int)(Math.random() * (50)) + 1;
            if(bstObj.contains(randomNum)) {
                i--;
                continue;
            }
            bstObj.add(randomNum);
            avlTreeObj.add(randomNum);
        }

        System.out.println(bstObj.toString2());
        System.out.println(avlTreeObj.toString2());
    }
}
