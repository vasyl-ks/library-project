package common.dataStructures.set.impl;

import common.dataStructures.map.impl.HashLibraryMap;
import common.dataStructures.list.LibraryListWithPI;
import common.dataStructures.list.impl.SLLLibraryListWithPI;
import common.dataStructures.set.LibrarySet;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Hash-based implementation of {@link LibrarySet}.
 * <p>
 * This implementation internally uses a {@link HashLibraryMap}
 * where each element is stored as a key mapped to a dummy value.
 * It ensures uniqueness of elements and provides efficient operations.
 *
 * @param <E> the type of elements maintained in this set
 */
public class HashLibrarySet<E> implements LibrarySet<E> {

    /** Placeholder value used for the map, since only keys are relevant. */
    private static final Object DUMMY = new Object();

    /** Internal hash map that stores the elements of the set. */
    private final HashLibraryMap<E, Object> table;

    /**
     * Creates an empty set with a given estimated initial capacity.
     *
     * @param estimatedSize the estimated number of elements the set will hold
     */
    public HashLibrarySet(int estimatedSize) {
        table = new HashLibraryMap<>(estimatedSize);
    }

    /** {@inheritDoc} */
    @Override
    public boolean add(E element) {
        Object old = table.put(element, DUMMY);
        return old == null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean remove(E element) {
        Object old = table.remove(element);
        return old != null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean contains(E element) {
        return table.get(element) != null;
    }

    /** {@inheritDoc} */
    @Override
    public int size() {
        return table.size();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isEmpty() {
        return table.isEmpty();
    }

    /** {@inheritDoc} */
    @Override
    public LibraryListWithPI<E> elements() {
        LibraryListWithPI<E> list = new SLLLibraryListWithPI<>();
        LibraryListWithPI<E> keys = table.keys();
        keys.start();
        while (!keys.isEnd()) {
            list.insert(keys.get());
            keys.next();
        }
        return list;
    }

    /** {@inheritDoc} */
    @Override
    public void forEach(Consumer<? super E> action) {
        LibraryListWithPI<E> keysList = elements();
        keysList.start();
        while (!keysList.isEnd()) {
            action.accept(keysList.get());
            keysList.next();
        }
    }

    /** {@inheritDoc} */
    @Override
    public Iterator<E> iterator() {
        LibraryListWithPI<E> keysList = elements();
        return new Iterator<>() {
            private final LibraryListWithPI<E> list = keysList;
            {
                list.start();
            }

            @Override
            public boolean hasNext() {
                return !list.isEnd();
            }

            @Override
            public E next() {
                E elem = list.get();
                list.next();
                return elem;
            }
        };
    }

    /** {@inheritDoc} */
    @Override
    public Stream<E> stream() {
        LibraryListWithPI<E> keysList = elements();
        Stream.Builder<E> builder = Stream.builder();
        keysList.start();
        while (!keysList.isEnd()) {
            builder.add(keysList.get());
            keysList.next();
        }
        return builder.build();
    }

    /**
     * Returns a string representation of the set, showing its elements.
     *
     * @return a string containing all elements in the set
     */
    @Override
    public String toString() {
        return elements().toString();
    }
}