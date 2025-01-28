package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

/**
 * The {@code TileManager} class is responsible for loading and managing the map and its collision data.
 * It handles the loading of map images, the collision map, and drawing the map onto the game screen.
 */
public class TileManager {

    /** A reference to the {@link GamePanel} for accessing game-related data. */
    GamePanel gp;

    /** The image representing the entire map. */
    public BufferedImage mapImage;

    /** A 2D array that holds the collision data for each tile on the map. */
    public boolean[][] collisionMap;

    /** The scaling factor for resizing the map image when drawing it. */
    private final int scale = 3;

    /**
     * Constructs a new {@code TileManager} object.
     * 
     * @param gp The {@link GamePanel} object that contains game-related data such as screen size and player position.
     */
    public TileManager(GamePanel gp) {
        this.gp = gp;
        collisionMap = new boolean[gp.maxWorldCol][gp.maxWorldRow];
        loadMapImage("/maps/world_map.png"); // Load map image from resources
        loadCollisionMap("/maps/map.txt");   // Load collision data from resources
    }

    /**
     * Loads the map image from the specified path.
     * 
     * @param path The path to the map image file in the resources.
     */
    private void loadMapImage(String path) {
        try (InputStream input = getClass().getResourceAsStream(path)) {
            if (input == null) {
                System.out.println("Plik '" + path + "' nie został znaleziony!");
            } else {
                mapImage = ImageIO.read(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the collision map from a specified file.
     * The file contains numeric values indicating whether a tile has a collision (1 for collision, 0 for no collision).
     * 
     * @param filePath The path to the collision map file.
     */
    private void loadCollisionMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int i = 0; i < gp.maxWorldRow; i++) {
                String line = br.readLine();
                String[] numbers = line.split(" ");

                for (int j = 0; j < gp.maxWorldCol; j++) {
                    int num = Integer.parseInt(numbers[j]);
                    collisionMap[j][i] = (num == 1); // If 1, mark as a collidable tile
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the map image on the game screen, scaling it according to the specified scale factor.
     * The map is only drawn if it is within the visible screen area.
     * 
     * @param g2 The {@link Graphics2D} object used for drawing on the game panel.
     */
    public void draw(Graphics2D g2) {
        // World position (adjusted based on player position)
        int worldX = 0;
        int worldY = 0;

        // Calculate screen position relative to player position
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Scale the map image
        int scaledWidth = mapImage.getWidth() * scale;
        int scaledHeight = mapImage.getHeight() * scale;

        // Only draw the map if it's visible on the screen
        if (worldX + scaledWidth > gp.player.worldX - gp.player.screenX &&
            worldX - scaledWidth < gp.player.worldX + gp.player.screenX &&
            worldY + scaledHeight > gp.player.worldY - gp.player.screenY &&
            worldY - scaledHeight < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(mapImage, screenX, screenY, scaledWidth, scaledHeight, null);
        }
    }
}
