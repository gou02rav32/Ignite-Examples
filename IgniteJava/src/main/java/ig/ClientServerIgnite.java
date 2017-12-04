package ig;

import javax.cache.CacheException;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteClientDisconnectedException;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;

public class ClientServerIgnite {
	private static IgniteRunnable job;

	public static void main(String[] args) {
		IgniteConfiguration cfg = new IgniteConfiguration();
		
		Ignite ig = Ignition.start(cfg);
		Ignition.setClientMode(true);
		
		CacheConfiguration cacheCfg = new CacheConfiguration("myCache");
		
		// Create cache on all the existing and future server nodes.
		// Note that since the local node is a client, it will not 
		// be caching any data.
		//IgniteCache<?, ?> cache = ig.getOrCreateCache(cfg);
		
		IgniteCompute compute = ig.compute();

		// Execute computation on the server nodes (default behavior).
		compute.broadcast(() -> System.out.println("Hello Server"));
		
		ClusterGroup clientGroup = ig.cluster().forClients();

		IgniteCompute clientCompute = ig.compute(clientGroup);

		// Execute computation on the client nodes.
		clientCompute.broadcast(() -> System.out.println("Hello Client"));
		
		TcpCommunicationSpi commSpi = new TcpCommunicationSpi();
		commSpi.setSlowClientQueueLimit(1000);
		
		while (true) {
		    try {
		        compute.run(job);
		    }
		    catch (IgniteClientDisconnectedException e) {
		        e.reconnectFuture().get(); // Wait for reconnection.

		        // Can proceed and use the same IgniteCompute instance.
		    }
		
		IgniteCache cache = ig.getOrCreateCache(cfg);

		while (true) {
		  try {
		    cache.put("Integer", "String");
		  }
		  catch (CacheException e) {
		    if (e.getCause() instanceof IgniteClientDisconnectedException) {
		      IgniteClientDisconnectedException cause =
		        (IgniteClientDisconnectedException)e.getCause();

		      cause.reconnectFuture().get(); // Wait for reconnection.

		      // Can proceed and use the same IgniteCache instance.
		    }
		  }
		}
		TcpDiscoverySpi discoverySpi = new TcpDiscoverySpi();

		discoverySpi.setClientReconnectDisabled(true);

		cfg.setDiscoverySpi(discoverySpi);
		
		discoverySpi.setForceServerMode(true);

		cfg.setDiscoverySpi(discoverySpi);
		}

	}
}
