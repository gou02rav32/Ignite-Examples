package ig;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.lang.IgniteCallable;

public class ComputeIgnite {
	public static void main(String[] args) {
		try (Ignite ignite = Ignition.start("/ig/src/main/java/example-ignite.xml")) {
			  Collection<IgniteCallable<Integer>> calls = new ArrayList<>();

			  // Iterate through all the words in the sentence and create Callable jobs.
			  for (final String word : "Count characters using callable".split(" "))
			    calls.add(word::length);

			  // Execute collection of Callables on the grid.
			  Collection<Integer> res = ignite.compute().call(calls);

			  // Add up all the results.
			  int sum = res.stream().mapToInt(Integer::intValue).sum();
			 
				System.out.println("Total number of characters is '" + sum + "'.");
		}
		}
}