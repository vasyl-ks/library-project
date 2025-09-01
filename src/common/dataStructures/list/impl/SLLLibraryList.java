package common.dataStructures.list.impl;

import common.dataStructures.list.LibraryList;
import common.dataStructures.list.LibraryListWithPI;

import java.util.stream.IntStream;

/**
 * Implementation of a singly linked list (SLL) using nodes of type {@link SLLNode}.
 * Supports insertion, removal, retrieval, and iteration over elements.
 *
 * @param <E> the type of elements in the list
 */
public class SLLLibraryList<E> implements LibraryList<E>, Iterable<E> {

    /** Reference to the first node of the list */
    private SLLNode<E> head;

    /** Number of elements in the list */
    protected int size;

    /** Constructs an empty singly linked list */
    public SLLLibraryList() {
        head = null;
        size = 0;
    }

    /** Constructs a list by copying elements from another {@link LibraryList} */
    public SLLLibraryList(LibraryList<? extends E> other) {
        this();
        for (int i = 0; i < other.size(); i++) {
            insert(other.get(i), size);
        }
    }

    /** Constructs a list by copying elements from a {@link LibraryListWithPI} */
    public SLLLibraryList(LibraryListWithPI<? extends E> other) {
        this();
        other.start();
        while (!other.isEnd()) {
            insert(other.get(), size);
            other.next();
        }
    }

    /**
     * Inserts an element at a given index.
     *
     * @param e the element to insert
     * @param i the index at which to insert the element (0 <= i <= size)
     */
    public void insert(E e, int i) {
        SLLNode<E> newNode = new SLLNode<>(e);
        size++;
        SLLNode<E> current = head;
        SLLNode<E> prev = null;
        for (int j = 0; j < i; j++) {
            prev = current;
            current = current.next;
        }
        newNode.next = current;
        if (prev == null) head = newNode;
        else prev.next = newNode;
    }

    /**
     * Removes the element at the specified index.
     *
     * @param i the index of the element to remove (0 <= i < size)
     */
    public void remove(int i) {
        size--;
        SLLNode<E> current = head;
        SLLNode<E> prev = null;
        for (int j = 0; j < i; j++) {
            prev = current;
            current = current.next;
        }
        if (prev == null) head = current.next;
        else prev.next = current.next;
    }

    /**
     * Returns the element at a specific index.
     *
     * @param i the index of the element (0 <= i < size)
     * @return the element at the specified position
     */
    public E get(int i) {
        SLLNode<E> current;
        int j;
        for (current = head, j = 0; j < i; current = current.next, j++);
        return current.data;
    }

    /** Returns {@code true} if the list contains no elements */
    public boolean isEmpty() {
        return head == null;
    }

    /** Returns the number of elements in the list */
    public int size() {
        return size;
    }

    /**
     * Returns an iterator over the elements in the list.
     * Enables use of enhanced for-loops.
     *
     * @return an Iterator over the elements in this list
     */
    @Override
    public java.util.Iterator<E> iterator() {
        return new java.util.Iterator<>() {
            private SLLNode<E> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    /**
     * Returns an IntStream of the elements in the list.
     * Elements must be castable to {@link Number}.
     *
     * @return an IntStream of the list elements
     * @throws ClassCastException if an element is not a Number
     */
    public IntStream stream() {
        IntStream.Builder builder = IntStream.builder();
        SLLNode<E> current = head;
        while (current != null) {
            builder.add(((Number) current.data).intValue());
            current = current.next;
        }
        return builder.build();
    }

    /**
     * Returns a string representation of the list in standard Java format.
     * Example: [1, 2, 3] for a list of integers.
     *
     * @return a string representing the list elements in order
     */
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("[");
        if (size == 0) return res.append("]").toString();
        SLLNode<E> current = head;
        for (int i = 0, j = size - 1; i < j; i++, current = current.next)
            res.append(current.data.toString()).append(", ");
        res.append(current.data.toString()).append("]");
        return res.toString();
    }
}