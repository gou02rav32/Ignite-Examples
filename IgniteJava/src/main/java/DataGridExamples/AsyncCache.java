package DataGridExamples;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.lang.IgniteFuture;
import org.apache.ignite.lang.IgniteInClosure;

public class AsyncCache {
	
	private static final String CACHE_NAME = AsyncCache.class.getSimpleName();
	

	    /**
	     * Executes example.
	     *
	     * @param args Command line arguments, none required.
	     * @throws IgniteException If example execution failed.
	     */
	    public static void main(String[] args) throws IgniteException {
	        try (Ignite ignite = Ignition.start()) {
	            System.out.println();
	            System.out.println(">>> Cache asynchronous API example started.");

	            // Auto-close cache at the end of the example.
	            try (IgniteCache<Integer, String> cache = ignite.getOrCreateCache(CACHE_NAME)) {
	                Collection<IgniteFuture<?>> futs = new ArrayList<>();

	                // Execute several puts asynchronously.
	                for (int i = 0; i < 10; i++)
	                    futs.add(cache.putAsync(i, String.valueOf(i)));

	                // Wait for completion of all futures.
	                for (IgniteFuture<?> fut : futs)
	                    fut.get();

	                // Execute get operation asynchronously and wait for result.
	                cache.getAsync(1).listen(new IgniteInClosure<IgniteFuture<String>>() {
	                    @Override public void apply(IgniteFuture<String> fut) {
	                        System.out.println("Get operation completed [value=" + fut.get() + ']');
	                    }
	                });
	            }
	            finally {
	                // Distributed cache could be removed from cluster only by #destroyCache() call.
	                ignite.destroyCache(CACHE_NAME);
	            }
	        }
	    }
	}

