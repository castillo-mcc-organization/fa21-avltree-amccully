package edu.miracosta.cs113;

public interface SearchTree<E> {
    E delete(E stringDatum);

    E find(E stringDatum);

    boolean contains(E stringDatum);

    boolean remove(E stringDatum);

    boolean add(E s);
}
