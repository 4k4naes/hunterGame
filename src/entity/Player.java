package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import dataBase.Counter;
import dataBase.historyOfMoves;
import objects.ODoor;
import main.GamePanel;
import main.KeyHandler;
import main.UI;
import objects.OClosedDoor;


public class Player extends Entity{
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	public int hasKey = 0;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 30;
		worldY = gp.tileSize * 35;
		speed = 6;
		direction = "down";
	}
	
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/back1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/back2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/stand1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/stand2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/left2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));
			standing = ImageIO.read(getClass().getResourceAsStream("/player/standing.png"));
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void update() {
		
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
			
			if(keyH.upPressed == true) {
				direction = "up";
			} else if(keyH.downPressed == true) {
				direction = "down";	
			} else if(keyH.leftPressed == true) {
				direction = "left";
			} else if(keyH.rightPressed == true) {
				direction = "right";
			}
			
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			if(collisionOn == false) {
				switch(direction) {
				case "up":
					worldY -= speed;
					historyOfMoves.saveMove("Up");
					break;
				case "down":
					worldY += speed;
					historyOfMoves.saveMove("Down");
					break;
				case "left":
					worldX -= speed;
					historyOfMoves.saveMove("Left");
					break;
				case "right":
					worldX += speed;
					historyOfMoves.saveMove("Right");
					break;
				}
			}
			
			
			spriteCounter++;
			if(spriteCounter > 10) {
				if(spriteNum == 1) {
					spriteNum = 2;
				} else if(spriteNum == 2) {
					spriteNum = 1;
				}
				
				spriteCounter = 0;
			}
		}
		
	}
	
	UI ui = new UI(gp);
	
	// Wyeksmituje to potem do jakiejś odzielnej klasy 
	public void pickUpObject(int i) {
		
		if(i != 999) {
			
			String objectName = gp.obj[i].name;
//			System.out.println(objectName);
			
			switch(objectName) {
			case "key":
				hasKey++;
				gp.obj[i] = null;
				gp.ui.showMessage("You picked up a key");
				System.out.println("Keys: " + hasKey);
				break;
			case "door":
				if(hasKey > 0) {
					gp.ui.showMessage("you oppened a doory");
					int x = gp.obj[i].worldX;
					int y = gp.obj[i].worldY;
					gp.obj[i] = null;
					gp.obj[i] = new OClosedDoor();
					gp.obj[i].worldX = x;
					gp.obj[i].worldY = y;
					hasKey--;
				} else {
					gp.ui.showMessage("You need a key!");
				}
				
				System.out.println("Keys: " + hasKey);
				break;
			case "boots":
				gp.ui.showMessage("u now fast");
				speed += 3;
				gp.obj[i] = null;
				break;
			case "chest":
				gp.ui.win("You found the treasure!");
				new Thread(() -> {
				    try {
				        Thread.sleep(3000);
				        Counter.stop();
				    } catch (InterruptedException e) {
				        e.printStackTrace();
				    }
				}).start();

				break;
			case "squirrel":
				gp.ui.showMessage("A squirrel!");
				break;
			case "gate":
				if(hasKey > 0) {
					gp.ui.showMessage("you oppened a gate! You won!");
					hasKey--;
					Counter.stop();
					gp.ui.triggerWinScreen(); // Wyświetl ekran zwycięstwa
				} else {
					gp.ui.showMessage("You need a key!");
				}
				break;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
//		g2.setColor(Color.white);
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
				image = up1;
			}
			if(spriteNum == 2) {
				image = up2;				
			}
			break;
		case "down":
			if(keyH.downPressed == true) {
				if(spriteNum == 1) {
					image = down1;
				}
				if(spriteNum == 2) {
					image = down2;				
				}
			} else {
				image = standing;
			}
			break;
		case "right":
			if(spriteNum == 1) {
				image = right1;
			}
			if(spriteNum == 2) {
				image = right2;				
			}
			break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}
			if(spriteNum == 2) {
				image = left2;				
			}
			break;
		}
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
}
