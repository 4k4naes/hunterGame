package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OClosedDoor extends SuperObject{

	public OClosedDoor() {
			
			name = "closedDoor";
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/objects/closedDoor.png"));
			} catch (IOException e){
				e.printStackTrace();
			}
		}
}
