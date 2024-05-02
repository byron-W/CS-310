import java.util.Iterator;

/**
 * A singly linked list.
 * 
 * @author Byron Washington
 * @param <T> the type of element in the linked list
 */
public class BasicList<T> implements Iterable<T> {

	// ******************************************************
	// ******* BELOW THIS LINE IS PROVIDED code *******
	// ******* Do NOT edit code! *******
	// ******* Remember to add JavaDoc *******
	// ******************************************************

	/**
	 * Creating a placeholder for the head node.
	 */
	protected Node<T> head = null;

	/**
	 * Constructs an iterator.
	 * 
	 * @return an iterator for this linked list
	 */
	public Iterator<T> iterator() {
		// Return an iterator that traverses from
		// the beginning to the end of the list.
		// This provided code would work if you have set up the list correctly.

		return new Iterator<T>() {
			/**
			 * Creating a placeholder for the head node.
			 */
			Node<T> current = head;

			/**
			 * Checks whether there is an element after the current.
			 * 
			 * @return a boolean depending if the head is not null
			 */
			public boolean hasNext() {
				return current != null;
			}

			/**
			 * Returns the next element in the list.
			 * 
			 * @return the next element in the list
			 * @throws NullPointerException if there are no more items
			 */
			public T next() {
				T toReturn = current.getData();
				current = current.getNext();
				return toReturn;
			}
		};
	}
	// ******************************************************
	// ******* END of PROVIDED Code *******
	// ******* Do NOT Change PROVIDED Code *******
	// ******************************************************

	/**
	 * Creating a placeholder for the list size.
	 */
	private int size;
	/**
	 * Creating a placeholder for the tail node.
	 */
	protected Node<T> tail = null;

	/**
	 * Constructs BasicList.
	 */
	public BasicList() {
		size = 0;
	}

	/**
	 * Returns the size of the list.
	 * 
	 * @return the size of the list
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns the first value of the list.
	 * 
	 * @return the head of the list or null if the list is empty
	 */
	public T getFirst() {// O(1)
		if (size < 1) // if the list is empty
			return null;
		else
			return head.getData();
	}

	/**
	 * Inserts a node at the start of the list.
	 * 
	 * @param value the value to be inserted
	 * @throws IllegalArgumentException if the value is null
	 */
	public void addFirst(T value) {// O(1)
		if (value == null)
			throw new IllegalArgumentException("Invalid node value");
		Node<T> newHead = new Node<T>(value);
		newHead.setNext(head);
		head = newHead;
		if (size == 0) // if the new element is the only one
			tail = newHead;
		size++;
	}

	/**
	 * Removes and returns the first node.
	 * 
	 * @return the head of list or null if the list is empty
	 */
	public T removeFirst() {// O(1)
		if (size < 1) // if the list is empty
			return null;
		Node<T> oldHead = head;
		head = head.getNext();
		size--;
		if (size == 0) // if there are no more elements
			tail = null;
		return oldHead.getData();
	}

	/**
	 * Returns the last value of the list.
	 * 
	 * @return the tail of the list or null if the list is empty
	 */
	public T getLast() {// O(1)
		if (size < 1) // if the list is empty
			return null;
		if (size == 1)
			return head.getData();
		return tail.getData();
	}

	/**
	 * Inserts a node at the end of the list.
	 * 
	 * @param value the value to be inserted
	 * @throws IllegalArgumentException if the value is null
	 */
	public void addLast(T value) {// O(1)
		if (value == null)
			throw new IllegalArgumentException("Invalid node value");
		Node<T> newHead = new Node<T>(value);
		if (size == 0) { // if the new element is the only one
			head = newHead;
		} else {
			tail.setNext(newHead);
		}
		tail = newHead;
		size++;
	}

	/**
	 * Removes and returns the last node.
	 * 
	 * @return the tail of list or null if the list is empty
	 */
	public T removeLast() {// O(n)
		T result = null;
		if (size < 1) // if list is empty
			return result;
		if (size == 1) { // if there's only one element
			result = head.getData();
			head = tail = null;
			size--;
			return result;
		}
		// holding 3 nodes
		Node<T> behindTemp = null;
		Node<T> tempHead = head;
		Node<T> aheadTemp = head.getNext();
		while (aheadTemp != null) {
			behindTemp = tempHead;
			tempHead = aheadTemp;
			aheadTemp = aheadTemp.getNext(); // iterate through the list
		}
		result = tempHead.getData(); // save the old tail data
		behindTemp.setNext(null); // remove the old tail
		tempHead = null;
		tail = behindTemp; // the furthest back node is now the tail
		size--;
		return result;
	}

	/**
	 * Removes and returns the first occurence of a value.
	 * 
	 * @param value the value to look for
	 * @return the value that was found or null if it wasn't
	 */
	public T remove(T value) {// O(n)
		if (value == null)
			return null;
		T result = null;
		if (size < 1) // if list is empty
			return result;
		if (head.getData().equals(value)) { // if the given value is the head
			result = head.getData();
			head = head.getNext();
			size--;
			if (size == 0)
				tail = null;
			return result;
		}
		// holding 2 nodes
		Node<T> behindTemp = head;
		Node<T> temp = head.getNext();
		while (temp != null) {
			if (temp.getData().equals(value)) {
				result = temp.getData();
				behindTemp.setNext(temp.getNext());
				if (behindTemp.getNext() == null) // if the node behind is now the tail
					tail = behindTemp;
				temp = null;
				size--;
				break;
			}
			behindTemp = temp;
			temp = temp.getNext(); // iterate through the list
		}
		return result;
	}

	/**
	 * Returns the index within the list for a given value.
	 * 
	 * @param value the value to look for
	 * @return the index of the value or -1 if it wasn't found
	 */
	public int indexOf(T value) {// O(n)
		if (value == null)
			return -1;
		if (size < 1) // if the list is empty
			return -1;
		int result = -1;
		int c = 0; // counter for the loop
		for (T item : this) {
			if (item.equals(value)) { // if the value is found
				result = c;
				return result;
			}
			c++;
		}
		return result;
	}

	/**
	 * Returns a string representing all the values in the list separated by " ".
	 * 
	 * @return the string of list values or an empty string if the list is empty
	 */
	public String listToString() {
		StringBuilder builder = new StringBuilder("");
		if (size < 1) // if the list is empty
			return builder.toString();
		int c = 0;
		for (T item : this) {
			if (c != size - 1) {
				builder.append(item + " ");
				c++;
			} else
				builder.append(item);
		}
		return builder.toString();
	}

	/**
	 * Returns the first node with the given value.
	 * 
	 * @param value the value to look for
	 * @return the node or null if no list or not found
	 */
	public Node<T> getNode(T value) {// O(n)
		if (value == null)
			return null;
		if (size < 1) // if the list is empty
			return null;
		if (head.getData().equals(value)) // if the given value is the head
			return head;
		Node<T> result = null;
		Node<T> temp = head.getNext();
		while (temp != null) {
			if (value.equals(temp.getData())) {
				result = temp;
				break;
			}
			temp = temp.getNext(); // iterate through the list
		}
		return result;
	}

	/**
	 * Finds a value within the list and moves the first corresponding node
	 * to the front of the list, shifting all values before it, backwards.
	 * 
	 * @param value the value to look for
	 * @return a boolean depending on whether the list can be moved
	 */
	public boolean moveToFront(T value) {// O(n)
		if (value == null)
			return false;
		int spot = indexOf(value);
		if (spot < 0) // if the value wasn't found
			return false;
		if (spot == 0) // if the given value is the head
			return true;
		// holding 2 nodes
		Node<T> behindTemp = head;
		Node<T> temp = head.getNext();
		while (temp != null) {
			if (temp.getData().equals(value)) {
				behindTemp.setNext(temp.getNext());
				temp.setNext(head);
				head = temp;
				if (behindTemp.getNext() == null) // if the node behind is now the tail
					tail = behindTemp;
				return true;
			}
			behindTemp = temp;
			temp = temp.getNext(); // iterate through the list
		}
		return false;
	}

	/**
	 * Finds a value within the list and moves the first corresponding node
	 * forward by one node, swapping places with the node that was there previously.
	 * 
	 * @param value the value to look for
	 * @return a boolean depending on whether the list can be moved
	 */
	public boolean moveForward(T value) {// O(n)
		if (value == null)
			return false;
		int spot = indexOf(value);
		if (spot < 0) // if the value wasn't found
			return false;
		if (spot == 0) // if the given value is the head
			return true;
		// holding 4 nodes
		Node<T> moreBehind = null;
		Node<T> behindTemp = head;
		Node<T> temp = head.getNext();
		Node<T> aheadTemp = temp.getNext();
		while (temp != null) {
			if (temp.getData().equals(value)) {
				behindTemp.setNext(aheadTemp);
				temp.setNext(behindTemp);
				moreBehind.setNext(temp);
				if (behindTemp.getNext() == null) // if the node behind is now the tail
					tail = behindTemp;
				return true;
			}
			moreBehind = behindTemp;
			behindTemp = temp;
			temp = aheadTemp;
			aheadTemp = aheadTemp.getNext(); // iterate through the list
		}
		return false;
	}

	/**
	 * Finds a value within the list and moves the first corresponding node
	 * to the back of the list, shifting all values before it, forwards.
	 * 
	 * @param value the value to look for
	 * @return a boolean depending on whether the list can be moved
	 */
	public boolean moveToBack(T value) {// O(n)
		if (value == null)
			return false;
		int spot = indexOf(value);
		if (spot < 0) // if the value wasn't found
			return false;
		if (spot == 0) // if the given value is the tail
			return true;
		// holding 3 nodes
		Node<T> behindTemp = head;
		Node<T> temp = head.getNext();
		Node<T> aheadTemp = temp.getNext();
		if (behindTemp.getData().equals(value)) { // if the given value is the head
			head = temp;
			behindTemp.setNext(null);
			tail.setNext(behindTemp);
			tail = behindTemp;
			return true;
		}
		while (aheadTemp != null) {
			if (temp.getData().equals(value)) {
				behindTemp.setNext(aheadTemp);
				tail.setNext(temp);
				temp.setNext(null);
				tail = temp;
				return true;
			}
			behindTemp = temp;
			temp = temp.getNext(); // iterate through the list
		}
		return false;
	}

	/**
	 * Finds a value within the list and moves the first corresponding node
	 * backward by one node, swapping places with the node that was there
	 * previously.
	 * 
	 * @param value the value to look for
	 * @return a boolean depending on whether the list can be moved
	 */
	public boolean moveBackward(T value) {// O(n)
		if (value == null)
			return false;
		int spot = indexOf(value);
		if (spot < 0) // if the value wasn't found
			return false;
		if (spot == 0) // if the given value is the tail
			return true;
		// holding 3 nodes
		Node<T> behindTemp = head;
		Node<T> temp = head.getNext();
		Node<T> aheadTemp = temp.getNext();
		if (behindTemp.getData().equals(value)) { // if the given value is the head
			behindTemp.setNext(aheadTemp);
			temp.setNext(behindTemp);
			head = temp;
			if (behindTemp.getNext() == null) // if the moved node is now the tail
				tail = behindTemp;
			return true;
		}
		while (aheadTemp != null) {
			if (temp.getData().equals(value)) {
				behindTemp.setNext(aheadTemp);
				temp.setNext(aheadTemp.getNext());
				aheadTemp.setNext(temp);
				if (temp.getNext() == null) // if the moved node is now the tail
					tail = temp;
				return true;
			}
			behindTemp = temp;
			temp = aheadTemp;
			aheadTemp = aheadTemp.getNext(); // iterate through the list
		}
		return false;
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

		// a list of integers
		BasicList<Integer> nums = new BasicList<>();

		// basic operations
		nums.addLast(100);
		nums.addFirst(200);
		nums.addFirst(300);
		nums.addFirst(400);

		if (nums.getFirst() == 400 && nums.getLast() == 100 &&
				nums.listToString().equals("400 300 200 100")) {
			System.out.println("Yay1");
		}

		// a list of strings
		BasicList<String> names = new BasicList<>();
		names.addLast("apple");
		names.addLast("banana");
		names.addLast("blueberry");
		names.addLast("orange");
		names.addLast("blueberry");
		names.addLast("peach");

		if (names.removeFirst().equals("apple") &&
				names.remove("blueberry").equals("blueberry") && names.size() == 4 &&
				names.indexOf("blueberry") == 2 &&
				names.listToString().equals("banana orange blueberry peach")) {
			System.out.println("Yay2");
		}
		// getNode and move methods
		// -reminder: keep the original node but move it to a new location
		// - we will use getNode() to verify this as the examples below

		Node<String> node = names.getNode("orange");
		if (names.moveToFront("orange") && names.getNode("orange") == node &&
				names.listToString().equals("orange banana blueberry peach")) {
			System.out.println("Yay3");
		}

		node = names.getNode("peach");
		if (names.moveForward("peach") && names.getNode("peach") == node &&
				names.listToString().equals("orange banana peach blueberry")) {
			System.out.println("Yay41");
		}

		node = names.getNode("orange");
		if (names.moveBackward("orange") && names.getNode("orange") == node &&
				names.listToString().equals("banana orange peach blueberry")) {
			System.out.println("Yay42");
		}

		node = names.getNode("orange");
		if (names.moveToBack("orange") && names.getNode("orange") == node &&
				names.listToString().equals("banana peach blueberry orange")) {
			System.out.println("Yay43");
		}
		/**
		 * SomeType class.
		 */
		class SomeType {
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
			 * Returns whether the objects are equal.
			 * 
			 * @param o the object
			 * @return if the objects are equal
			 */
			public boolean equals(Object o) {
				if (!(o instanceof SomeType))
					return false;

				// both null
				if (((SomeType) o).value == null && this.value == null)
					return true;

				// both empty string
				if (((SomeType) o).value.length() == 0 && this.value.length() == 0)
					return true;

				// compare the leading chars
				return ((SomeType) o).value.charAt(0) == this.value.charAt(0);
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

		SomeType item1 = new SomeType("Apple");
		SomeType item2 = new SomeType("Alligator");
		SomeType item3 = new SomeType("Bee");
		SomeType item4 = new SomeType("Alder");

		BasicList<SomeType> items = new BasicList<>();
		items.addLast(item1);
		items.addLast(item2);
		items.addLast(item3);

		SomeType deleted = items.remove(item4);
		if (deleted.toString().equals("Apple")) {
			System.out.println("Yay5");
		}
	}
}