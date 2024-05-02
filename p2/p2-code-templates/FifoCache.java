import java.util.Iterator;

/**
 * A first in, first out cache.
 * 
 * @author Byron Washington
 */
public class FifoCache implements Cache {
	/**
	 * Creating a placeholder for the cache capacity.
	 */
	private int capacity;
	/**
	 * Creating a placeholder for the cache addresses.
	 */
	private BasicList<String> storage; // each address is a string

	// YOU CANNOT ADD MORE DATA MEMBERS!
	// ADD PRIVATE HELPER METHODS IF NEEDED!

	/**
	 * Constructs FifoCache.
	 * 
	 * @param cap the max capacity of the cache
	 * @throws IllegalArgumentException if the cap is not positive
	 */
	public FifoCache(int cap) {// O(1)
		if (cap < 1)
			throw new IllegalArgumentException("Capacity must be positive");
		capacity = cap;
		storage = new BasicList<String>();
	}

	/**
	 * Checks whether the cache is full.
	 * 
	 * @return a boolean depending if the cache is at capacity
	 */
	public boolean isFull() {// O(1)
		if (storage.size() >= capacity)
			return true;
		else
			return false;
	}

	/**
	 * Returns the capacity of the cache.
	 * 
	 * @return the max capacity
	 */
	public int capacity() {// O(1)
		return capacity;
	}

	/**
	 * Returns the number of items within the cache.
	 * 
	 * @return how full the cache is
	 */
	public int size() {// O(1)
		return storage.size();
	}

	/**
	 * Returns the next item to be replaced.
	 * 
	 * @return the item to be replaced or null if none
	 */
	public String nextToReplace() {// O(1)
		if (storage.size() < capacity) // if the storage isn't at full capacity
			return null;
		return storage.getLast(); // return the tail
	}

	/**
	 * Returns true if there is a hit or miss for the given address.
	 * 
	 * @param addr the given address
	 * @return whether the address is there or not
	 * @throws IllegalArgumentException if the address is null
	 */
	public boolean access(String addr) {// O(n)
		if (addr == null)
			throw new IllegalArgumentException("Invalid address");
		for (String item : storage) {
			if (item.equals(addr))
				return true;
		}
		if (this.isFull()) // if the cache is full, remove the tail
			storage.removeLast();
		storage.addFirst(addr); // add new value to the front
		return false;
	}

	/**
	 * Returns a string of the cache in FIFO order.
	 * 
	 * @return the string of the cache
	 */
	@Override
	public String toString() {// O(n)
		if (size() < 1)
			return null;
		// the tail is always the next to be replaced so we create
		// a reversed list when we print for readability
		BasicList<String> reversed = new BasicList<String>();
		for (String s : storage) {
			reversed.addFirst(s);
		}
		return reversed.listToString();
	}
}