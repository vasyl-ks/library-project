package common.dataStructures.map.impl;

import common.dataStructures.list.impl.SLLLibraryListWithPI;
import common.dataStructures.list.LibraryListWithPI;
import common.dataStructures.map.LibraryMap;

import java.util.function.*;

/**
 * Implementation of a generic chained hash table using lists without rehashing.
 * <p>
 * Each bucket is a {@link LibraryListWithPI} of {@link HashLibraryEntry} objects.
 * The table does not automatically resize or rehash, so its capacity should be
 * chosen carefully at construction.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class HashLibraryMap<K, V> implements LibraryMap<K, V> {

    /** Default load factor, following java.util.HashMap convention. */
    public static final double LOAD_FACTOR = 0.75;

    /** Array of buckets. Each bucket is a list of HashLibraryEntry objects. */
    private final LibraryListWithPI<HashLibraryEntry<K,V>>[] buckets;

    /** The total number of entries in the hash table. */
    protected int size;

    /**
     * Returns the bucket index for the given key.
     *
     * @param k the key
     * @return the index of the bucket in which the key belongs
     */
    protected int hashIndex(K k) {
        int index = k.hashCode() % buckets.length;
        if (index < 0) index += buckets.length;
        return index;
    }

    /**
     * Constructs a new hash table with capacity based on the estimated size.
     *
     * @param estimatedSize the expected number of entries
     */
    @SuppressWarnings("unchecked")
    public HashLibraryMap(int estimatedSize) {
        int capacity = nextPrime((int) (estimatedSize / LOAD_FACTOR));
        buckets = new SLLLibraryListWithPI[capacity];
        for (int i = 0; i < buckets.length; i++)
            buckets[i] = new SLLLibraryListWithPI<>();
        size = 0;
    }

    /**
     * Returns the next prime number greater than or equal to n.
     *
     * @param n the starting number
     * @return the next prime number >= n
     */
    protected static int nextPrime(int n) {
        if (n == 2) return n;
        if (n % 2 == 0) n++;
        for (; !isPrime(n); n += 2);
        return n;
    }

    /** Checks if a number is prime. */
    protected static boolean isPrime(int n) {
        for (int i = 3 ; i * i <= n; i += 2)
            if (n % i == 0) return false;
        return true;
    }

    /** {@inheritDoc} */
    public V get(K k) {
        int pos = hashIndex(k);
        LibraryListWithPI<HashLibraryEntry<K,V>> bucket = buckets[pos];
        V value = null;
        bucket.start();
        while(!bucket.isEnd() && !bucket.get().key.equals(k))
            bucket.next();
        if(!bucket.isEnd())
            value = bucket.get().value;
        return value;
    }

    /** {@inheritDoc} */
    public V remove(K k) {
        int pos = hashIndex(k);
        LibraryListWithPI<HashLibraryEntry<K,V>> bucket = buckets[pos];
        V value = null;
        bucket.start();
        while(!bucket.isEnd() && !bucket.get().key.equals(k))
            bucket.next();
        if(!bucket.isEnd()) {
            value = bucket.get().value;
            bucket.remove();
            size--;
        }
        return value;
    }

    /** {@inheritDoc} */
    public V put(K k, V v) {
        int pos = hashIndex(k);
        LibraryListWithPI<HashLibraryEntry<K,V>> bucket = buckets[pos];
        V oldValue = null;
        bucket.start();
        while(!bucket.isEnd() && !bucket.get().key.equals(k))
            bucket.next();
        if(!bucket.isEnd()) {
            oldValue = bucket.get().value;
            bucket.get().value = v;
        } else {
            bucket.insert(new HashLibraryEntry<>(k, v));
            size++;
        }
        return oldValue;
    }

    /** {@inheritDoc} */
    public boolean isEmpty() { return size == 0; }

    /** {@inheritDoc} */
    public int size() { return size; }

    /** {@inheritDoc} */
    public boolean containsKey(K key) {
        int pos = hashIndex(key);
        LibraryListWithPI<HashLibraryEntry<K,V>> bucket = buckets[pos];
        bucket.start();
        while (!bucket.isEnd()) {
            if (bucket.get().key.equals(key)) return true;
            bucket.next();
        }
        return false;
    }

    /** {@inheritDoc} */
    public LibraryListWithPI<K> keys() {
        LibraryListWithPI<K> list = new SLLLibraryListWithPI<>();
        for(LibraryListWithPI<HashLibraryEntry<K,V>> bucket : buckets) {
            bucket.start();
            while(!bucket.isEnd()) {
                list.insert(bucket.get().key);
                bucket.next();
            }
        }
        return list;
    }

    /** {@inheritDoc} */
    public LibraryListWithPI<V> values() {
        LibraryListWithPI<V> list = new SLLLibraryListWithPI<>();
        for(LibraryListWithPI<HashLibraryEntry<K,V>> bucket : buckets) {
            bucket.start();
            while(!bucket.isEnd()) {
                list.insert(bucket.get().value);
                bucket.next();
            }
        }
        return list;
    }

    /** {@inheritDoc} */
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        V currentValue = get(key);
        if (currentValue != null) {
            V newValue = remappingFunction.apply(key, currentValue);
            if (newValue != null) put(key, newValue);
            else remove(key);
            return newValue;
        }
        return null;
    }

    /** {@inheritDoc} */
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        V currentValue = get(key);
        if (currentValue == null) {
            V newValue = mappingFunction.apply(key);
            if (newValue != null) put(key, newValue);
            return newValue;
        }
        return currentValue;
    }

    /**
     * Returns the current load factor of the table.
     *
     * @return the load factor
     */
    public final double loadFactor() {
        return (double) size / buckets.length;
    }

    /**
     * Returns the number of collisions in the bucket corresponding to key k.
     *
     * @param k the key whose bucket to check
     * @return the number of entries in the bucket
     */
    public int numberOfCollisions(K k) {
        int pos = hashIndex(k), collisions = 0;
        LibraryListWithPI<HashLibraryEntry<K,V>> bucket = buckets[pos];
        bucket.start();
        while(!bucket.isEnd()) {
            collisions++;
            bucket.next();
        }
        return collisions;
    }

    /**
     * Returns the keys for which the value equals the specified value.
     *
     * @param v the value to search for
     * @return a list of keys associated with the value
     */
    public LibraryListWithPI<K> keysWithValue(V v) {
        LibraryListWithPI<K> list = new SLLLibraryListWithPI<>();
        for(LibraryListWithPI<HashLibraryEntry<K,V>> bucket : buckets) {
            bucket.start();
            while(!bucket.isEnd()) {
                if(bucket.get().value.equals(v))
                    list.insert(bucket.get().key);
                bucket.next();
            }
        }
        return list;
    }

    /** {@inheritDoc} */
    public void forEach(BiConsumer<? super K, ? super V> action) {
        for(LibraryListWithPI<HashLibraryEntry<K,V>> bucket : buckets) {
            bucket.start();
            while(!bucket.isEnd()) {
                HashLibraryEntry<K,V> e = bucket.get();
                action.accept(e.key, e.value);
                bucket.next();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(LibraryListWithPI<HashLibraryEntry<K, V>> bucket : buckets) {
            bucket.start();
            while(!bucket.isEnd()) {
                res.append(bucket.get().toString()).append("\n");
                bucket.next();
            }
        }
        return res.toString();
    }
}