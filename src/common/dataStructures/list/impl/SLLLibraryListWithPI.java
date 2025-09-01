package common.dataStructures.list.impl;

import common.dataStructures.list.LibraryListWithPI;

import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * Implements a ListWithPI using a singly linked list (SLL) with a dummy header node.
 *
 * <p>Maintains:
 * <ul>
 *   <li>A reference to the first node (dummy header)</li>
 *   <li>A reference to the last node</li>
 *   <li>A reference to the node preceding the Point of Interest (PI)</li>
 *   <li>An int size representing the number of elements</li>
 * </ul>
 *
 * @param <E> the type of elements stored in the list
 */
public class SLLLibraryListWithPI<E> implements LibraryListWithPI<E> {

    private final SLLNode<E> first;
    private SLLNode<E> prev;
    private SLLNode<E> last;
    protected int size;

    /**
     * Constructs an empty singly-linked ListWithPI.
     * The list contains only the dummy header node and has size 0.
     */
    public SLLLibraryListWithPI() {
        first = last = prev = new SLLNode<>(null);
        size = 0;
    }

    /**
     * Constructs a new ListWithPI by copying all elements from another list.
     * The PI of the new list starts at the beginning.
     *
     * @param other another LibraryListWithPI to copy elements from
     */
    public SLLLibraryListWithPI(LibraryListWithPI<? extends E> other) {
        this();
        other.start();
        while (!other.isEnd()) {
            insert(other.get());
            other.next();
        }
    }

    /**
     * Inserts a new element into the list before the element at the PI.
     * After insertion, the PI points to the newly inserted element.
     *
     * @param e the element to insert
     */
    public void insert(E e) {
        SLLNode<E> newNode = new SLLNode<>(e, prev.next);
        prev.next = newNode;
        if (newNode.next == null) last = newNode;
        prev = prev.next;
        size++;
    }

    /**
     * Removes the element at the PI if the PI is not at the end.
     * After removal, the PI remains at the same logical position.
     * If the removed element was the last element, updates the last reference.
     */
    public void remove() {
        size--;
        if (prev.next == last) last = prev;
        prev.next = prev.next.next;
    }

    /**
     * Moves the PI to the beginning of the list, before the first element.
     * After calling this, the next element accessed by get() is the first element.
     */
    public void start() { prev = first; }

    /**
     * Moves the PI forward by one element if it is not at the end.
     * After calling this, the PI points to the next element in the list.
     */
    public void next() { prev = prev.next; }

    /**
     * Moves the PI to the end of the list, after the last element.
     * This allows insertions to append to the list.
     */
    public void end() { prev = last; }

    /**
     * Returns the element at the PI if it is not at the end.
     *
     * @return the element at the PI
     * @throws NullPointerException if called when PI is at the end
     */
    public E get() { return prev.next.data; }

    /**
     * Checks if the PI is at the end of the list.
     *
     * @return true if the PI is after the last element, false otherwise
     */
    public boolean isEnd() { return prev == last; }

    /**
     * Checks if the list is empty.
     *
     * @return true if the list contains no elements, false otherwise
     */
    public boolean isEmpty() { return first == last; }

    /**
     * Returns the number of elements in the list.
     *
     * @return the current size of the list
     */
    public int size() { return size; }

    /**
     * Performs the given action for each element in the list in order.
     *
     * @param action a Consumer that processes each element
     */
    public void forEach(Consumer<? super E> action) {
        SLLNode<E> current = first.next;
        while(current != null) {
            action.accept(current.data);
            current = current.next;
        }
    }

    /**
     * Returns an iterator over the elements in the list in proper sequence.
     * This allows the list to be used in enhanced for-each loops.
     *
     * @return an Iterator over the elements
     */
    public java.util.Iterator<E> iterator() {
        return new java.util.Iterator<>() {
            private SLLNode<E> current = first.next; // start after dummy header

            @Override
            public boolean hasNext() { return current != null; }

            @Override
            public E next() {
                if (!hasNext()) throw new java.util.NoSuchElementException();
                E data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    /**
     * Returns a string representation of the list in insertion order,
     * using the standard Java array format.
     *
     * @return a string containing the elements of the list
     */
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        SLLNode<E> current = first.next;
        for (int i = 1; i < size; i++, current = current.next) {
            s.append(current.data.toString()).append(", ");
        }
        if (size != 0) s.append(current.data.toString()).append("]");
        else s.append("]");
        return s.toString();
    }

    /**
     * Returns an IntStream of the elements in the list.
     * <p>Each element is converted to int using a cast. Only valid for Number types.</p>
     *
     * @return an IntStream of elements in the list
     * @throws ClassCastException if an element is not a Number
     */
    public IntStream stream() {
        IntStream.Builder builder = IntStream.builder();
        SLLNode<E> current = first.next;
        while (current != null) {
            builder.add(((Number) current.data).intValue());
            current = current.next;
        }
        return builder.build();
    }
}