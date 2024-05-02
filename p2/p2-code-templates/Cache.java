//TODO: None. No changes allowed.

/**
 * An interface for common operations supported by a cache.
 * @author Y. Zhong
 */
public interface Cache{

	/**
	 * This is the method that reports max number of items allowed in cache.
	 * @return max number of items allowed in cache
	 */
	int capacity();

	/**
	 * This is the method that reports the number of items stored in cache.
	 * @return number of items stored in cache
	 */
	int size();

	/**
	 * This is the method that reports whether the cache is full or not.
	 * @return whether the cache is full
	 */
	boolean isFull();
	
	/**
	 * This is the method that reports the item that will be evicted if the next access is a miss.
	 * @return item that will be evicted if the next access is a miss. Return null if no item will be evicted
	 */
	String nextToReplace();
	
	/**
	 * This is the method that accepts the addr as the next access to cache and performs
	 * necessary maintenance based on a cache replacement policy.
	 * @param addr the address requested by the next access	
	 * @return true if the access is a cache hit, false if it is a cache miss
	 */
	boolean access(String addr);	
	
	
}