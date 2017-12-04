package DataGridExamples;

import java.util.concurrent.ConcurrentMap;
import javax.cache.processor.EntryProcessor;
import javax.cache.processor.MutableEntry;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;


public class CacheApiExample {
		    /** Cache name. */
	    private static final String CACHE_NAME = CacheApiExample.class.getSimpleName();

	    /**
	     * Executes example.
	     *
	     * @param args Command line arguments, none required.
	     * @throws IgniteException If example execution failed.
	     */
	    public static void main(String[] args) throws IgniteException {
	        try (Ignite ignite = Ignition.start()) {
	            System.out.println();
	            System.out.println(">>> Cache API example started.");

	            // Auto-close cache at the end of the example.
	            try (IgniteCache<Integer, String> cache = ignite.getOrCreateCache(CACHE_NAME)) {
	                // Demonstrate atomic map operations.
	                atomicMapOperations(cache);
	            }
	            finally {
	                // Distributed cache could be removed from cluster only by #destroyCache() call.
	                ignite.destroyCache(CACHE_NAME);
	            }
	        }
	    }

	    /**
	     * Demonstrates cache operations similar to {@link ConcurrentMap} API. Note that
	     * cache API is a lot richer than the JDK {@link ConcurrentMap}.
	     *
	     * @throws IgniteException If failed.
	     */
	    private static void atomicMapOperations(final IgniteCache<Integer, String> cache) throws IgniteException {
	        System.out.println();
	        System.out.println(">>> Cache atomic map operation examples.");

	        // Put and return previous value.
	        String v = cache.getAndPut(1, "1");
	        assert v == null;

	        // Put and do not return previous value.
	        // Performs better when previous value is not needed.
	        cache.put(2, "2");

	        // Put-if-absent.
	        boolean b1 = cache.putIfAbsent(4, "4");
	        boolean b2 = cache.putIfAbsent(4, "44");
	        assert b1 && !b2;

	        // Invoke - assign new value based on previous value.
	        cache.put(6, "6");
	        cache.invoke(6, new EntryProcessor<Integer, String, Object>() {
	            @Override public Object process(MutableEntry<Integer, String> entry, Object... args) {
	                String v = entry.getValue();

	                entry.setValue(v + "6"); // Set new value based on previous value.

	                return null;
	            }
	        });

	        // Replace.
	        cache.put(7, "7");
	        b1 = cache.replace(7, "7", "77");
	        b2 = cache.replace(7, "7", "777");
	        assert b1 & !b2;
	    }
	}

