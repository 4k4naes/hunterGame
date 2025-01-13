package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBoots extends SuperObject{

	public OBoots() {
			
			name = "boots";
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/objects/shoe.png"));
			} catch (IOException e){
				e.printStackTrace();
			}
		}
}
