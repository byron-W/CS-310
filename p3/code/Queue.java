/**
 * A circular FIFO queue.
 * 
 * @author Byron Washington
 * @param <T> the class accepts generic types
 */
@SuppressWarnings("unchecked")
public class Queue<T> {
	/**
	 * Creating a placeholder for the dynamic array.
	 */
	private T[] queue;

	/**
	 * Creating a constant for the initial capacity.
	 */
	private static final int INITIAL_CAPACITY = 10;

	/**
	 * Creating a placeholder for the start of the queue.
	 */
	private int frontIndex = 0;

	/**
	 * Creating a placeholder for the next free spot in the queue.
	 */
	private int backIndex = 0;

	/**
	 * Creating a placeholder for the current size.
	 */
	private int size;

	/**
	 * The constructor.
	 */
	public Queue() { // O(1)
		queue = (T[]) new Object[INITIAL_CAPACITY];
		size = 0;
	}

	/**
	 * Returns whether the queue is empty or not.
	 * 
	 * @return a boolean
	 */
	public boolean isEmpty() { // O(1)
		if (size < 1)
			return true;
		else
			return false;
	}

	/**
	 * Adds an item to the back of the queue.
	 * 
	 * @param value the item to be added
	 */
	public void enqueue(T value) { // O(1) amortized
		int capacity = queue.length;
		if (size < capacity) { // if there's enough space with the current capacity
			queue[backIndex] = value;
			if (backIndex + 1 >= capacity) // if theres no space in the back, move to the front
				backIndex = 0;
			else if (queue[backIndex + 1] == null) // else the next spot is free
				backIndex++;
			// else keep the back index the same because the queue is at max capacity
		} else { // create a new array with 2x capacity
			int newCap = capacity * 2;
			T[] newQueue = (T[]) new Object[newCap];
			int placeHolder = 0;
			for (int i = frontIndex, j = 0; i < capacity; i++, j++) {
				newQueue[j] = queue[i]; // go from the front index to the end
				placeHolder++;
			}
			for (int i = 0; i <= backIndex; i++) { // go from the start to the backindex
				newQueue[placeHolder] = queue[i];
				placeHolder++;
			}
			newQueue[capacity] = value;
			queue = newQueue; // make the old queue, the newly created one
			backIndex = capacity + 1; // reset the front and back index
			frontIndex = 0;
		}
		size++; // increment the size
	}

	/**
	 * Peeks at the front item of the queue without removing it.
	 * 
	 * @return the front item
	 * @throws RuntimeException in case the queue is empty
	 */
	public T peek() { // O(1)
		if (size < 1)
			throw new RuntimeException();
		else
			return queue[frontIndex];
	}

	/**
	 * Removes and returns the front item from the queue.
	 * 
	 * @return the previous front item
	 * @throws RuntimeException in case the queue is empty
	 */
	public T dequeue() { // O(1)
		if (size < 1)
			throw new RuntimeException();
		T value = queue[frontIndex]; // holding the removed item
		queue[frontIndex] = null; // removing the item
		// reset the front and back index
		if (frontIndex + 1 >= queue.length) {
			frontIndex = 0;
		} else if (size - 1 < 1) {
			backIndex = 0;
			frontIndex = 0;
		} else
			frontIndex++;
		size--; // decrement the size
		return value;
	}
}