package common.dataStructures.map.impl;

/**
 * Represents a single entry (key-value pair) in a hash table.
 * <p>
 * Used internally by {@link HashLibraryMap} as the node stored
 * in each bucket. This class is package-private and should not
 * be accessed directly outside the map implementation.
 *
 * @param <K> the type of keys maintained by the map
 * @param <V> the type of mapped values
 */
class HashLibraryEntry<K, V> {

    /**
     * The key of the map entry.
     */
    protected K key;

    /**
     * The value associated with the key.
     */
    protected V value;

    /**
     * Constructs a new hash table entry with the specified key and value.
     *
     * @param k the key of the entry
     * @param v the value associated with the key
     */
    HashLibraryEntry(K k, V v) {
        key = k;
        value = v;
    }

    /**
     * Returns a string representation of the entry in the form "(key, value)".
     *
     * @return a string representing the key-value pair
     */
    @Override
    public String toString() {
        return "(" + key + ", " + value + ")";
    }
}
