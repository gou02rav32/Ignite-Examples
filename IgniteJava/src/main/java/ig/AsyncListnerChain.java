package ig;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.Ignition;
import org.apache.ignite.lang.IgniteFuture;

public class AsyncListnerChain {
	public static void main(String[] args) {
		Ignite ignite = Ignition.start();
		IgniteCompute compute = ignite.compute();

		// Execute a closure asynchronously.
		IgniteFuture<String> fut = compute.callAsync(() -> {
		    return "Hello World";
		});

		// Listen for completion and print out the result.
		fut.listen(f -> System.out.println("Job result: " + f.get()));
	}
}
