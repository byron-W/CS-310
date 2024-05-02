import java.util.Iterator;

/**
 * A sorted linked list that extends BasicList.
 * 
 * @author Byron Washington
 * @param <T> the type of element in the sorted list
 */
public class SortedList<T extends Comparable<T>> extends BasicList<T> {
	/**
	 * Adds and sorts the new value into the linked list.
	 * 
	 * @param value the value to be added
	 * @throws IllegalArgumentException if value is null
	 */
	public void add(T value) {// O(n)
		if (value == null)
			throw new IllegalArgumentException("Invalid node value");
		addFirst(value); // add the value to the front of the list
		if (size() == 1) // if the new value is the only value
			return;
		Node<T> newValNode = head;
		Node<T> aheadNode = newValNode.getNext();
		while (aheadNode != null) {
			if (newValNode.getData().compareTo(aheadNode.getData()) < 0)
				return;
			moveBackward(newValNode.getData()); // move backward if the new value is greater than the next
			aheadNode = aheadNode.getNext();
			newValNode = aheadNode;
			aheadNode = newValNode.getNext();
			if (newValNode.getNext() == null) // if the new value is at the end, make it the tail
				tail = newValNode;
		}
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
	public static void main(String[] args) {
		SortedList<String> names = new SortedList<>();

		names.add("Mason");
		System.out.println(names.listToString());
		names.add("George");
		System.out.println(names.listToString());
		names.add("Washington");
		System.out.println(names.listToString());
		if (names.size() == 3 && names.getFirst().equals("George") &&
				names.listToString().equals("George Mason Washington")) {
			System.out.println("Yay1");
		}

		System.out.println("\nDone with names\n");
		/**
		 * SomeType class.
		 */
		class SomeType implements Comparable<SomeType> {
			/**
			 * Creating a placeholder for the value.
			 */
			private String value;

			/**
			 * Constructs SomeType.
			 * 
			 * @param value the value
			 */
			public SomeType(String value) {
				this.value = value;
			}

			/**
			 * Compares two objects based off their length.
			 * 
			 * @param other the object
			 * @return the difference
			 */
			public int compareTo(SomeType other) {
				return this.value.length() - other.value.length();
			}

			/**
			 * Returns a string representing the value.
			 * 
			 * @return the string
			 */
			public String toString() {
				return value;
			}
		}

		SomeType item1 = new SomeType("123");
		SomeType item2 = new SomeType("1234");
		SomeType item3 = new SomeType("12345");
		SomeType item4 = new SomeType("7890");

		SortedList<SomeType> items = new SortedList<>();
		items.add(item2);
		items.add(item1);
		items.add(item3);
		System.out.println(items.listToString());
		boolean ok = items.listToString().equals("123 1234 12345");
		items.add(item4);
		System.out.println(items.listToString());

		// add with a tie: 7890 should be inserted after 1234
		if (ok && items.listToString().equals("123 1234 7890 12345")) {
			System.out.println("Yay2");
		}
	}
}
