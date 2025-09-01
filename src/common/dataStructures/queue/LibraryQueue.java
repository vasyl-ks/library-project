package common.dataStructures.queue;

import java.util.function.Consumer;

/**
 * Defines the contract for a Queue (FIFO) data structure,
 * where elements are processed in insertion order.
 *
 * @param <E> the type of elements held in this queue
 */
public interface LibraryQueue<E> extends Iterable<E> {

    /**
     * Inserts the specified element into the queue at the rear.
     *
     * @param e the element to add
     */
    void add(E e);

    /**
     * Retrieves and removes the element at the front of the queue.
     * <p>
     * Precondition: the queue must not be empty.
     *
     * @return the element at the front of the queue
     */
    E remove();

    /**
     * Retrieves, without removing, the element at the front of the queue.
     * <p>
     * Precondition: the queue must not be empty.
     *
     * @return the front element of the queue
     */
    E first();

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue contains no elements, false otherwise
     */
    boolean isEmpty();

    /**
     * Checks whether the queue contains the specified element.
     *
     * @param e the element to check
     * @return true if the element is present in the queue, false otherwise
     */
    boolean contains(E e);

    /**
     * Executes the given action for each element in the queue,
     * in FIFO (insertion) order.
     *
     * @param action a Consumer that performs an operation on each element
     */
    void forEach(Consumer<? super E> action);
}