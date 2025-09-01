package common.dataStructures.map;

import common.dataStructures.list.LibraryListWithPI;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Interface for a generic map / dictionary.
 *
 * @param <K> Key type
 * @param <V> Value type
 */
public interface LibraryMap<K, V> {

    /** Inserts or updates the entry (k, v). Returns old value or null if not present */
    V put(K k, V v);

    /** Returns the value associated with key k, or null if not present */
    V get(K k);

    /** Removes the entry with key k and returns its value, or null if not present */
    V remove(K k);

    /** Checks if the map is empty */
    boolean isEmpty();

    /** Returns the number of entries in the map */
    int size();

    /** Checks if the HashTable contains a specific key. */
    boolean containsKey(K key);

    /** Returns a list of all keys */
    LibraryListWithPI<K> keys();

    /** Returns a list of all values */
    LibraryListWithPI<V> values();

    /**
     * If key k is present, compute a new value using the provided function
     * and update the map with it. Returns the new value or null if key not present.
     */
    V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction);

    /**
     * If key k is absent, compute a new value using the provided function
     * and update the map with it. Returns the new value or null if key not absent.
     */
    V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction);

    /**
     * Performs the given action for each key-value entry in the map.
     */
    void forEach(BiConsumer<? super K, ? super V> action);
}