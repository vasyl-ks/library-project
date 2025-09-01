package common.dataStructures.queue.impl;

import common.dataStructures.queue.LibraryQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * Array-based circular implementation of {@link LibraryQueue}.
 * <p>
 * Elements are maintained in a circular array to efficiently manage
 * enqueue and dequeue operations. The array is automatically doubled
 * when capacity is exceeded.
 *
 * @param <E> the type of elements held in this queue
 */
public class ArrayLibraryQueue<E> implements LibraryQueue<E> {

    /** Default initial capacity for the queue. */
    protected static final int DEFAULT_CAPACITY = 50;

    /** The circular array storing queue elements. */
    protected E[] theArray;

    /** Index of the front element of the queue. */
    protected int front;

    /** Index of the next available rear position. */
    protected int rear;

    /** Current number of elements in the queue. */
    protected int size;

    /**
     * Constructs an empty queue with default capacity.
     */
    @SuppressWarnings("unchecked")
    public ArrayLibraryQueue() {
        theArray = (E[]) new Object[DEFAULT_CAPACITY];
        front = 0;
        rear = 0;
        size = 0;
    }

    /** {@inheritDoc} */
    @Override
    public void add(E e) {
        if (size == theArray.length) doubleCircularArray();
        theArray[rear] = e;
        rear = increment(rear);
        size++;
    }

    /**
     * Doubles the current capacity of the circular array,
     * preserving the order of existing elements.
     */
    @SuppressWarnings("unchecked")
    protected void doubleCircularArray() {
        E[] newArray = (E[]) new Object[theArray.length * 2];
        for (int i = 0; i < size; i++, front = increment(front)) {
            newArray[i] = theArray[front];
        }
        theArray = newArray;
        front = 0;
        rear = size;
    }

    /** Circularly increments an index in the array. */
    protected int increment(int index) {
        if (++index == theArray.length) index = 0;
        return index;
    }

    /** {@inheritDoc} */
    @Override
    public E remove() {
        E first = theArray[front];
        theArray[front] = null;
        front = increment(front);
        size--;
        return first;
    }

    /** {@inheritDoc} */
    @Override
    public E first() {
        return theArray[front];
    }

    /** {@inheritDoc} */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /** {@inheritDoc} */
    @Override
    public boolean contains(E e) {
        int aux = front;
        for (int i = 0; i < size; i++, aux = increment(aux)) {
            if (theArray[aux] == null) {
                if (e == null) return true;
            } else if (theArray[aux].equals(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a string representation of the queue in FIFO order.
     *
     * @return a string containing all elements of the queue
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("[");
        int aux = front;
        for (int i = 0, j = size - 1; i < j; i++, aux = increment(aux)) {
            res.append(theArray[aux].toString()).append(", ");
        }
        if (size != 0) res.append(theArray[aux].toString()).append("]");
        else res.append("]");
        return res.toString();
    }

    /** {@inheritDoc} */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int index = front;
            private int count = 0;

            @Override
            public boolean hasNext() {
                return count < size;
            }

            @Override
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                E element = theArray[index];
                index = increment(index);
                count++;
                return element;
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public void forEach(Consumer<? super E> action) {
        int index = front;
        for (int i = 0; i < size; i++, index = increment(index)) {
            action.accept(theArray[index]);
        }
    }
}