package DataGridExamples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.lang.IgniteRunnable;

public  class AffinityProg {
   
    private static final String CACHE_NAME = AffinityProg.class.getSimpleName();

    private static final int KEY_CNT = 20;

    public static void main(String[] args) throws IgniteException {
        try (Ignite ignite = Ignition.start()) {
            System.out.println();
            System.out.println(">>> Cache affinity example started.");

            // Auto-close cache at the end of the example.
            try (IgniteCache<Integer, String> cache = ignite.getOrCreateCache(CACHE_NAME)) {
                for (int i = 0; i < KEY_CNT; i++)
                    cache.put(i, Integer.toString(i));

                // Co-locates jobs with data using IgniteCompute.affinityRun(...) method.
                visitUsingAffinityRun();

                // Co-locates jobs with data using IgniteCluster.mapKeysToNodes(...) method.
                visitUsingMapKeysToNodes();
            }
            finally {
                // Distributed cache could be removed from cluster only by #destroyCache() call.
                ignite.destroyCache(CACHE_NAME);
            }
        }
    }

    /**
     * Collocates jobs with keys they need to work on using
     * {@link IgniteCompute#affinityRun(String, Object, IgniteRunnable)} method.
     */
    private static void visitUsingAffinityRun() {
        Ignite ignite = Ignition.ignite();

        final IgniteCache<Integer, String> cache = ignite.cache(CACHE_NAME);

        for (int i = 0; i < KEY_CNT; i++) {
            final int key = i;

            // This runnable will execute on the remote node where
            // data with the given key is located. Since it will be co-located
            // we can use local 'peek' operation safely.
            ignite.compute().affinityRun(CACHE_NAME, key, new IgniteRunnable() {
                @Override public void run() {
                    // Peek is a local memory lookup, however, value should never be 'null'
                    // as we are co-located with node that has a given key.
                    System.out.println("Co-located using affinityRun [key= " + key + ", value=" + cache.localPeek(key) + ']');
                }
            });
        }
    }

    /**
     * Collocates jobs with keys they need to work on using {@link Affinity#mapKeysToNodes(Collection)}
     * method. The difference from {@code affinityRun(...)} method is that here we process multiple keys
     * in a single job.
     */
    private static void visitUsingMapKeysToNodes() {
        final Ignite ignite = Ignition.ignite();

        Collection<Integer> keys = new ArrayList<>(KEY_CNT);

        for (int i = 0; i < KEY_CNT; i++)
            keys.add(i);

        // Map all keys to nodes.
        Map<ClusterNode, Collection<Integer>> mappings = ignite.<Integer>affinity(CACHE_NAME).mapKeysToNodes(keys);

        for (Map.Entry<ClusterNode, Collection<Integer>> mapping : mappings.entrySet()) {
            ClusterNode node = mapping.getKey();

            final Collection<Integer> mappedKeys = mapping.getValue();

            if (node != null) {
                // Bring computations to the nodes where the data resides (i.e. collocation).
                ignite.compute(ignite.cluster().forNode(node)).run(new IgniteRunnable() {
                    @Override public void run() {
                        IgniteCache<Integer, String> cache = ignite.cache(CACHE_NAME);

                        
                        for (Integer key : mappedKeys)
                            System.out.println("Co-located using mapKeysToNodes [key= " + key +
                                ", value=" + cache.localPeek(key) + ']');
                    }
                });
            }
        }
    }
}
