package ig;

import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.DataPageEvictionMode;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.configuration.MemoryConfiguration;
import org.apache.ignite.configuration.MemoryPolicyConfiguration;

public class DurableMemoryConfig {
	public static void main(String[] args) {
		/*IgniteConfiguration cfg = new IgniteConfiguration();

		// Changing total RAM size to be used by Ignite Node.
		MemoryConfiguration memCfg = new MemoryConfiguration();

		// Setting the size of the default memory region to 4GB to achieve this.
		memCfg.setDefaultMemoryPolicySize(4L * 1024 * 1024 * 1024);

		cfg.setMemoryConfiguration(memCfg);
		
		// Altering the concurrency level.
		memCfg.setConcurrencyLevel(4);

		// Changing the page size to 4 KB.
		memCfg.setPageSize(4096);
		MemoryPolicyConfiguration plCfg = new MemoryPolicyConfiguration();

		// Policy/region name.
		plCfg.setName("500MB_Region_Eviction");

		// Setting initial size.
		plCfg.setInitialSize(100L * 1024 * 1024);

		// Setting maximum size.
		plCfg.setMaxSize(500L * 1024 * 1024);

		// Setting data pages eviction algorithm.
		plCfg.setPageEvictionMode(DataPageEvictionMode.RANDOM_2_LRU);

		// Applying the memory policy.
		memCfg.setMemoryPolicies(plCfg);
		
		plCfg.setName("20GB_Region_Eviction");

		// Initial size is 5 GB.
		plCfg.setInitialSize(5L * 1024 * 1024 * 1024);

		// Maximum size is 20 GB.
		plCfg.setMaxSize(20L * 1024 * 1024 * 1024);

		// Enabling RANDOM_LRU eviction.
		plCfg.setPageEvictionMode(DataPageEvictionMode.RANDOM_LRU);
		        
		// Setting the new memory policy.
		memCfg.setMemoryPolicies(plCfg);
		        
		// Applying the new page memory configuration.
		        
		// Applying the new page memory configuration.
		cfg.setMemoryConfiguration(memCfg);
		System.out.println(cfg);*/
		
		/*CacheConfiguration cacheCfg = new CacheConfiguration();

		// Setting a memory policy name to bind to a specific memory region.
		cacheCfg.setMemoryPolicyName("500MB_Region_Eviction");
		        
		// Setting the cache name.
		cacheCfg.setName("SampleCache");

		// Applying the cache configuration.
		cfg.setCacheConfiguration(cacheCfg);*/

		// Starting the node.
		//Ignition.start(cfg);
		System.out.println("Done");
		
	}
}
