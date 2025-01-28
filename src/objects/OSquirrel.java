package objects;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OSquirrel extends SuperObject{
	
	public OSquirrel() {
		
		name = "squirrel";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/wiewiur.png"));
		} catch (IOException e){
			e.printStackTrace();
		}
		collision = true;
	}
}
