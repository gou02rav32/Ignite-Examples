package computeGridExa;

import java.util.ArrayList;
import java.util.Collection;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.lang.IgniteFuture;
import org.apache.ignite.lang.IgniteRunnable;


public class ComputeAsyncExample {
	
	    /**
	     * Executes example.
	     *
	     * @param args Command line arguments, none required.
	     * @throws IgniteException If example execution failed.
	     */
	    public static void main(String[] args) throws IgniteException {
	        try (Ignite ignite = Ignition.start()) {
	            System.out.println();
	            System.out.println("Compute asynchronous example started.");

	            // Enable asynchronous mode.
	            IgniteCompute compute = ignite.compute();

	            Collection<IgniteFuture<?>> futs = new ArrayList<>();

	            // Iterate through all words in the sentence and create runnable jobs.
	            for (final String word : "Print words using runnable".split(" ")) {
	                // Execute runnable on some node.
	                futs.add(compute.runAsync(new IgniteRunnable() {
	                    @Override public void run() {
	                        System.out.println();
	                        System.out.println(">>> Printing '" + word + "' on this node from ignite job.");
	                    }
	                }));
	            	
	            }

	            // Wait for completion of all futures.
	            for (IgniteFuture<?> fut : futs)
	                fut.get();

	            System.out.println();
	            System.out.println(">>> Finished printing words using runnable execution.");
	            System.out.println(">>> Check all nodes for output (this node is also part of the cluster).");
	        }
	    }
	}

