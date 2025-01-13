package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

public class ODoor extends SuperObject{

	public ODoor() {
			
			name = "door";
			collision = true;
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
			} catch (IOException e){
				e.printStackTrace();
			}
		}
}
