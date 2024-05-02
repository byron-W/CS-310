/**
 * The dynamic array used throughout the tetris game.
 * 
 * @author Byron Washington
 * @param <T> making the class accept generic types
 */
@SuppressWarnings("unchecked")
public class DynamicArray<T> {
    /**
     * Creating a placeholder for the generic array data.
     */
    private T arr[]; // The generic type array

    /**
     * Creating an instance of Dynamic Array.
     * 
     * @param size the size of the array
     */
    public DynamicArray(int size) { // O(1)
        arr = (T[]) new Object[size];
    }

    /**
     * Sets a selected element to a generic object.
     * 
     * @param index the index of the selected element
     * @param obj   the object to be placed at the given index
     * @throws IndexOutOfBoundsException if the given index is out of bounds
     */
    public void set(int index, T obj) { // O(1)
        if ((index >= arr.length) || (index < 0))
            throw new IndexOutOfBoundsException();
        arr[index] = obj;
    }

    /**
     * Gets an element at a given index.
     * 
     * @param index the index of the selected element
     * @return the generic object at the index
     * @throws IndexOutOfBoundsException if the given index is out of bounds
     */
    public T get(int index) { // O(1)
        if ((index >= arr.length) || (index < 0))
            throw new IndexOutOfBoundsException();
        else
            return arr[index];

    }

    /**
     * Gets the size of the array.
     * 
     * @return the size of the array
     */
    public int size() { // O(1)
        if (arr.length > 0) // Returns the length if it has elements
            return arr.length;
        else
            return 0;
    }
}