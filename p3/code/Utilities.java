import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;
import java.io.File;

/**
 * A helper class for the QuadTree.
 * 
 * @author Byron Washington
 */
public final class Utilities {
    /**
     * Reads input data and stores it in an array.
     * 
     * @param filename the file to get the image data
     * @return the 2D array with data
     * @throws FileNotFoundException in case of invalid input
     */
    public static Short[][] loadData(String filename) { // O(n) where n is the # of pixels
        try {
            File imageFile = new File(filename);
            Scanner scan = new Scanner(imageFile);
            String first = scan.nextLine();
            if (!first.equals("P2")) { // checks if the 1st line is "P2"
                scan.close();
                System.out.println(first);
                throw new IllegalArgumentException("Invalid 1st line, should be `P2`");
            }

            if (!scan.hasNextInt()) { // Checks if there's an integer next
                scan.close();
                throw new IllegalArgumentException("Invalid 2nd line, should be `0-255` `0-255`");
            }
            int width = scan.nextInt(); // makes the first integer, the width

            if (!scan.hasNextInt()) { // Checks if there's an integer next
                scan.close();
                throw new IllegalArgumentException("Invalid 2nd line, should be `0-255` `0-255`");
            }
            int height = scan.nextInt(); // makes the second integer, the height

            if (!scan.hasNextInt() || scan.nextInt() != 255) { // Checks if the next int is 255
                scan.close();
                throw new IllegalArgumentException("Invalid 3rd line, should be `255`");
            }

            Short[][] data = new Short[width][height];
            for (int i = 0; i < width; i++) { // grabbing all the pixels
                for (int j = 0; j < height; j++) {
                    if (scan.hasNextShort())
                        data[i][j] = scan.nextShort();
                }
            }
            scan.close();
            return data;
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Writes a QuadTree into a PGM file.
     * 
     * @param <Pixel>  the data values of the tree
     * @param image    the quadtree
     * @param filename the name of the new file
     * @throws FileNotFoundException just in case
     */
    public static <Pixel extends Number> void exportImage(QuadTreeImage<Pixel> image, String filename) {
        try {
            File newFile = new File(filename);
            PrintWriter writer = new PrintWriter(newFile);
            writer.write("P2\n"); // greyscale image
            int depth = image.getSize();
            writer.append(depth + " " + depth + "\n"); // the image width
            writer.append("255\n"); // the maximum pixel count
            Iterator<TreeNode<Pixel>> nodeIterator = image.iterator();
            while (nodeIterator.hasNext()) { // writing each node
                String str = nodeIterator.next().toString();
                writer.append(str + " ");
            }
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }
}