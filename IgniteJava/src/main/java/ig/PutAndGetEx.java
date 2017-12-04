package ig;


import java.util.concurrent.locks.Lock;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.transactions.Transaction;

public class PutAndGetEx {
	public static void main(String[] args) {
		try (Ignite ignite = Ignition.start(); Transaction tx = ignite.transactions().txStart()) {
		    IgniteCache<Integer, String> cache = ignite.getOrCreateCache("myCacheName");
		 
		    // Store keys in cache (values will end up on different cache nodes).
		    for (int i = 0; i < 10; i++)
		        cache.put(i, Integer.toString(i));
		 
		    for (int i = 0; i < 10; i++)
		        System.out.println("Got [key=" + i + ", val=" + cache.get(i) + ']');
		    
		   
		       /* Integer hello = cache.get("Hello", 1);
		      
		        if (hello == 1)
		            cache.put("Hello", 11);
		      
		        cache.put("World", 22);
		      
		        tx.commit();*/
		    
		   /* Lock lock = cache.lock("Hello");
		    
		    lock.lock();
		     
		    try {
		        cache.put(11, "Hello");
		        cache.put(22, "World");
		    }
		    finally {
		        lock.unlock();
		    }*/
		    
		    
		}
	}
}
