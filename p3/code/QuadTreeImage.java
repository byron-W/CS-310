import java.util.Iterator;

/**
 * A QuadTree used for displaying images.
 * 
 * @author Byron Washington
 * @param <Pixel> the data values in the tree
 */
public class QuadTreeImage<Pixel extends Number>
        implements Comparable<QuadTreeImage<Pixel>>, Iterable<TreeNode<Pixel>> {
    /**
     * Creating a placeholder for the root.
     */
    private TreeNode<Pixel> root;

    /**
     * Creating a placeholder for the width.
     */
    private int imageWidth;

    /**
     * Returns the root of the QuadTree.
     * 
     * @return the root
     */
    public TreeNode<Pixel> getRoot() {
        return root;
    }

    /**
     * Returns the depth of the tree.
     * 
     * @return the depth
     */
    public int getSize() {
        return imageWidth;
    }

    /**
     * The constructor.
     * 
     * @param array the array of pixels
     * @throws RuntimeException in case of invalid input or errors
     */
    public QuadTreeImage(Pixel[][] array) { // O(n * log n) where n is the # of pixels
        if (array == null)
            throw new RuntimeException();
        root = new TreeNode<>();
        imageWidth = array.length;
        assignNodes(array, root, 0, 0, array.length); // call the recursive method
    }

    /**
     * Returns the color of a pixel from the former pixel array.
     * 
     * @param w the column in the former array
     * @param h the row in the former array
     * @return the color as a Pixel
     * @throws IndexOutOfBoundsException in case of invalid input
     */
    public Pixel getColor(int w, int h) { // O(log n) where n is the # of pixels
        if (w < 0 || h < 0 || h >= imageWidth || w >= imageWidth)
            throw new IndexOutOfBoundsException();
        int dim = imageWidth / 2;
        TreeNode<Pixel> temp = root;
        while (temp.value == null) {
            if (w < (dim) && h < (dim)) { // if in Q1
                temp = temp.NW;
                dim /= 2;
            } else if ((w >= (dim)) && (h < (dim))) { // else if in Q2
                temp = temp.NE;
                w /= 2;
                dim /= 2;
            } else if (w < (dim) && h >= (dim)) { // else if in Q4
                temp = temp.SW;
                h /= 2;
                dim /= 2;
            } else { // else in Q3
                h /= 2;
                w /= 2;
                temp = temp.SE;
            }
        }
        return temp.value;
    }

    /**
     * Changes the color of a pixel from the former array.
     * 
     * @param w the column in the former array
     * @param h the row in the former array
     * @param v the new pixel color
     * @throws IndexOutOfBoundsException incase of invalid input
     */
    public void setColor(int w, int h, Pixel v) { // O(log n) where n is the # of pixels
        if (w < 0 || h < 0 || h >= imageWidth || w >= imageWidth)
            throw new IndexOutOfBoundsException();
        int dim = imageWidth / 2;
        TreeNode<Pixel> temp = root;
        while (temp.value == null) {
            if (w < (dim) && h < (dim)) {// if in Q1
                temp = temp.NW;
                dim /= 2;
            } else if ((w >= (dim)) && (h < (dim))) { // else if in Q2
                temp = temp.NE;
                w /= 2;
                dim /= 2;
            } else if (w < (dim) && h >= (dim)) { // else if in Q4
                temp = temp.SW;
                h /= 2;
                dim /= 2;
            } else {// else in Q3
                h /= 2;
                w /= 2;
                temp = temp.SE;
            }
        }
        temp.value = v;
        restructure(this);
    }

    /**
     * Counts the nodes (inner and leaves).
     * 
     * @return the number of nodes
     */
    public int countNodes() { // O(m) where m is the number of nodes in the tree
        Iterator<TreeNode<Pixel>> nodeIterator = this.iterator();
        int sum = 0;
        while (nodeIterator.hasNext()) {
            nodeIterator.next();
            sum++;
        }
        return sum;
    }

    /**
     * Compares 2 trees by their brightness.
     * 
     * @param other the tree to be compared to
     * @return the difference between them
     * @throws RuntimeException in case of invalid input
     */
    public int compareTo(QuadTreeImage<Pixel> other) { // O(m) where m is the # of nodes in the tree
        if (other == null)
            throw new RuntimeException();
        return this.brightness() - other.brightness();
    }

    /**
     * Returns the sum of all the pixels in the array.
     * 
     * @return the sum
     */
    public int brightness() { // O(m) where m is the # of nodes in the tree
        Iterator<TreeNode<Pixel>> nodeIterator = this.iterator();
        TreeNode<Pixel> rootNode = nodeIterator.next();
        if (rootNode.value != null) // if the root is the only pixel
            return rootNode.value.intValue();
        int numNull = 1;
        int sum = 0;
        int dim = imageWidth / 2;
        while (nodeIterator.hasNext()) { // going level by level
            int innerNode = 0;
            for (int i = 0; i < numNull * 4; i++) { // quadtree must have at least 4 nodes per level
                TreeNode<Pixel> tempNode = nodeIterator.next();
                if (tempNode.value != null)
                    sum += tempNode.value.intValue() * dim * dim; // add the node times its area
                else
                    innerNode++; // counts the inner nodes on each depth
            }
            dim /= 2; // subdivide the dimension
            numNull = innerNode;
        }
        return sum;
    }

    /**
     * Returns the all nodes with their pixel height, width, area, and value.
     * 
     * @return the string
     */
    @Override
    public String toString() { // O(m) where m is the number of nodes in the tree
        StringBuilder strBuilder = new StringBuilder();
        Iterator<TreeNode<Pixel>> nodeIterator = this.iterator();
        TreeNode<Pixel> rootNode = nodeIterator.next();
        Queue<int[]> intQueue = new Queue<int[]>(); // holds the row and column for each node found
        if (rootNode.value != null) // if the root is the only pixel
            return strBuilder.append("{0 0 " + imageWidth + " " + root.toString() + " }").toString();
        int row = 0;
        int col = 0;
        int dim = imageWidth / 2;
        int numNull = 1;
        // since the root is null, we must add it's children preemptively into the queue
        intQueue.enqueue(new int[] { row, col }); // q1
        intQueue.enqueue(new int[] { row, col + dim }); // q2
        intQueue.enqueue(new int[] { row + dim, col + dim }); // q3
        intQueue.enqueue(new int[] { row + dim, col }); // q4
        while (nodeIterator.hasNext()) {
            int innerNode = 0;
            numNull *= 4;
            for (int i = 0; i < numNull; i++) { // going level by level
                int[] tempCoor = intQueue.dequeue(); // grab the row and col for the current node
                TreeNode<Pixel> tempNode = nodeIterator.next();
                row = tempCoor[0];
                col = tempCoor[1];
                if (tempNode.value != null) { // if the node is a leaf
                    strBuilder.append(
                            "{" + row + " " + col + " " + dim + " " + tempNode.toString() + "},");
                } else { // else add its children
                    int lowerDim = dim / 2;
                    innerNode++;
                    intQueue.enqueue(new int[] { row, col }); // q1
                    intQueue.enqueue(new int[] { row, col + lowerDim }); // q2
                    intQueue.enqueue(new int[] { row + lowerDim, col + lowerDim }); // q3
                    intQueue.enqueue(new int[] { row + lowerDim, col }); // q4
                }
            }
            dim /= 2; // subdivide the dimension
            numNull = innerNode;
        }
        strBuilder.deleteCharAt(strBuilder.length() - 1); // remove the trailing space
        return strBuilder.toString();
    }

    /**
     * Returns an iterator for the QuadTree.
     * 
     * @return the iterator object
     */
    public Iterator<TreeNode<Pixel>> iterator() {
        return new QuadTreeImageIterator<>(this);
    }

    /**
     * Recursively constructs a QuadTree.
     * 
     * @param array the 2d array of pixels
     * @param node  a node of the tree
     * @param col   the column of the array
     * @param row   the row of the array
     * @param dim   the current dimension
     * @throws RuntimeException in case of invalid input
     */
    private void assignNodes(Pixel[][] array, TreeNode<Pixel> node, int col, int row, int dim) {
        if (array == null)
            throw new RuntimeException();
        boolean sameColor = true;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (!array[row][col].equals(array[row + i][col + j])) {
                    sameColor = false;
                    break;
                }
            }
        }
        if (sameColor) { // base case
            node.value = array[row][col];
            return;
        }
        dim /= 2;
        node.NW = new TreeNode<>();
        node.NE = new TreeNode<>();
        node.SE = new TreeNode<>();
        node.SW = new TreeNode<>();
        // i know i swapped some stuff around so it doesn't look like it should work
        // but it does, not entirely sure how
        assignNodes(array, node.NW, col, row, dim);
        assignNodes(array, node.SW, col, row + dim, dim);
        assignNodes(array, node.NE, col + dim, row, dim);
        assignNodes(array, node.SE, col + dim, row + dim, dim);
    }

    /**
     * Reconstructs the tree after changing a value.
     * 
     * @param tree the current tree
     * @throws RuntimeException in case of invalid input
     */
    private void restructure(QuadTreeImage<Pixel> tree) {
        if (tree == null)
            throw new RuntimeException();
        Queue<TreeNode<Pixel>> tempQueue = new Queue<TreeNode<Pixel>>();
        tempQueue.enqueue(tree.getRoot());
        while (!tempQueue.isEmpty()) {
            TreeNode<Pixel> temp = tempQueue.dequeue();
            if (temp.value != null)
                continue;
            else if (temp.NW.value.equals(temp.NE.value) && temp.NE.value.equals(temp.SE.value)
                    && temp.SE.value.equals(temp.SW.value)) {
                temp.value = temp.NW.value;
                temp.NW = null;
                temp.NE = null;
                temp.SE = null;
                temp.SW = null;
                restructure(tree);
            } else {
                tempQueue.enqueue(temp.NW);
                tempQueue.enqueue(temp.NE);
                tempQueue.enqueue(temp.SE);
                tempQueue.enqueue(temp.SW);
            }
        }
    }
}