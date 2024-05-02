import java.io.FileNotFoundException;

public class Project3 {
    public static void main(String[] args) throws FileNotFoundException {
        // load an image from a file
        Short[][] array = Utilities.loadData("image1.pgm");

        // construct the quadtree
        QuadTreeImage<Short> img1 = new QuadTreeImage<>(array);

        // invoke toString()
        System.out.println("Image representation\nExpected is\n" +
                "{0 0 4 255},{4 0 4 0},{0 4 2 255},{0 6 2 255},{2 4 2 0},{4 4 2 0},{4 6 2 255},{6 4 2 255},{2 6 1 0},{2 7 1 255},{3 7 1 255},{3 6 1 255},{6 6 1 255},{6 7 1 255},{7 7 1 0},{7 6 1 255}"
                + "\nActual is\n" + img1);

        // invoke the iterator
        System.out.println("-----\nImage iterator\nExpected is\n"
                + "null 255 null null 0 255 255 null 0 0 255 null 255 0 255 255 255 255 255 0 255" +
                "\nActual is");
        for (TreeNode<Short> iter : img1)
            System.out.print(iter + " ");

        // count the number of quadtree nodes; expected: 21
        System.out.println("\n-----\nNumber of nodes (expected is 21): " +
                img1.countNodes());

        // calculate brightness; expected: 9690
        System.out.println("-----\nBrightness (expected is 9690): " +
                img1.brightness());
        // load a second image from another file
        array = Utilities.loadData("image2.pgm");

        // construct a second quadtree
        QuadTreeImage<Short> img2 = new QuadTreeImage<>(array);

        // compare the two images, i.e. which one is brighter; expected: -111
        System.out.println("\n-----\nBrightness comparison (expected is -111): " +
                img1.compareTo(img2));
        // change the color of a single pixel in image1
        img1.setColor(6, 2, Short.valueOf("255"));

        // save the modified image in a file
        Utilities.exportImage(img1, "image1_after_setColor.pgm");
        // you must open the image to visually inspect the result

        // invoke the toString to see how the tree looks like after the modification
        System.out.println("-----\nImage representation after setColor\nExpected is\n" +
                "{0 0 4 255},{4 0 4 0},{0 4 2 255},{0 6 2 255},{2 6 2 255},{2 4 2 0},{4 4 2 0},{4 6 2 255},{6 4 2 255},{6 6 1 255},{6 7 1 255},{7 7 1 0},{7 6 1 255}"
                + "\nActual is\n" + img1);

        // inspect the values of all the nodes after the modification
        System.out.println("-----\nImage iterator after setColor\nExpected is\n" +
                "null 255 null null 0 255 255 255 0 0 255 null 255 255 255 0 255" +
                "\nActual is");
        for (TreeNode<Short> iter : img1)
            System.out.print(iter + " ");

        // construct a FIFO queue
        Queue<Integer> queue = new Queue<>();
        try {
            // it should throw an exception because queue is empty
            queue.dequeue();
            System.out.println("\n-----\nError: dequeue on an empty queue should throw an exception");
        } catch (Exception e) {
            System.out.println("\n-----\nThat's correct, the queue should throw an exception");
        }

        Integer r = null;

        for (int i = 0; i < 1000000; i++)
            queue.enqueue(i);
        for (int i = 0; i < 1000000; i++)
            r = queue.dequeue();

        if (queue.isEmpty())
            System.out.println("-----\nThat's correct, the queue is empty now");
        else
            System.out.println("-----\nError: the queue should be empty now");

        if (r == 999999)
            System.out.println("-----\nLast dequeue is correct!");
        else
            System.out.println("-----\nError: last dequeue is incorrect!");

        System.out.println(
                "\n\n******************** WARNING *******************\n* *\n* THIS IS NOT A TESTER *\n* THOROUGH TESTING IS NEEDED BEFORE SUBMISSION *\n**\n************************************************");
    }

}
