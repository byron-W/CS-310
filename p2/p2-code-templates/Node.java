//TODO: None. No changes allowed.

/**
 * A generic class representing a Node in the linked list.
 * @param <T> the type of element in the linked list
 *
 * @author Y. Zhong
 */

class Node<T> {
	/**
	 * The Node's data value. 
	 */
	private T data;

	/**
	 * The Node's link to the next element in the linked list. 
	 */	
	private Node<T> next;
	
	
	/**
	 * constructor.
	 * @param data value to initialize data attribute
	 */
	public Node(T data) {
		this.data = data;
		this.next = null;
	}
	
	/**
	 * getter for the data attribute.
	 * @return data attribute of this Node
	 */
	public T getData() {
		return data;
	}
	
	/**
	 * setter for the data attribute.
	 * @param data value to set data attribute
	 */
	public void setData(T data) {
		this.data = data;
	}
		
	/**
	 * getter for the next attribute.
	 * @return next attribute of this Node
	 */
	public Node<T> getNext() {
		return this.next;
	}
	
	/**
	 * setter for the data attribute.
	 * @param next reference to set next attribute
	 */
	public void setNext(Node<T> next) {
		this.next = next;
	}
	
	
	/**
	 * returns a string description of the Node's data attribute.
	 * @return string based on data of this Node
	 */	
	@Override
	public String toString() { 
		return data.toString(); 
	}

}


