package DataGridExamples;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;

public class EntryProcessCache {
	private static final String CACHE_NAME = EntryProcessCache.class.getSimpleName();
	
	private static final int KEY_CNT = 20;

    /** Keys predefined set. */
    private static final Set<Integer> KEYS_SET;

    /**
     * Initializes keys set that is used in bulked operations in the example.
     */
    static {
        KEYS_SET = new HashSet<>();

        for (int i = 0; i < KEY_CNT; i++)
            KEYS_SET.add(i);
    }
	
	public static void main(String[] args) {
		try (Ignite ignite = Ignition.start()) {
            System.out.println();
            System.out.println(">>> Entry processor example started.");

            
            try (IgniteCache<Integer, Integer> cache = ignite.getOrCreateCache(CACHE_NAME)) {
                
                populateEntriesWithInvoke(cache);

                // Demonstrates usage of EntryProcessor.invokeAll(...) method.
                incrementEntriesWithInvokeAll(cache);
            }
            finally {
                // Distributed cache could be removed from cluster only by #destroyCache() call.
                ignite.destroyCache(CACHE_NAME);
            }
        }
    }

    /**
     * Populates cache with values using {@link IgniteCache#invoke(Object, EntryProcessor, Object...)} method.
     *
     * @param cache Cache that must be populated.
     */
    private static void populateEntriesWithInvoke(IgniteCache<Integer, Integer> cache) {
        // Must be no entry in the cache at this point.
        printCacheEntries(cache);

        System.out.println("");
        System.out.println(">> Populating the cache using EntryProcessor.");

        // Invokes EntryProcessor for every key sequentially.
        for (int i = 0; i < KEY_CNT; i++) {
            cache.invoke(i, new EntryProcessor<Integer, Integer, Object>() {
                @Override public Object process(MutableEntry<Integer, Integer> entry,
                    Object... objects) throws EntryProcessorException {
                    // Initializes entry's value if it's not set.
                    if (entry.getValue() == null)
                        entry.setValue((entry.getKey() + 1) * 10);

                    return null;
                }
            });
        }

        // Print outs entries that are set using the EntryProcessor above.
        printCacheEntries(cache);
    }

    /**
     * Increments values of entries stored in the cache using
     * {@link IgniteCache#invokeAll(Set, EntryProcessor, Object...)} method.
     *
     * @param cache Cache instance.
     */
    private static void incrementEntriesWithInvokeAll(IgniteCache<Integer, Integer> cache) {
        System.out.println("");
        System.out.println(">> Incrementing values in the cache using EntryProcessor.");

        // Using EntryProcessor.invokeAll to increment every value in place.
        cache.invokeAll(KEYS_SET, new EntryProcessor<Integer, Integer, Object>() {
            @Override public Object process(MutableEntry<Integer, Integer> entry,
                Object... arguments) throws EntryProcessorException {

                entry.setValue(entry.getValue() + 5);

                return null;
            }
        });

        // Print outs entries that are incremented using the EntryProcessor above.
        printCacheEntries(cache);
    }

    /**
     * Prints out all the entries that are stored in a cache.
     *
     * @param cache Cache.
     */
    private static void printCacheEntries(IgniteCache<Integer, Integer> cache) {
        System.out.println();
        System.out.println(">>> Entries in the cache.");

        Map<Integer, Integer> entries = cache.getAll(KEYS_SET);

        if (entries.isEmpty())
            System.out.println("No entries in the cache.");
        else {
            for (Map.Entry<Integer, Integer> entry : entries.entrySet())
                System.out.println("Entry [key=" + entry.getKey() + ", value=" + entry.getValue() + ']');
        }
    }
	
}
