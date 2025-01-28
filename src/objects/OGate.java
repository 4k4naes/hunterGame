package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OGate extends SuperObject{
	
	public OGate() {
		
		name = "gate";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/empty.png"));
		} catch (IOException e){
			e.printStackTrace();
		}
		collision = false;
	}
}
