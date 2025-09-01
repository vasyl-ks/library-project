package common.dataStructures.list;

import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * Interface representing a List with a Point of Interest (PI).
 *
 * <p>A ListWithPI is a sequential-access collection where a cursor-like
 * Point of Interest can be used to traverse, insert, or remove elements.</p>
 *
 * @param <E> the type of elements in the list
 */
public interface LibraryListWithPI<E> extends Iterable<E> {

    /**
     * Inserts an element into the list before the element at the PI.
     * The PI itself remains unchanged.
     *
     * @param e the element to insert
     */
    void insert(E e);

    /**
     * Removes the element at the PI if the PI is not at the end.
     * The PI remains unchanged.
     */
    void remove();

    /**
     * Moves the PI to the start of the list, before the first element.
     */
    void start();

    /**
     * Moves the PI forward to the next element if it is not at the end.
     */
    void next();

    /**
     * Moves the PI to the end of the list, after the last element.
     */
    void end();

    /**
     * Returns the element at the PI if it is not at the end.
     *
     * @return the element at the PI
     */
    E get();

    /**
     * Checks whether the PI is currently at the end of the list.
     *
     * @return true if the PI is at the end, false otherwise
     */
    boolean isEnd();

    /**
     * Checks whether the list is empty.
     *
     * @return true if the list contains no elements, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in the list.
     *
     * @return the size of the list
     */
    int size();

    /**
     * Performs the given action for each element in the list, in order.
     *
     * @param action a Consumer that processes each element
     */
    void forEach(Consumer<? super E> action);

    /**
     * Returns a sequential {@code IntStream} of elements in the list.
     * <p>Only valid if elements are instances of {@link Number}.</p>
     *
     * @return an IntStream of elements in the list
     * @throws ClassCastException if an element is not a Number
     */
    IntStream stream();
}