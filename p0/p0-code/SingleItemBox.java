/**
 * A box that can only hold one item.
 * 
 * @author Byron Washington
 * @param <T> making the class accept generic types
 */
public class SingleItemBox<T> {
	/**
	 * Creating a placeholder for the item in the box.
	 */
	private T item;

	/**
	 * Constructs a SingleItemBox instance.
	 * 
	 * @param item the item to be put in the box
	 * @throws IllegalArgumentException if there is no parameter given
	 */
	public SingleItemBox(T item) {
		if (item == null)
			throw new IllegalArgumentException("Item cannot be null!");
		else
			setItem(item);
	}

	/**
	 * Sets a new item into the box.
	 * 
	 * @param item the item that is currently in the box
	 * @return the item that was there before, if there was one
	 * @throws IllegalArgumentException if there is no parameter given
	 */
	public T setItem(T item) {
		if (item == null)
			throw new IllegalArgumentException("Item cannot be null!");
		else {
			T temp = this.item;
			this.item = item;
			return temp;
		}
	}

	/**
	 * Gets the item that's in the box.
	 * 
	 * @return the current item in the box
	 * @throws IllegalArgumentException if there is no item currently in the box
	 */
	public T getItem() {
		if (item == null)
			throw new IllegalArgumentException("Item cannot be null!");
		else
			return item;
	}

}