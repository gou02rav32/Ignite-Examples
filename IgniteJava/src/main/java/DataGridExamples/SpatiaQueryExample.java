package DataGridExamples;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.*;
import org.apache.ignite.*;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.cache.query.annotations.*;
import org.apache.ignite.configuration.*;

import javax.cache.*;
import java.util.*;

public class SpatiaQueryExample {
    /** Cache name. */
	    private static final String CACHE_NAME = SpatiaQueryExample.class.getSimpleName();

	    /**
	     * @param args Command line arguments, none required.
	     */
	    public static void main(String[] args) throws Exception {
	        // Starting Ignite node.
	        try (Ignite ignite = Ignition.start()) {
	            // Preparing the cache configuration.
	            CacheConfiguration<Integer, MapPoint> cc = new CacheConfiguration<>(CACHE_NAME);

	            // Setting the indexed types.
	            cc.setIndexedTypes(Integer.class, MapPoint.class);

	            // Starting the cache.
	            try (IgniteCache<Integer, MapPoint> cache = ignite.createCache(cc)) {
	                Random rnd = new Random();

	                WKTReader r = new WKTReader();

	                // Adding geometry points into the cache.
	                for (int i = 0; i < 1000; i++) {
	                    int x = rnd.nextInt(10000);
	                    int y = rnd.nextInt(10000);

	                    Geometry geo = r.read("POINT(" + x + " " + y + ")");

	                    cache.put(i, new MapPoint(geo));
	                }

	                // Query to fetch the points that fit into a specific polygon.
	                SqlQuery<Integer, MapPoint> query = new SqlQuery<>(MapPoint.class, "coords && ?");

	                // Selecting points that fit into a specific polygon.
	                for (int i = 0; i < 10; i++) {
	                    // Defining the next polygon boundaries.
	                    Geometry cond = r.read("POLYGON((0 0, 0 " + rnd.nextInt(10000) + ", " +
	                        rnd.nextInt(10000) + " " + rnd.nextInt(10000) + ", " +
	                        rnd.nextInt(10000) + " 0, 0 0))");

	                    // Executing the query.
	                    Collection<Cache.Entry<Integer, MapPoint>> entries = cache.query(query.setArgs(cond)).getAll();

	                    // Printing number of points that fit into the area defined by the polygon.
	                    System.out.println("Fetched points [cond=" + cond + ", cnt=" + entries.size() + ']');
	                }
	            }
	        }
	    }

	    /**
	     * MapPoint with indexed coordinates.
	     */
	    private static class MapPoint {
	        /** Coordinates. */
	        @QuerySqlField(index = true)
	        private Geometry coords;

	        /**
	         * @param coords Coordinates.
	         */
	        private MapPoint(Geometry coords) {
	            this.coords = coords;
	        }
	    }
	}
