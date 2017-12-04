package computeGridExa;

import java.util.ArrayList;
import java.util.Collection;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.lang.IgniteCallable;

public class ComputeCallableExample {
    /**
     * Executes example.
     *
     * @param args Command line arguments, none required.
     * @throws IgniteException If example execution failed.
     */
    public static void main(String[] args) throws IgniteException {
        try (Ignite ignite = Ignition.start()) {
            System.out.println();
            System.out.println(">>> Compute callable example started.");

            Collection<IgniteCallable<Integer>> calls = new ArrayList<>();

            // Iterate through all words in the sentence and create callable jobs.
            for (final String word : "Count characters using callable".split(" ")) {
                calls.add(new IgniteCallable<Integer>() {
                    @Override public Integer call() throws Exception {
                        System.out.println();
                        System.out.println(">>> Printing '" + word + "' on this node from ignite job.");

                        return word.length();
                    }
                });
            }

            // Execute collection of callables on the ignite.
            Collection<Integer> res = ignite.compute().call(calls);

            int sum = 0;

            // Add up individual word lengths received from remote nodes.
            for (int len : res)
                sum += len;

            System.out.println();
            System.out.println(">>> Total number of characters in the phrase is '" + sum + "'.");
            System.out.println(">>> Check all nodes for output (this node is also part of the cluster).");
        }
    }
}
