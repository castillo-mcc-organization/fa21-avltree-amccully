package edu.miracosta.cs113;

public class TreeDriver {

    public static void main(String[] args) {
        // change to 20+ later
        // check to make sure no repeated values?
        int size = 22;
        BinarySearchTree<Integer> bstObj = new BinarySearchTree<Integer>();
        AVLTree<Integer> avlTreeObj = new AVLTree<Integer>();

        for(int i = 0; i < size; i++) {
            int randomNum = (int)(Math.random() * (size+10)) + 1;
            if(bstObj.contains(randomNum)) {
                i--;
                continue;
            }
            bstObj.add(randomNum);
            avlTreeObj.add(randomNum);
        }
        /* Use this as example, note steps between add 19 and add 20
        avlTreeObj.add(30);
        avlTreeObj.add(9);
        avlTreeObj.add(5);
        avlTreeObj.add(28);
        avlTreeObj.add(27);
        avlTreeObj.add(4);
        avlTreeObj.add(2);
        avlTreeObj.add(25);
        avlTreeObj.add(24);
        avlTreeObj.add(22);
        avlTreeObj.add(21);
        avlTreeObj.add(19);
        avlTreeObj.add(20);
        avlTreeObj.add(18);
        */

        /*
        avlTreeObj.add(8);
        avlTreeObj.add(9);
        avlTreeObj.add(10);
        avlTreeObj.add(11);
        avlTreeObj.add(13);
        avlTreeObj.add(12);
        avlTreeObj.add(18);
        avlTreeObj.add(25);
        avlTreeObj.add(28);
        avlTreeObj.add(28);
        avlTreeObj.add(8);
        avlTreeObj.add(30);
        avlTreeObj.add(31);
        avlTreeObj.add(34);
        */

        System.out.println(bstObj.toString2());
        System.out.println(avlTreeObj.toString2());
    }
}
