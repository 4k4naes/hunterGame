package main;

import objects.ODoor;
import objects.OGate;
import objects.OBoots;
import objects.OChest;
import objects.OClosedDoor;
import objects.OKey;
import objects.OSquirrel;

/**
 * The AssetSetter class is responsible for initializing and placing objects in the game world.
 * This class defines a method to set different types of objects and their world positions.
 * It is used to populate the game with objects like keys, squirrels, and gates.
 */
public class AssetSetter {

    // Instance of GamePanel, which manages the game state and objects
    GamePanel gp;

    /**
     * Constructor for AssetSetter.
     * Initializes the AssetSetter with a reference to the GamePanel.
     *
     * @param gp The GamePanel instance that this AssetSetter will modify.
     */
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Sets the game objects by assigning them to the 'obj' array in the GamePanel.
     * Each object is assigned specific world coordinates based on the tile size.
     * The objects are placed at predefined world positions.
     * 
     * The objects include:
     * - OKey (Key)
     * - OSquirrel (Squirrel)
     * - OGate (Gate)
     * 
     * The world coordinates for each object are calculated based on the tile size
     * and placed in specific positions within the game world.
     */
    public void setObject() {
        
        // Create and set the key object at specified world coordinates
        gp.obj[0] = new OKey();
        gp.obj[0].worldX = 41 * gp.tileSize;
        gp.obj[0].worldY = 34 * gp.tileSize;
        
        // Create and set the squirrel object at specified world coordinates
        gp.obj[1] = new OSquirrel();
        gp.obj[1].worldX = 48 * gp.tileSize;
        gp.obj[1].worldY = 35 * gp.tileSize;
        
        // Create and set the gate objects at specified world coordinates
        gp.obj[2] = new OGate();
        gp.obj[2].worldX = 35 * gp.tileSize;
        gp.obj[2].worldY = 22 * gp.tileSize;
        
        gp.obj[3] = new OGate();
        gp.obj[3].worldX = 36 * gp.tileSize;
        gp.obj[3].worldY = 22 * gp.tileSize;
        
        gp.obj[4] = new OGate();
        gp.obj[4].worldX = 37 * gp.tileSize;
        gp.obj[4].worldY = 22 * gp.tileSize;
        
        gp.obj[5] = new OGate();
        gp.obj[5].worldX = 38 * gp.tileSize;
        gp.obj[5].worldY = 22 * gp.tileSize;
        
        gp.obj[6] = new OGate();
        gp.obj[6].worldX = 39 * gp.tileSize;
        gp.obj[6].worldY = 22 * gp.tileSize;
        
        gp.obj[7] = new OGate();
        gp.obj[7].worldX = 40 * gp.tileSize;
        gp.obj[7].worldY = 22 * gp.tileSize;
    }
}
