package tile;

import java.awt.image.BufferedImage;

/**
 * The {@code Tile} class represents a single tile in the game world.
 * Each tile has an image and an optional collision property that can 
 * be used to detect whether the tile is walkable or not.
 */
public class Tile {

    /** The image representing the tile. */
    public BufferedImage image;
    
    /** A boolean flag indicating whether the tile has a collision (i.e., it's not walkable). */
    public boolean collision = false;
}
