/**
 * This is a demo of using the SingleItemBox class.
 * 
 * @author K. Raven Russell
 * @author Y. Zhong
 */
public class BoxUsageDemo {
	/**
	 * This is a main method with demo code.
	 * 
	 * @param args command line args (not used)
	 */
	public static void main(String[] args) {
		// demo putting an apple in a box

		/**
		 * Class for apples.
		 */
		class Apple {
		}

		// make an apple
		Apple a1 = new Apple();

		// put the apple in a box
		SingleItemBox<Apple> appleBox = new SingleItemBox<>(a1);

		// check that the apple was put in the box
		if (appleBox.getItem().equals(a1)) {
			System.out.println("yay 1");
		}

		// demo putting a banana in a box

		/**
		 * Class for bananas.
		 */
		class Banana {
		}

		// make a banana
		Banana b1 = new Banana();

		// put the banana in a box
		SingleItemBox<Banana> bananaBox = new SingleItemBox<>(b1);

		// check that the banana was put in the box
		if (bananaBox.getItem().equals(b1)) {
			System.out.println("yay 2");
		}

		// check exception behavior if item used in constructor is null
		try {
			appleBox = new SingleItemBox<>(null);
		} catch (IllegalArgumentException ex) {
			if (ex.getMessage().equals("Item cannot be null!"))
				System.out.println("yay 3");
		}

		// demo putting a banana in a box using setItem
		Banana b2 = new Banana();
		if (bananaBox.setItem(b2).equals(b1)) {
			System.out.println("yay 4");
		}

		// check that the banana was put in the box
		if (bananaBox.getItem().equals(b2)) {
			System.out.println("yay 5");
		}

	}
}