/**
 * A single node in the QuadTree.
 * 
 * @author Byron Washington
 * @param <T> accepts generic types
 */
public class TreeNode<T extends Number> {
    /**
     * Creating a placeholder for the northwest child.
     */
    public TreeNode<T> NW;

    /**
     * Creating a placeholder for the northeast child.
     */
    public TreeNode<T> NE;

    /**
     * Creating a placeholder for the southeast child.
     */
    public TreeNode<T> SE;

    /**
     * Creating a placeholder for the southwest.
     */
    public TreeNode<T> SW;

    /**
     * Creating a placeholder for the node value.
     */
    public T value;

    /**
     * Returns if the node is a leaf.
     * 
     * @return a boolean
     */
    public boolean isLeaf() { // O(1)
        if (NW == null)
            return true;
        else
            return false;
    }

    /**
     * Returns the value as a string.
     * 
     * @return a string
     */
    public String toString() { // O(1)
        if (value == null)
            return "null";
        else
            return value.toString();
    }
}
