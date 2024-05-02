import java.util.Iterator;

/**
 * A hash map that implements separate chaining.
 * 
 * @author Byron Washington
 * @param <K> the key of the element
 * @param <V> the value of the element
 */

public class BasicMap<K, V> {

	// ******************************************************
	// ******* BELOW THIS LINE IS PROVIDED code *******
	// ******* Do NOT edit code! *******
	// ******* Remember to add JavaDoc *******
	// ******************************************************

	/**
	 * A pair of a key (K) and a value (V).
	 */
	private class Pair {
		/**
		 * Creating a placeholder for the pair key.
		 */
		private K key;
		/**
		 * Creating a placeholder for the pair value.
		 */
		private V value;

		/**
		 * Creates a Pair instance.
		 * 
		 * @param key   the key
		 * @param value the value
		 */
		public Pair(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * Returns the key of the pair.
		 * 
		 * @return the key
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Returns the value of the pair.
		 * 
		 * @return the value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Sets the value of the pair.
		 * 
		 * @param value the value
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Returns a string representing the key and pair.
		 * 
		 * @return the string
		 */
		@Override
		public String toString() {
			return "<" + key.toString() + ":" + value.toString() + ">";
		}

		/**
		 * Returns whether a given key is equal to the current key.
		 * 
		 * @param o the object
		 * @return if they are equal
		 */
		@Override
		@SuppressWarnings("unchecked")
		public boolean equals(Object o) {
			// return true if two pairs have matching keys
			// i.e. <"Alice", 1> is considered as equal to <"Alice", 2>
			if (o instanceof BasicMap<?, ?>.Pair) {
				Pair pair = (Pair) o;
				return pair.key.equals(key);
			}
			return false;
		}

		/**
		 * Returns the key's hashcode.
		 * 
		 * @return the hashcode
		 */
		@Override
		public int hashCode() {
			return key.hashCode();
		}

	}

	/**
	 * Creates a placeholder for the map buckets.
	 */
	private BasicList<Pair>[] buckets;

	/**
	 * Creates a default capacity for the map.
	 */
	final static private int DEFAULT_CAPACITY = 7;

	/**
	 * Creates a placeholder for the number of elements in the map.
	 */
	private int size;

	/**
	 * Constructs BasicMap.
	 */
	@SuppressWarnings("unchecked")
	public BasicMap() {
		buckets = (BasicList<Pair>[]) new BasicList[DEFAULT_CAPACITY];
		size = 0;
	}

	/**
	 * Returns the number of elements.
	 * 
	 * @return number of elements
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns the maps capacity.
	 * 
	 * @return number of elements
	 */
	private int capacity() {
		return buckets.length;
	}

	/**
	 * Returns the hashcode of a given key.
	 * 
	 * @param key the key
	 * @return the hashcode
	 */
	private int getHash(K key) {
		return Math.abs(key.hashCode());
	}

	/**
	 * Returns a detailed string representing all the elements of the map.
	 * 
	 * @return the string
	 */
	public String toStringDebug() {
		// print all entries of buckets, including null ones
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < buckets.length; i++) {
			BasicList<Pair> list = buckets[i];
			sb.append("[");
			if (list != null) {
				sb.append(list.listToString());
			}
			sb.append("]");
			if (i != buckets.length - 1)
				sb.append(",");

		}
		return "{" + sb.toString() + "}";
	}

	/**
	 * Returns a string representing all the elements of the map.
	 * 
	 * @return the string
	 */
	@Override
	public String toString() {
		// only display non-null entries of buckets
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < buckets.length; i++) {
			BasicList<Pair> list = buckets[i];
			if (list != null) {
				if (sb.length() > 0)
					sb.append(",");
				sb.append(list.listToString());
			}
		}
		return sb.toString();
	}

	// ******************************************************
	// ******* BELOW THIS LINE IS YOUR CODE *******
	// ******************************************************
	// ADD PRIVATE METHODS HERE IF NEEDED!
	// YOU CANNOT ADD MORE DATA MEMBERS
	/**
	 * Maps a key to its value in the hashmap.
	 * 
	 * @param key   the key
	 * @param value the value
	 * @throws IllegalArgumentException when key or value is null
	 */
	public void put(K key, V value) {// O(load) on average, and O(n) worst case
		if (key == null || value == null)
			throw new IllegalArgumentException("Invalid key and/or value");
		Pair newPair = new Pair(key, value);
		int hashCode = key.hashCode();
		int bucketIndex = Math.abs(hashCode % buckets.length);
		if (buckets[bucketIndex] == null) // if there is no chain started
			buckets[bucketIndex] = new BasicList<Pair>();
		Node<Pair> chainStart = buckets[bucketIndex].head;
		while (chainStart != null) { // if the key is already present
			if (chainStart.getData().getKey().equals(key)) {
				chainStart.getData().setValue(value); // replace value
				return;
			}
			chainStart = chainStart.getNext(); // iterate through bucket
		}
		buckets[bucketIndex].addLast(newPair); // add new pair to end of chain
		size++;
	}

	/**
	 * Returns the current mapping of a key.
	 * 
	 * @param key the key
	 * @return the found value or null if not found
	 */
	public V get(K key) {// O(load) on average, and O(n) worst case
		if (key == null)
			return null;
		int hashCode = key.hashCode();
		int bucketIndex = Math.abs(hashCode % buckets.length);
		if (buckets[bucketIndex] == null)
			return null;
		Node<Pair> chainStart = buckets[bucketIndex].head;
		while (chainStart != null) {
			if (chainStart.getData().getKey().equals(key))
				return chainStart.getData().getValue();
			chainStart = chainStart.getNext();
		}
		return null;
	}

	/**
	 * Returns the current mapping of a key and deletes it.
	 * 
	 * @param key the key
	 * @return the found value or null if not found
	 */
	public V delete(K key) {// O(load) on average, and O(n) worst case
		if (key == null)
			return null;
		int hashCode = key.hashCode();
		int bucketIndex = Math.abs(hashCode % buckets.length);
		if (buckets[bucketIndex] == null)
			return null;
		Node<Pair> chainStart = buckets[bucketIndex].head;
		while (chainStart != null) {
			if (chainStart.getData().getKey().equals(key)) {
				V value = chainStart.getData().getValue();
				buckets[bucketIndex].remove(chainStart.getData());
				size--;
				return value;
			}
			chainStart = chainStart.getNext();
		}
		return null;
	}

	// ******************************************************
	// ******* BELOW THIS LINE IS TESTING CODE *******
	// ******* Edit it as much as you'd like! *******
	// ******* Remember to add JavaDoc *******
	// ******************************************************
	/**
	 * The main method.
	 * 
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		BasicMap<String, String> map = new BasicMap<>();

		map.put("apple", "red");
		map.put("pear", "yellow");
		map.put("eggplant", "purple");

		if (map.get("apple").equals("red") && map.get("eggplant").equals("purple") && map.size() == 3) {
			System.out.println("Yay1");
		}

		// change mapping, delete
		map.put("apple", "green");
		if (map.get("apple").equals("green") && map.size() == 3 && map.delete("pear").equals("yellow")
				&& map.size() == 2) {
			System.out.println("Yay2");
		}

		// key not present
		if (map.get("banana") == null && map.delete("pear") == null) {
			System.out.println("Yay3");
		}

		// add to tail
		map.put("cherry", "red");
		if (map.toStringDebug().equals("{[],[<apple:green> <cherry:red>],[],[],[],[<eggplant:purple>],[]}")) {
			System.out.println("Yay4");
		}
	}
}