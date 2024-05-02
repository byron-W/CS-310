import java.util.Iterator;

/**
 * A least frequently used cache.
 * 
 * @author Byron Washington
 */
public class LfuCache implements Cache {

	// ******************************************************
	// ******* BELOW THIS LINE IS PROVIDED code *******
	// ******* Do NOT edit code! *******
	// ******* Remember to add JavaDoc *******
	// ******************************************************
	/**
	 * A block class to keep a value and access count.
	 */
	private class Block implements Comparable<Block> {

		/**
		 * Creating a placeholder for the block data.
		 */
		private String data;

		/**
		 * Creating a placeholder for the number of block accesses.
		 */
		private int count;

		/**
		 * Constructs Block.
		 * 
		 * @param addr the block data
		 */
		public Block(String addr) {
			this.data = addr;
			count = 1;
		}

		/**
		 * Returns the count of the block.
		 * 
		 * @return the count
		 */
		public int getCount() {
			return count;
		}

		/**
		 * Increments the count of the block.
		 */
		public void incCount() {
			count++;
		}

		/**
		 * Returns the data of the block.
		 * 
		 * @return the data
		 */
		public String getData() {
			return data;
		}

		/**
		 * Returns whether a block is equal to another.
		 * 
		 * @param other the block
		 * @return if the blocks are equal
		 */
		@Override
		public boolean equals(Object other) {
			if (other instanceof Block) {
				if (((Block) other).data.equals(this.data)) { // if an item is already in cache
					return true;
				}
			}
			return false;
		}

		/**
		 * Returns the comparison of two blocks.
		 * 
		 * @param other the block
		 * @return the difference
		 */
		public int compareTo(Block other) {
			return this.count - other.count; // compares access counts
		}

		/**
		 * Returns a string representing the data and count.
		 * 
		 * @return the string
		 */
		@Override
		public String toString() {
			return "<" + data.toString() + "," + count + ">";
		}
	}

	/**
	 * Creating a placeholder for the cache capacity.
	 */
	private int capacity;
	/**
	 * Creating a placeholder for the cache addresses.
	 */
	private SortedList<Block> storage; // NOTE: SortedList! List of Blocks!

	// ******************************************************
	// ******* END of PROVIDED Code *******
	// ******* Do NOT Change PROVIDED Code *******
	// ******************************************************

	// YOU CANNOT ADD MORE DATA MEMBERS!
	// ADD PRIVATE HELPER METHODS IF NEEDED!

	/**
	 * Constructs LfuCache.
	 * 
	 * @param cap the max capacity of the cache
	 * @throws IllegalArgumentException if the cap is not positive
	 */
	public LfuCache(int cap) {
		if (cap < 1)
			throw new IllegalArgumentException("Capacity must be positive");
		capacity = cap;
		storage = new SortedList<Block>();
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
		return storage.getLast().getData(); // return the tail
	}

	/**
	 * Returns true if there is a hit or miss for the given address.
	 * 
	 * @param addr the given address
	 * @return whether the address is there or not
	 * @throws IllegalArgumentException if the address is null
	 */
	public boolean access(String addr) {// O(n)
		boolean hit = false;
		if (addr == null)
			throw new IllegalArgumentException("Invalid address");
		for (Block item : storage) {
			if (item.getData().equals(addr)) {
				item.incCount();
				storage.moveToFront(item); // since it was used, move it to the front
				hit = true;
				break;
			}
		}
		if (!hit) { // if there is no hit
			if (this.isFull())
				storage.removeLast();
			storage.addFirst(new Block(addr));
		}
		sortCache(); // sort the head to the correct spot
		return hit;
	}

	/**
	 * Returns a string of the cache from LFU to MFU.
	 * 
	 * @return the string of the cache
	 */
	@Override
	public String toString() {// O(n)
		if (size() < 1)
			return null;
		// the tail is always the next to be replaced so we create
		// a reversed list when we print for readability
		SortedList<Block> reversed = new SortedList<Block>();
		for (Block s : storage) {
			reversed.addFirst(s);
		}
		return reversed.listToString();
	}

	/**
	 * Sorts the head to the right position.
	 */
	// made a separate method to help readability
	private void sortCache() {
		Node<Block> newValNode = storage.head;
		Node<Block> aheadNode = newValNode.getNext();
		while (aheadNode != null) {
			if (newValNode.getData().compareTo(aheadNode.getData()) >= 0)
				return;
			storage.moveBackward(newValNode.getData()); // move backward if the value is greater or equal to the next
			aheadNode = aheadNode.getNext();
			newValNode = aheadNode;
			aheadNode = newValNode.getNext();
			// iterate through list
		}
	}
}