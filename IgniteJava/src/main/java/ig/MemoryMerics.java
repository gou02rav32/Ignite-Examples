package ig;

import java.util.Collection;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.MemoryMetrics;
import org.apache.ignite.PersistenceMetrics;

public class MemoryMerics {
	public static void main(String[] args) {
		Ignite ignite = Ignition.start();
		Collection<MemoryMetrics> regionsMetrics = ignite.memoryMetrics();

		// Print some of the metrics' probes for all the regions.
		for (MemoryMetrics metrics : regionsMetrics) {
		  System.out.println(">>> Memory Region Name: " + metrics.getName());
		  System.out.println(">>> Allocation Rate: " + metrics.getAllocationRate());
		  System.out.println(">>> Fill Factor: " + metrics.getPagesFillFactor());
		}
		PersistenceMetrics pm = ignite.persistentStoreMetrics();

		System.out.println("Fsync duration: " + pm.getLastCheckpointFsyncDuration());

		System.out.println("Data pages: " + pm.getLastCheckpointDataPagesNumber());

		System.out.println("Checkpoint duration:" + pm.getLastCheckpointingDuration());
	}
}
