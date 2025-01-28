package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The {@code OBoots} class represents a boots object in the game.
 * It extends the {@link SuperObject} class and is responsible for
 * loading and displaying the boots image as well as setting its name.
 */
public class OBoots extends SuperObject {

    /**
     * Constructs a new {@code OBoots} object.
     * The constructor sets the name of the object to "boots" and attempts to
     * load the image of the boots from the specified file path. 
     * If the image cannot be loaded, it prints the stack trace of the exception.
     */
    public OBoots() {
        name = "boots";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/shoe.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
