package hashmap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    int numElement;
    int capacity;
    double loadFactor;

    /** Constructors */
    public MyHashMap() {
        this(16);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.numElement = 0;
        this.capacity = initialCapacity;
        this.loadFactor = loadFactor;
        this.buckets = new Collection[this.capacity];

        for (int i = 0; i < this.capacity; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<Node>();
    }

    @Override
    public Iterator<K> iterator() {
        return this.keySet().iterator();
    }

    @Override
    public void put(K key, V value) {
        if (this.containsKey(key)) {
            for (Node node : this.buckets[Math.floorMod(key.hashCode(), this.capacity)]) {
                if (node.key.equals(key)) {
                    node.value = value;
                }
            }
        } else {
            this.buckets[Math.floorMod(key.hashCode(), this.capacity)].add(new Node(key, value));
            this.numElement += 1;
        }

        if (1.0 * this.numElement / this.capacity > this.loadFactor) {
            this.capacity *= 2;
            Collection<Node>[] newBuckets = new Collection[this.capacity];

            for (int i = 0; i < this.capacity; i++) {
                newBuckets[i] = createBucket();
            }

            for (Collection<Node> collection : this.buckets) {
                for (Node node : collection) {
                    newBuckets[Math.floorMod(node.key.hashCode(), this.capacity)].add(node);
                }
            }

            this.buckets = newBuckets;
        }
    }

    @Override
    public V get(K key) {
        for (Node node : this.buckets[Math.floorMod(key.hashCode(), this.capacity)]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;

    }

    @Override
    public boolean containsKey(K key) {
        for (Node node : this.buckets[Math.floorMod(key.hashCode(), this.capacity)]) {
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return this.numElement;
    }

    @Override
    public void clear() {
        this.numElement = 0;

        for (int i = 0; i < this.capacity; i++) {
            this.buckets[i] = createBucket();
        }
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> keys = new HashSet<>();
        for (Collection<Node> collection : this.buckets) {
            for (Node node : collection) {
                keys.add(node.key);
            }
        }
        return keys;
    }

    @Override
    public V remove(K key) {
        for (Node node : this.buckets[Math.floorMod(key.hashCode(), this.capacity)]) {
            if (node.key.equals(key)) {
                this.buckets[Math.floorMod(key.hashCode(), this.capacity)].remove(node);
                return node.value;
            }
        }
        return null;
    }
}
