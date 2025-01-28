package objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

/**
 * The {@code SuperObject} class represents a generic object in the game world.
 * It contains properties related to the object's image, collision detection, and its position.
 * The class also provides a method for drawing the object on the screen.
 */
public class SuperObject {
    
    /** The image representing the object. */
    public BufferedImage image;
    
    /** The name of the object. */
    public String name;
    
    /** A boolean flag indicating whether the object has a collision. */
    public boolean collision = false;
    
    /** The world X coordinate of the object. */
    public int worldX, worldY;
    
    /** The rectangular area for collision detection. */
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    
    /** Default X coordinate of the solid area. */
    public int solidAreaDefaultX = 0;
    
    /** Default Y coordinate of the solid area. */
    public int solidAreaDefaultY = 0;

    /**
     * Draws the object on the screen.
     * The object is drawn relative to the player's position, ensuring it is only drawn when it's within the screen bounds.
     * 
     * @param g2 The {@link Graphics2D} object used to draw the object.
     * @param gp The {@link GamePanel} that contains the game state, including the player and screen dimensions.
     */
    public void draw(Graphics2D g2, GamePanel gp) {
        
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        
        // Check if the object is within the screen boundaries
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            
            // Draw the image of the object on the screen
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
