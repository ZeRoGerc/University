package components;

import components.DoublyConnectedList.Node;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * A fixed-capacity cache that stores most recent objects.
 * Oldest entities will be removed when cache reaches capacity.
 * @param <K> type of keys to get values by.
 * @param <V> type of values that will be stored in cache.
 */
public class LRUCache<K, V> {

    private final int capacity;

    @NotNull
    private final DoublyConnectedList<K, V> nodesList;

    @NotNull
    private final Map<K, Node<K, V>> cache;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.nodesList = new DoublyConnectedList<>();
        this.cache = new HashMap<>();
    }

    /**
     * Put new value in cache. You can get it latter using {@link #get(Object)}.
     * Will remove oldest value from cache if capacity reached.
     * @param key key to associate value with.
     * @param value value to store.
     */
    public void put(@NotNull K key, @NotNull V value) {
        // pre:
        assert invariant();

        removeIfCached(key);
        ensureCapacityForPut();

        Node<K, V> node = new Node<>(key, value);
        this.nodesList.addFirst(node);
        this.cache.put(key, node);

        // post:
        assert invariant();
        assert this.cache.containsKey(key);
        assert this.nodesList.getFirst().equals(node);
    }

    /**
     * Get value from cache by key. Will return value if it's cached. Will return empty otherwise.
     * @param key key to get value by.
     * @return cached value or empty if not cached.
     */
    @NotNull
    public Optional<V> get(@NotNull K key) {
        // pre:
        assert invariant();

        if (!this.cache.containsKey(key)) {
            return Optional.empty();
        }

        Node<K, V> node = this.cache.get(key);
        this.nodesList.remove(node);
        this.nodesList.addFirst(node);

        // post:
        assert invariant();
        assert this.cache.containsKey(key);
        assert this.nodesList.getFirst().equals(node);

        return Optional.of(node.getValue());
    }

    /**
     * Clear all values stored in cache.
     */
    public void clear() {
        // pre:
        assert invariant();

        this.nodesList.clear();
        this.cache.clear();

        // post:
        assert this.nodesList.size() == 0;
        assert this.cache.size() == 0;
    }

    /**
     * Look for a key and remove it from cache if found.
     */
    private void removeIfCached(K key) {
        // pre:
        assert invariant();

        if (this.cache.containsKey(key)) {
            Node<K, V> node = this.cache.get(key);
            this.nodesList.remove(node);
            this.cache.remove(key);
        }

        // post:
        assert invariant();
        assert !this.cache.containsKey(key);
    }

    /**
     * Will remove one element if capacity reached.
     */
    private void ensureCapacityForPut() {
        // pre:
        assert invariant();

        if (this.nodesList.size() == this.capacity) {
            Node<K, V> node = this.nodesList.removeLast();
            this.cache.remove(node.getKey());
        }

        // post:
        assert invariant();
        assert this.nodesList.size() < this.capacity;
        assert this.cache.size() < this.capacity;
    }

    private boolean invariant() {
        return this.cache.size() == this.nodesList.size() && this.nodesList.size() <= this.capacity;
    }

}
