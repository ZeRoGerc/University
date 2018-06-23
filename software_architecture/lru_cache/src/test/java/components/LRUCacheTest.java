package components;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class LRUCacheTest {

    @Test
    public void testPut_shouldNotThrow() throws Exception {
        LRUCache<String, Integer> cache = new LRUCache<>(5);

        cache.put("key", 1);
    }

    @Test
    public void testGet_shouldReturnValueIfPresent() throws Exception {
        LRUCache<String, Integer> cache = new LRUCache<>(5);

        cache.put("key", 1);

        assertEquals(cache.get("key"), Optional.of(1));
    }

    @Test
    public void testGet_shouldReturnEmptyIfNotPresent() throws Exception {
        LRUCache<String, Integer> cache = new LRUCache<>(5);

        assertEquals(cache.get("key"), Optional.empty());
    }

    @Test
    public void testCapacity_shouldRemoveOldestPutOnly() throws Exception {
        LRUCache<String, Integer> cache = new LRUCache<>(2);

        cache.put("key1", 1);
        cache.put("key2", 2);
        cache.put("key3", 3);

        assertEquals(cache.get("key1"), Optional.empty());
        assertEquals(cache.get("key2"), Optional.of(2));
        assertEquals(cache.get("key3"), Optional.of(3));
    }


    @Test
    public void testCapacity_shouldRemoveOldestPutAndGet() throws Exception {
        LRUCache<String, Integer> cache = new LRUCache<>(2);

        cache.put("key1", 1);
        cache.put("key2", 2);
        cache.get("key1");
        cache.put("key3", 3);

        assertEquals(cache.get("key1"), Optional.of(1));
        assertEquals(cache.get("key2"), Optional.empty());
        assertEquals(cache.get("key3"), Optional.of(3));
    }

    @Test
    public void testClear_shouldClearAll() throws Exception {
        LRUCache<String, Integer> cache = new LRUCache<>(3);
        cache.put("key1", 1);
        cache.put("key2", 2);
        cache.put("key3", 3);

        cache.clear();

        assertEquals(cache.get("key1"), Optional.empty());
        assertEquals(cache.get("key2"), Optional.empty());
        assertEquals(cache.get("key3"), Optional.empty());
    }


}
