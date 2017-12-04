package ig;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;

public class IgniteCacheDemo {
	public static void cacheDemo() {
		Ignite ignite = Ignition.start();
		IgniteCache<Integer, String> cache = ignite.getOrCreateCache("test");
		if(cache.putIfAbsent(1, "Hello World !")) {
			System.out.println("Added a value to the cache ");
		}
		else
		{
			System.out.println(cache.get(1));
		}
		/*cache.put(1, "Hello World!");
		System.out.println(cache.get(1));*/
	}
}	
