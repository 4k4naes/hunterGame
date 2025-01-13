package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OKey extends SuperObject{
	
	public OKey() {
		
		name = "key";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
		} catch (IOException e){
			e.printStackTrace();
		}
		collision = true;
	}
}
