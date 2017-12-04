package computeGridExa;

import java.util.Collection;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.lang.IgniteCallable;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.IgniteInstanceResource;

public class ComputeBroadCastExample {
    /**
     * Executes example.
     *
     * @param args Command line arguments, none required.
     * @throws IgniteException If example execution failed.
     */
    public static void main(String[] args) throws IgniteException {
        try (Ignite ignite = Ignition.start()) {
            System.out.println();
            System.out.println(">>> Compute broadcast example started.");

            // Print hello message on all nodes.
            hello(ignite);

            // Gather system info from all nodes.
            gatherSystemInfo(ignite);
       }
    }

    /**
     * Print 'Hello' message on all nodes.
     *
     * @param ignite Ignite instance.
     * @throws IgniteException If failed.
     */
    private static void hello(Ignite ignite) throws IgniteException {
        // Print out hello message on all nodes.
    	ignite.compute().broadcast(()-> { System.out.println(); System.out.println("<< Hello Node");});
        /*ignite.compute().broadcast(
            new IgniteRunnable() {
                @Override public void run() {
                    System.out.println();
                    System.out.println(">>> Hello Node! :)");
                }
            }
        );*/

        System.out.println();
        System.out.println(">>> Check all nodes for hello message output.");
    }

    /**
     * Gather system info from all nodes and print it out.
     *
     * @param ignite Ignite instance.
     * @throws IgniteException if failed.
     */
    private static void gatherSystemInfo(Ignite ignite) throws IgniteException {
        // Gather system info from all nodes.
        Collection<String> res = ignite.compute().broadcast(
            new IgniteCallable<String>() {
                // Automatically inject ignite instance.
                @IgniteInstanceResource
                private Ignite ignite;

                @Override public String call() {
                    System.out.println();
                    System.out.println("Executing task on node: " + ignite.cluster().localNode().id());

                    return "Node ID: " + ignite.cluster().localNode().id() + "\n" +
                        "OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version") + " " +
                        System.getProperty("os.arch") + "\n" +
                        "User: " + System.getProperty("user.name") + "\n" +
                        "JRE: " + System.getProperty("java.runtime.name") + " " +
                        System.getProperty("java.runtime.version");
                }
        });

        // Print result.
        System.out.println();
        System.out.println("Nodes system information:");
        System.out.println();

        for (String r : res) {
            System.out.println(r);
            System.out.println();
        }
    }
}