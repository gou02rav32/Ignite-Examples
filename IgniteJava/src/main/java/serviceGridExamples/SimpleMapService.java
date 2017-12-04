package serviceGridExamples;

public interface SimpleMapService<K, V> {
	
	    void put(K key, V val);

	    /**
	     * Gets value based on key.
	     *
	     * @param key Key.
	     * @return Value.
	     */
	    V get(K key);

	    /**
	     * Clears map.
	     */
	    void clear();

	    /**
	     * @return Map size.
	     */
	    int size();
	}

