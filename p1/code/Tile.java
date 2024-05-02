/**
 * The individual tiles witin the board.
 * 
 * @author Byron Washington
 */
public class Tile {
    /**
     * Creating a placeholder for the color data.
     */
    private byte color; // The internal storage of the tile data

    /**
     * Creating a Tile instance.
     * 
     * @param color the color of the tile
     */
    public Tile(byte color) {
        this.color = color;
    }

    /**
     * Gets the color of the tile.
     * 
     * @return the color of the tile
     */
    public byte getColor() {
        return color;
    }
}
