package components;

import com.sun.istack.internal.Nullable;
import org.jetbrains.annotations.NotNull;

/**
 * Helper class for {@link LRUCache} to support insertion to the front and removing from any place in O(1).
 */
class DoublyConnectedList<K, V> {

    @NotNull
    private final Node<K, V> head;

    @NotNull
    private final Node<K, V> tail;

    private int size;

    DoublyConnectedList() {
        this.head = new Node<>(null, null);
        this.tail = new Node<>(null, null);
        this.head.next = this.tail;
        this.tail.prev = this.head;
        this.size = 0;
    }

    /**
     * Push new entity to the front of the list.
     */
    void addFirst(Node<K, V> node) {
        // pre: true
        int initialSize = this.size;

        node.prev = this.head;
        node.next = this.head.next;

        node.next.prev = node;
        node.prev.next = node;

        this.size++;

        // post:
        assert getFirst() == node;
        assert this.size == initialSize + 1;
    }

    /**
     * Remove last element of the list. Return value of last element.
     */
    Node<K, V> removeLast() {
        Node<K, V> last = getLast();
        remove(getLast());
        return last;
    }

    /**
     * Remove Node from the list in O(1).
     */
    void remove(@NotNull Node<K, V> toRemove) {
        // pre:
        assert this.size > 0 && this.head != toRemove && this.tail != toRemove;
        assert toRemove.prev != null && toRemove.next != null;
        int initialSize = this.size;

        toRemove.next.prev = toRemove.prev;
        toRemove.prev.next = toRemove.next;
        toRemove.next = null;
        toRemove.prev = null;
        this.size--;

        // post:
        assert this.size == initialSize - 1;
    }

    /**
     * Return current number of entities in the list in O(1).
     */
    int size() {
        return this.size;
    }

    /**
     * Delete all entities from list.
     */
    void clear() {
        this.head.next = this.tail;
        this.tail.prev = this.head;
        this.size = 0;
    }

    /**
     * Returns first element of list.
     */
    Node<K, V> getFirst() {
        // pre:
        assert this.size > 0;
        int oldSize = this.size;

        Node<K, V> first = this.head.next;

        // post:
        assert oldSize == this.size;
        return first;
    }


    /**
     * Returns last element of list.
     */
    Node<K, V> getLast() {
        // pre:
        assert this.size > 0;
        int oldSize = this.size;

        Node<K, V> last = this.tail.prev;

        // post:
        assert oldSize == this.size;
        return last;
    }

    /**
     * Node entry for the doubly connected list.
     */
    static final class Node<K, V> {

        private final K key;

        @Nullable
        private final V value;

        @Nullable
        private Node<K, V> prev;

        @Nullable
        private Node<K, V> next;

        Node(@Nullable K key, @Nullable V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}
