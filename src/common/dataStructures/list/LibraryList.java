package common.dataStructures.list;

import java.util.stream.IntStream;

/**
 * Interface representing a generic list of elements.
 * Provides methods for insertion, removal, retrieval, and size queries.
 *
 * @param <E> the type of elements in the list
 */
public interface LibraryList<E> extends Iterable<E> {

    /**
     * Inserts the element {@code e} at position {@code i} in the list.
     *
     * @param e the element to insert
     * @param i the position where the element should be inserted
     *          (0 <= i <= size())
     */
    void insert(E e, int i);

    /**
     * Removes the element at position {@code i} from the list.
     *
     * @param i the position of the element to remove (0 <= i < size())
     */
    void remove(int i);

    /**
     * Returns the element at position {@code i} in the list.
     *
     * @param i the index of the element to retrieve (0 <= i < size())
     * @return the element at the specified position
     */
    E get(int i);

    /**
     * Checks whether the list is empty.
     *
     * @return {@code true} if the list contains no elements, {@code false} otherwise
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in the list.
     *
     * @return the size of the list
     */
    int size();

    /**
     * Returns an {@link IntStream} of the list elements cast to int.
     * Only valid if elements are instances of {@link Number}.
     *
     * @return an IntStream of list elements
     * @throws ClassCastException if an element is not a Number
     */
    IntStream stream();
}
