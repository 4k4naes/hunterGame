/**
 * The {@code Entity} class represents a generic game entity with attributes
 * such as position, movement, and graphical representation. This class is
 * designed to serve as a base class for different types of entities in the game.
 *
 * <p>Key features of this class include:
 * <ul>
 *   <li>Position and movement tracking in the game world</li>
 *   <li>Sprite animations for different directions</li>
 *   <li>Collision handling with a defined solid area</li>
 * </ul>
 *
 * <p>This class is intended to be extended by specific entity implementations
 * to add additional behavior and attributes.
 *
 * @author [Your Name]
 * @version 1.0
 */
package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {

    /**
     * The X-coordinate of the entity's position in the game world.
     */
    public int worldX;

    /**
     * The Y-coordinate of the entity's position in the game world.
     */
    public int worldY;

    /**
     * The movement speed of the entity.
     */
    public int speed;

    /**
     * The sprite images for the entity's upward movement.
     */
    public BufferedImage up1, up2;

    /**
     * The sprite images for the entity's downward movement.
     */
    public BufferedImage down1, down2;

    /**
     * The sprite images for the entity's leftward movement.
     */
    public BufferedImage left1, left2;

    /**
     * The sprite images for the entity's rightward movement.
     */
    public BufferedImage right1, right2;

    /**
     * The sprite image for the entity when it is standing still.
     */
    public BufferedImage standing;

    /**
     * The current direction the entity is facing (e.g., "up", "down", "left", "right").
     */
    public String direction;

    /**
     * Counter for sprite animation updates.
     */
    public int spriteCounter = 0;

    /**
     * The current sprite number for animation (e.g., 1 or 2).
     */
    public int spriteNum = 1;

    /**
     * The rectangular area representing the entity's solid collision boundary.
     */
    public Rectangle solidArea;

    /**
     * The default X-coordinate of the solid area for collision detection.
     */
    public int solidAreaDefaultX;

    /**
     * The default Y-coordinate of the solid area for collision detection.
     */
    public int solidAreaDefaultY;

    /**
     * Indicates whether a collision has been detected for the entity.
     */
    public boolean collisionOn = false;
}