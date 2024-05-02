import java.util.Random;

/**
 * The controllable block within the board.
 * 
 * @author Byron Washington
 */
public class Block {
    /**
     * Creating a placeholder for the block data.
     */
    private DynamicArray<DynamicArray<Tile>> block; // the internal storage of the block data

    /**
     * Creating a placeholder for the Y data.
     */
    private int curY; // the Y location of the block on the board

    /**
     * Creating a placeholder for the X data.
     */
    private int curX; // the X location of the block on the board

    /**
     * Creates a Block instance.
     * 
     * @param y    the vertical location of the block on the board
     * @param x    the horizontal location of the block on the board
     * @param size the size of the square block
     * @throws RuntimeException if the block isn't at least a 2x2
     */
    public Block(int y, int x, int size) { // O(block_size)
        if (size <= 2)
            throw new RuntimeException();
        block = new DynamicArray<DynamicArray<Tile>>(size);
        for (int i = 0; i < size; i++) {
            block.set(i, new DynamicArray<Tile>(size));
        }
        curY = y;
        curX = x;
    }

    /**
     * Creating an overloaded Block instance.
     * 
     * @param y     the vertical location of the block on the board
     * @param x     the horizontal location of the block on the board
     * @param size  the size of the square block
     * @param color the color of the block
     * @throws RuntimeException if the block isn't at least a 2x2
     */
    public Block(int y, int x, int size, byte color) { // O(block_size)
        if (size <= 2)
            throw new RuntimeException();
        block = new DynamicArray<DynamicArray<Tile>>(size);
        curY = y;
        curX = x;
        Random rand = new Random(); // Generates a random number
        for (int i = 0; i < size; i++) {
            block.set(i, new DynamicArray<Tile>(size));
            for (int j = 0; j < size; j++) {
                if (rand.nextInt(size) % 2 == 0) // If the random number is even, fill in the tile
                    block.get(i).set(j, new Tile(color));
            }
        }
    }

    /**
     * Gets the size of the block.
     * 
     * @return the size of the block
     */
    public int getSize() {// returns the length of the side of block -- O(1)
        if (block.size() > 0)
            return block.size();
        else
            return 0;
    }

    /**
     * Gets the top-left Y-coordinate of the block.
     * 
     * @return the size of the block
     */
    public int getY() { // O(1)
        return curY;
    }

    /**
     * Gets the top-left X-coordinate of the block.
     * 
     * @return the size of the block
     */
    public int getX() { // O(1)
        return curX;
    }

    /**
     * Sets a tile at location (y, x) of the block.
     * 
     * @param y the y coordinate of the block
     * @param x the x coordinate of the block
     * @param t the tile
     */
    public void setTile(int y, int x, Tile t) { // O(1)
        block.get(y).set(x, t);
    }

    /**
     * Gets the tile from location (y, x) of the block.
     * 
     * @param y the y coordinate of the block
     * @param x the x coordinate of the block
     * @return the tile at y,x
     */
    public Tile getTile(int y, int x) { // O(1)
        return block.get(y).get(x);
    }

    /**
     * Drops the block by one row.
     */
    public void drop() { // O(block_size)
        curY++;
    }

    /**
     * Moves the block one spot to the left.
     */
    public void moveLeft() { // O(block_size)
        curX--;
    }

    /**
     * Moves the block one spot to the right.
     */
    public void moveRight() { // O(block_size)
        curX++;
    }

    /**
     * Rotates the block 90 degrees clockwise.
     */
    public void rotate() {// O(block_size)
        int size = block.size();
        Block oldBlock = copyBlock(size);// Creates a new block that copies the values from the current block
        DynamicArray<DynamicArray<Tile>> oldArr = oldBlock.block;
        int a = size - 1;
        for (int i = 0, y = 0; i <= a; i++) { // algorithm to find the new y,x values
            for (int j = 0, x = 0; j <= a; j++) {
                x = j;
                y = i;
                int b = a - x;
                x = a - y;
                y = a - b;
                block.get(y).set(x, oldArr.get(i).get(j)); // setting the current block new spots to the old values
            }
        }
    }

    /**
     * Flips the block vertically.
     */
    public void flipVertical() { // O(block_size)
        int size = block.size();
        Block oldBlock = copyBlock(size);
        DynamicArray<DynamicArray<Tile>> oldArr = oldBlock.block;
        int a = size - 1;
        for (int i = 0, y = 0; i <= a; i++) { // algorithm to find the new y,x values
            for (int j = 0; j <= a; j++) {
                y = i;
                int b = a - y;
                y = b;
                block.get(y).set(j, oldArr.get(i).get(j)); // setting the current block new spots to the old values
            }
        }
    }

    /**
     * Flips the block horizontally.
     */
    public void flipHorizontal() { // O(block_size)
        int size = block.size();
        Block oldBlock = copyBlock(size);
        DynamicArray<DynamicArray<Tile>> oldArr = oldBlock.block;
        int a = size - 1;
        for (int i = 0; i <= a; i++) { // algorithm to find the new y,x values
            for (int j = 0, x = 0; j <= a; j++) {
                x = j;
                int b = a - x;
                x = b;
                block.get(i).set(x, oldArr.get(i).get(j)); // setting the current block new spots to the old values
            }
        }
    }

    /**
     * Scales up the block by double its original size.
     * 
     * @return the scaled up block
     */
    public Block scaleUp() {// O(block_size)
        int size = block.size();
        int newSize = size * 2;
        Block newBlock = copyBlock(newSize);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (block.get(i).get(j) != null) { // if the tile is actually filled
                    Tile currTile = block.get(i).get(j);
                    int scaledI = i * 2;
                    int scaledJ = j * 2;
                    newBlock.block.get(scaledI).set(scaledJ, currTile); // fills the tiles around the new spot
                    newBlock.block.get(scaledI + 1).set(scaledJ, currTile);
                    newBlock.block.get(scaledI).set(scaledJ + 1, currTile);
                    newBlock.block.get(scaledI + 1).set(scaledJ + 1, currTile);
                }
            }
        }
        return newBlock;
    }

    /**
     * Scales down the block by half its original size.
     * 
     * @return the scaled down block
     */
    public Block scaleDown() {// O(block_size)
        int size = block.size();
        if (size == 2) // if the block is already at the minimum size
            return this;
        int newSize = size / 2;
        if (newSize < 2) // if the new size somehow ends up less than 2, set to 2
            newSize = 2;
        Block newBlock = copyBlock(newSize);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (block.get(i).get(j) != null) { // if the tile is actually filled
                    int scaledI = (int) i / 2;
                    int scaledJ = (int) j / 2;
                    newBlock.block.get(scaledI).set(scaledJ, block.get(i).get(j)); // fills the new spot
                }
            }
        }
        return newBlock;
    }

    /**
     * A private method that creates a copy of a block.
     * 
     * @param newSize the size of the copied block
     * @return the new block
     */
    private Block copyBlock(int newSize) {
        Block newBlock = new Block(curY, curX, newSize);
        for (int i = 0; i < newSize; i++) {
            newBlock.block.set(i, new DynamicArray<Tile>(newSize));
            if (newSize != block.size()) {
                for (int j = 0; j < newSize; j++) {
                    newBlock.block.get(i).set(j, block.get(i).get(j));
                }
            }
        }
        return newBlock;
    }
}
