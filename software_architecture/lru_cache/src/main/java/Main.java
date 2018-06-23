import components.LRUCache;
import org.jetbrains.annotations.NotNull;

public class Main {

    public static void main(@NotNull String[] args) {
        LRUCache<String, Integer> cache = new LRUCache<>(50);
        cache.put("key", 1024);
        cache.get("key").ifPresent(System.out::println);
    }
}
