/**
 * The board on which the game is played.
 * 
 * @author Byron Washington
 */
public class Board {
    /**
     * Creating a placeholder for the board data.
     */
    private DynamicArray<DynamicArray<Tile>> board; // the internal storage of the board data
    
    /**
     * Creating a placeholder for the height data.
     */
    private int height; // the height of the board

    /**
     * Creating a placeholder for the width data.
     */
    private int width; // the width of the board

    /**
     * Creates a Board instance.
     * 
     * @param height the height of the board
     * @param width  the width of the board
     * @throws RuntimeException if the board is too small
     */
    public Board(int height, int width) {// O(height * width)
        if ((height <= 2) || (width <= 2))
            throw new RuntimeException();
        board = new DynamicArray<DynamicArray<Tile>>(height);
        for (int i = 0; i < height; i++) {
            board.set(i, new DynamicArray<Tile>(width));
        }
        this.height = height;
        this.width = width;
    }

    /**
     * Gets the width of the board.
     * 
     * @return the width of the board
     */
    public int getWidth() { // O(1)
        return width;
    }

    /**
     * Gets the height of the board.
     * 
     * @return the height of the board
     */
    public int getHeight() {// O(1)
        return height;
    }

    /**
     * Sets a tile at location (y, x) of the board.
     *
     * @param y the y coordinate of the board
     * @param x the x coordinate of the board
     * @param t the tile
     */
    public void setTile(int y, int x, Tile t) {// O(1)
        board.get(y).set(x, t);
    }

    /**
     * Gets the tile from location (y, x) of the board.
     * 
     * @param y the y coordinate of the board
     * @param x the x coordinate of the board
     * @return the tile at y,x
     */
    public Tile getTile(int y, int x) { // O(1)
        return board.get(y).get(x);
    }

    /**
     * Consolidates fallen blocks into the tetris well.
     * 
     * @param block the fallen block
     */
    public void consolidate(Block block) {// O(block_size)
        int blockSize = block.getSize();
        int blockY = block.getY();
        int blockX = block.getX();
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                if (block.getTile(i, j) != null) { // if the tile is actually filled
                    Tile currTile = block.getTile(i, j);
                    board.get(i + blockY).set(j + blockX, currTile);
                }
            }
        }
    }

    /**
     * Clears any/all rows that are complete and shifts the above tiles down by one.
     */
    public void clearRows() { // O(board_size)
        int badRow = 0;
        boolean filledRow = false;
        for (int i = 0; i < height; i++) {
            int c = 0;
            for (int j = 0; j < width; j++) {
                if (board.get(i).get(j) != null) { // if the tile is actually filled
                    c++; // counts the tiles in each row
                }
            }
            if (c == width) {
                badRow = i;
                filledRow = true;
                break; // if a complete row is found, stop iterating and shift the rows
            }
        }
        if (filledRow) { // if there is a row that's complete, shift everything down
            for (int i = badRow; i > 0; i--) {
                for (int j = 0; j < width; j++) {
                    board.get(i).set(j, board.get(i - 1).get(j));
                }
            }
            clearRows(); // recursion to get every row that is full
        }
    }

    /**
     * Rewards the player for scaling up by removing the row with the most blocks.
     */
    public void reward() { // O(board_size)
        int bigRow = 0;
        int highestAmt = 0;
        for (int i = 0; i < height; i++) {
            int c = 0;
            for (int j = 0; j < width; j++) {
                if (board.get(i).get(j) != null) { // if the tile is actually filled
                    c++; // counts the tiles in each row
                }
            }
            if (c >= highestAmt) { // if the row has a higher amount of tiles
                bigRow = i; // the biggest row is always the lower row
                highestAmt = c;
            }
        }
        if (highestAmt > 0) { // shifts all the rows down by one
            for (int i = bigRow; i > 0; i--) {
                for (int j = 0; j < width; j++) {
                    board.get(i).set(j, board.get(i - 1).get(j));
                }
            }
        }
    }

    /**
     * Penalizes the player for scaling down by adding the row with the least
     * blocks.
     */
    public void penalize() { // O(board_size)
        int smallRow = 0;
        int lowestAmt = width - 1;
        for (int i = 0; i < height; i++) {
            int c = 0;
            for (int j = 0; j < width; j++) {
                if (board.get(i).get(j) != null) { // if the tile is actually filled
                    c++; // counts the tiles in each row
                }
            }
            if ((c < lowestAmt) && (c != 0)) { // ignores rows without any blocks
                smallRow = i; // since we're going top down, we dont need an if clause for ties
                lowestAmt = c;
            }
            if ((lowestAmt == width - 1) && (i == height - 1))
                return;
        }
        if (lowestAmt > 0) { // shifts all the rows up by one
            for (int i = 0; i < smallRow; i++) {
                for (int j = 0; j < width; j++) {
                    board.get(i).set(j, board.get(i + 1).get(j));
                }
            }
        }
    }
}
