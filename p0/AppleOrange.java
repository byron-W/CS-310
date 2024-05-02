/**
 * A script that prints based of cmd line arguments.
 * 
 * @author Byron Washington
 */
public class AppleOrange {
	/**
	 * Creates the main method.
	 * 
	 * @param args cmd line argument
	 */
	public static void main(String[] args) {
		// creating an error message if the arguments given aren't valid
		final String errString = "One positive number required as a command line argument.\nExample Usage: java AppleOrange [number]";

		// instantiating a placeholder integer that will hold the given integer
		int num = 0;

		if (args.length != 1) { // Checks if there is only one argument given
			System.err.println(errString);
			return;
		}
		try { // Checks if the argument given is an integer
			num = Integer.parseInt(args[0]);
		} catch (Exception e) {
			System.err.println(errString);
			return;
		}
		if (num <= 0) { // Checks if the integer given is positive
			System.err.println(errString);
			return;
		}
		for (int i = 1; i <= num; i++) {
			if ((i % 3 == 0) && (i % 7 != 0)) // If i is divisible by 3 and not 7
				System.out.print("apple"); // Print apple
			else if ((i % 3 != 0) && (i % 7 == 0)) // If i is divisible by 7 and not 3
				System.out.print("orange"); // Print orange
			else if ((i % 3 == 0) && (i % 7 == 0)) // If i is divisble by both 7 and 3
				System.out.print("appleorange"); // Print appleorange
			else // If i isn't divisble by 7 or 3
				System.out.print(i); // Print i
			if (i < num)
				System.out.print(" ");
		}
	}
}