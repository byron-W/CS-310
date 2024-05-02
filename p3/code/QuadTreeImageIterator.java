import java.util.Iterator;

/**
 * An BFS iterator for a QuadTree.
 * 
 * @author Byron Washington
 * @param <Pixel> the data values in the tree
 */
public class QuadTreeImageIterator<Pixel extends Number> implements Iterator<TreeNode<Pixel>> {
    /**
     * Creating a placeholder for the root.
     */
    private TreeNode<Pixel> root;

    /**
     * Creating a boolean that holds whether the queue has been created.
     */
    private boolean constructed = false;

    /**
     * Creating a placeholder for the queue.
     */
    private Queue<TreeNode<Pixel>> treeQueue;

    /**
     * The constructor.
     * 
     * @param image the quadtree
     * @throws RuntimeException in case of invalid input
     */
    public QuadTreeImageIterator(QuadTreeImage<Pixel> image) { // O(1)
        if (image == null)
            throw new RuntimeException();
        treeQueue = new Queue<TreeNode<Pixel>>();
        root = image.getRoot();
        treeQueue.enqueue(root); // adding the root
    }

    /**
     * Returns whether the iterator has another element.
     * 
     * @return a boolean
     */
    public boolean hasNext() { // O(1)
        return !treeQueue.isEmpty();
    }

    /**
     * Returns the next element in the tree.
     * 
     * @return the next tree node
     * @throws RuntimeException if no remaining elements
     */
    public TreeNode<Pixel> next() { // O(1) amortized
        if (!hasNext())
            throw new RuntimeException();
        if (!constructed)
            makeQueue(); // makes a queue if one hasn't been made yet
        return treeQueue.dequeue();
    }

    /**
     * Constructs a queue for the tree.
     */
    private void makeQueue() {
        treeQueue.dequeue();
        Queue<TreeNode<Pixel>> tempQueue = new Queue<TreeNode<Pixel>>();
        tempQueue.enqueue(root);
        while (!tempQueue.isEmpty()) {
            TreeNode<Pixel> temp = tempQueue.dequeue();
            treeQueue.enqueue(temp);
            if (temp.isLeaf()) // if the node is a leaf
                continue;
            else { // else add its children
                tempQueue.enqueue(temp.NW);
                tempQueue.enqueue(temp.NE);
                tempQueue.enqueue(temp.SE);
                tempQueue.enqueue(temp.SW);
            }
        }
        constructed = true; // to stop from recreating the queue
    }
}