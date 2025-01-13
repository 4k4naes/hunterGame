package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

    GamePanel gp;
    public BufferedImage mapImage; // Obraz całej mapy
    public boolean[][] collisionMap; // Tablica kolizyjna
    private final int scale = 3; // Współczynnik skalowania

    public TileManager(GamePanel gp) {
        this.gp = gp;

        collisionMap = new boolean[gp.maxWorldCol][gp.maxWorldRow];

        loadMapImage("/maps/world_map.png"); // Wczytaj obraz mapy
        loadCollisionMap("/maps/map.txt"); // Wczytaj dane kolizyjne
    }

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

    private void loadCollisionMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int i = 0; i < gp.maxWorldRow; i++) {
                String line = br.readLine();
                String[] numbers = line.split(" ");

                for (int j = 0; j < gp.maxWorldCol; j++) {
                    int num = Integer.parseInt(numbers[j]);
                    collisionMap[j][i] = (num == 1); // Jeśli 1, to pole kolizyjne
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        // Pozycja świata
        int worldX = 0;
        int worldY = 0;

        // Obliczenie pozycji obrazu na ekranie
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Skalowanie obrazu
        int scaledWidth = mapImage.getWidth() * scale;
        int scaledHeight = mapImage.getHeight() * scale;

        // Rysowanie mapy tylko jeśli jest widoczna
        if (worldX + scaledWidth > gp.player.worldX - gp.player.screenX &&
            worldX - scaledWidth < gp.player.worldX + gp.player.screenX &&
            worldY + scaledHeight > gp.player.worldY - gp.player.screenY &&
            worldY - scaledHeight < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(mapImage, screenX, screenY, scaledWidth, scaledHeight, null);
        }
    }
}