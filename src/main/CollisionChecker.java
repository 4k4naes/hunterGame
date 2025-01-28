package main;

import entity.Entity;

/**
 * The CollisionChecker class handles collision detection between entities and the game world, including tiles and objects.
 * It checks for collisions based on the entity's movement direction and updates the entity's collision state accordingly.
 */
public class CollisionChecker {

    // Instance of GamePanel, which manages the game state and objects
    GamePanel gp;

    /**
     * Constructor for CollisionChecker.
     * Initializes the CollisionChecker with a reference to the GamePanel.
     *
     * @param gp The GamePanel instance that this CollisionChecker will use for collision checks.
     */
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Checks for collisions between the entity and the tiles in the game world.
     * This method calculates the entity's boundaries and checks for tile collisions based on its direction of movement.
     * The collision is detected if the entity's path intersects with a tile that has collision set to true.
     *
     * @param entity The entity whose collision with tiles is being checked.
     */
    public void checkTile(Entity entity) {
        // Calculate the boundaries of the entity in world coordinates
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        // Calculate the columns and rows the entity is in based on tile size
        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        // Check for collisions based on the entity's movement direction
        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                if (isCollisionTile(entityLeftCol, entityTopRow) || isCollisionTile(entityRightCol, entityTopRow)) {
                    entity.collisionOn = true;
                }
                break;

            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                if (isCollisionTile(entityLeftCol, entityBottomRow) || isCollisionTile(entityRightCol, entityBottomRow)) {
                    entity.collisionOn = true;
                }
                break;

            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                if (isCollisionTile(entityLeftCol, entityTopRow) || isCollisionTile(entityLeftCol, entityBottomRow)) {
                    entity.collisionOn = true;
                }
                break;

            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                if (isCollisionTile(entityRightCol, entityTopRow) || isCollisionTile(entityRightCol, entityBottomRow)) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    /**
     * Checks if there is a collision with a tile at the specified column and row in the collision map.
     * This is done by accessing the collision map from the TileManager and checking the value at the given coordinates.
     *
     * @param col The column index of the tile to check.
     * @param row The row index of the tile to check.
     * @return true if there is a collision at the given tile, false otherwise.
     */
    private boolean isCollisionTile(int col, int row) {
        // Check if the given coordinates are within the bounds of the map
        if (col >= 0 && col < gp.maxWorldCol && row >= 0 && row < gp.maxWorldRow) {
            return gp.tileM.collisionMap[col][row]; // Use the collision map from TileManager
        }
        return false; // Default to no collision if out of bounds
    }

    /**
     * Checks for collisions between the entity and the objects in the game world.
     * This method checks if the entity's movement direction intersects with any objects that have collision enabled.
     * If the entity collides with an object, the collision state is updated, and the object index is returned if the player is involved.
     *
     * @param entity The entity whose collision with objects is being checked.
     * @param player A boolean indicating whether the collision is being checked for the player.
     * @return The index of the first object that collides with the entity, or 999 if no collision occurs.
     */
    public int checkObject(Entity entity, boolean player) {

        int index = 999; // Default index if no collision occurs

        // Iterate through the objects in the game world
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                // Set the entity and object solid areas
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                // Check for collisions based on the entity's movement direction
                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;

                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;

                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;

                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }

                // Reset the positions of solid areas to their default values
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index; // Return the index of the collided object (if any)
    }
}
