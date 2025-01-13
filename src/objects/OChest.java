package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OChest extends SuperObject{

	public OChest() {
			
			name = "chest";
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
			} catch (IOException e){
				e.printStackTrace();
			}
		}
}
