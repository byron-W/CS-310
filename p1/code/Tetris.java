/**
 * The rule implementer for Tetris.
 * 
 * @author Byron Washington
 */
public class Tetris {
    /**
     * Checks if the block can move to the left.
     * 
     * @param board the tetris board
     * @param block the block currently in use
     * @return whether the block can be moved or not
     */
    public static boolean canMoveLeft(Board board, Block block) {// O(board_size)
        int boardSize = block.getSize();
        int blockY = block.getY();
        int blockX = block.getX();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (block.getTile(i, j) != null) { // if the tile is actually filled
                    if (blockX + j - 1 >= 0) { // If the tile to the left is within the board
                        if (board.getTile(blockY + i, blockX + j - 1) != null) { // If the tile to the left is filled
                            return false;
                        }
                    } else
                        return false; // If the tile to the left isnt within the board
                }
            }
        }
        return true;
    }

    /**
     * Checks if the block can move to the right.
     * 
     * @param board the tetris board
     * @param block the block currently in use
     * @return whether the block can be moved or not
     */
    public static boolean canMoveRight(Board board, Block block) {// O(board_size)
        int boardSize = block.getSize();
        int blockY = block.getY();
        int blockX = block.getX();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (block.getTile(i, j) != null) { // if the tile is actually filled
                    if (blockX + j + 1 < board.getWidth()) { // If the tile to the right is within the board
                        if (board.getTile(blockY + i, blockX + j + 1) != null) { // If the tile to the right is filled
                            return false;
                        }
                    } else
                        return false; // If the tile to the right isnt within the board
                }
            }
        }
        return true;
    }

    /**
     * Checks if the block can flip vertically.
     * 
     * @param board the tetris board
     * @param block the block currently in use
     * @return whether the block can be flipped or not
     */
    public static boolean canflipVertical(Board board, Block block) {// O(board_size)
        int boardSize = block.getSize();
        int blockY = block.getY();
        int blockX = block.getX();
        for (int i = 0, y = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (block.getTile(i, j) != null) { // if the tile is actually filled
                    y = i; // algorithm for finding new spots
                    int b = boardSize - 1 - y;
                    y = b;
                    if (blockY + y < board.getHeight()) { // if the new spot is within the board
                        if (board.getTile(blockY + y, blockX + j) != null) { // if the new spot isn't filled by board
                            return false;
                        }
                    } else
                        return false; // if the tile to the right isnt within the board
                }
            }
        }
        return true;
    }

    /**
     * Checks if the block can flip horizontally.
     * 
     * @param board the tetris board
     * @param block the block currently in use
     * @return whether the block can be flipped or not
     */
    public static boolean canflipHorizontal(Board board, Block block) {// O(board_size)
        int boardSize = block.getSize();
        int blockY = block.getY();
        int blockX = block.getX();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0, x = 0; j < boardSize; j++) {
                if (block.getTile(i, j) != null) { // if the tile is actually filled
                    x = j; // algorithm for finding new spots
                    int b = boardSize - 1 - x;
                    x = b;
                    if ((blockX + x < board.getWidth()) && (blockX + x >= 0)) { // if the new spot is within the board
                        if (board.getTile(blockY + i, blockX + x) != null) { // if the new spot isn't filled by board
                            return false;
                        }
                    } else
                        return false; // if the tile to the right isnt within the board
                }
            }
        }
        return true;
    }

    /**
     * Checks if the block can rotate.
     * 
     * @param board the tetris board
     * @param block the block currently in use
     * @return whether the block can be rotated or not
     */
    public static boolean canRotate(Board board, Block block) { // O(board_size)
        int boardSize = block.getSize();
        int blockY = block.getY();
        int blockX = block.getX();
        for (int i = 0, y = 0; i < boardSize; i++) {
            for (int j = 0, x = 0; j < boardSize; j++) {
                if (block.getTile(i, j) != null) { // if the tile is actually filled
                    x = j; // algorithm for finding new spots
                    y = i;
                    int b = boardSize - 1 - x;
                    x = boardSize - 1 - y;
                    y = boardSize - 1 - b;
                    // if the new spot is within the board
                    if ((blockX + x < board.getWidth()) && (blockY + y < board.getHeight()) && (blockX + x >= 0)) {
                        if (board.getTile(blockY + y, blockX + x) != null) { // if the new spot isn't filled by board
                            return false;
                        }
                    } else
                        return false; // if the tile to the right isnt within the board
                }
            }
        }
        return true;
    }

    /**
     * Checks if the block can scale down to half size.
     * 
     * @param board the tetris board
     * @param block the block currently in use
     * @return whether the block can be scaled down or not
     */
    public static boolean canScaleDown(Board board, Block block) {// O(board_size)
        int boardSize = block.getSize();
        if ((boardSize == 2) || ((int) (boardSize / 2) == 2)) // If the block is already at its minimum size
            return false;
        else
            return true;
    }

    /**
     * Checks if the block can scale up to double size.
     * 
     * @param board the tetris board
     * @param block the block currently in use
     * @return whether the block can be scaled up or not
     */
    public static boolean canScaleUp(Board board, Block block) {// O(board_size)
        int boardSize = block.getSize();
        int blockY = block.getY();
        int blockX = block.getX();
        if (boardSize * 2 > board.getHeight() || boardSize * 2 > board.getWidth()) // if the new size is too big to fit
            return false;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (block.getTile(i, j) != null) { // if the tile is actually filled
                    int scaledI = i * 2;
                    int scaledJ = j * 2;
                    // if the new spots are not within the board
                    if ((blockY + scaledI + 1 >= board.getHeight()) || (blockX + scaledJ + 1 >= board.getWidth())
                            || (blockY + scaledI >= board.getHeight()) || (blockX + scaledJ >= board.getWidth()))
                        return false;
                    // if the new spots are already filled
                    if ((board.getTile(scaledI + blockY, scaledJ + blockX) != null)
                            || (board.getTile(scaledI + blockY + 1, scaledJ + blockX) != null)
                            || (board.getTile(scaledI + blockY, scaledJ + blockX + 1) != null)
                            || (board.getTile(scaledI + blockY + 1, scaledJ + blockX + 1) != null))
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the block can move down.
     * 
     * @param board the tetris board
     * @param block the block currently in use
     * @return whether the block can move down or not
     */
    public static boolean canDrop(Board board, Block block) {// O(board_size)
        int boardSize = block.getSize();
        int blockY = block.getY();
        int blockX = block.getX();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (block.getTile(i, j) != null) { // if the tile is actually filled
                    if (blockY + i + 1 < board.getHeight()) { // if the block isn't at the bottom already
                        if (board.getTile(blockY + i + 1, blockX + j) != null) { // if the tile below is filled
                            return false;
                        }
                    } else
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the game is over.
     * 
     * @param board the tetris board
     * @param block the block currently in use
     * @return whether the game is over or not
     */
    public static boolean isGameOver(Board board, Block block) {// O(board_size)
        int boardSize = block.getSize();
        int blockY = block.getY();
        int blockX = block.getX();
        for (int i = boardSize - 1; i >= 0; i--) {
            for (int j = 0; j < boardSize; j++) {
                if (block.getTile(i, j) != null) { // if the tile is actually filled
                    if (board.getTile(blockY + i, blockX + j) != null) { // if the block cant move after spawning
                        System.out.println("GAME OVER");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
