package main;

import objects.ODoor;
import objects.OBoots;
import objects.OChest;
import objects.OClosedDoor;
import objects.OKey;

public class AssetSetter {

	GamePanel gp;
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
//		gp.obj[0] = new OKey();
//		gp.obj[0].worldX = 35 * gp.tileSize;
//		gp.obj[0].worldY = 12 * gp.tileSize;
//		

		gp.obj[0] = new OKey();
		gp.obj[0].worldX = 41 * gp.tileSize;
		gp.obj[0].worldY = 34 * gp.tileSize;
		
	}
	
}
