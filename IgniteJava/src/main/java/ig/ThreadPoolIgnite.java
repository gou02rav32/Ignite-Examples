package ig;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.ExecutorConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.IgniteInstanceResource;

public class ThreadPoolIgnite {
	public static void main(String[] args) {
		IgniteConfiguration cfg = new IgniteConfiguration();
		Ignite ig = Ignition.start(cfg);
		cfg.setExecutorConfiguration(new ExecutorConfiguration("myPool").setSize(16));
	}
	public class InnerRunnable implements IgniteRunnable {    
	    @Override public void run() {
	        System.out.println("Hello from inner runnable!");
	    }
	}
	public class OuterRunnable implements IgniteRunnable {    
	    @IgniteInstanceResource
	    private Ignite ignite;
	    
	    @Override public void run() {
	        // Synchronously execute InnerRunnable in custom executor.
	        ignite.compute().withExecutor("myPool").run(new InnerRunnable());
	    }
	}
}
