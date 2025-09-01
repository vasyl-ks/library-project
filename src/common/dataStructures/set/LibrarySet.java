package common.dataStructures.set;

import common.dataStructures.list.LibraryListWithPI;
import java.util.stream.Stream;

/**
 * Defines the contract for a Set (mathematical set) data structure,
 * where elements are unique (no duplicates allowed).
 *
 * @param <E> the type of elements maintained in this set
 */
public interface LibrarySet<E> extends Iterable<E> {

    /**
     * Adds an element to the set.
     *
     * @param element the element to add
     * @return true if the element was successfully added, false if it was already present
     */
    boolean add(E element);

    /**
     * Removes an element from the set.
     *
     * @param element the element to remove
     * @return true if the element was successfully removed, false if it was not present
     */
    boolean remove(E element);

    /**
     * Checks if an element exists in the set.
     *
     * @param element the element to check
     * @return true if the element is present, false otherwise
     */
    boolean contains(E element);

    /**
     * Returns the number of elements in the set.
     *
     * @return the size of the set
     */
    int size();

    /**
     * Checks whether the set is empty.
     *
     * @return true if the set contains no elements, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns a list containing all the elements in the set.
     *
     * @return a {@link LibraryListWithPI} with all elements
     */
    LibraryListWithPI<E> elements();

    /**
     * Returns a sequential {@link Stream} with the elements of the set.
     *
     * @return a stream over the elements in this set
     */
    Stream<E> stream();
}