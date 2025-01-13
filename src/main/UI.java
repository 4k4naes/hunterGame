package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import objects.OKey;

import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 40);
    Font pixelMplusSmall;
    Font pixelMplusBig;
    Font pixelMplusMini;
    BufferedImage keyImage;
    Boolean isThereAmessage = false;
    Boolean winMessage = false;
    String message = "Go find a chest!";
    Boolean isThereAFPS= false;
    int FPS;
    
    
    public UI(GamePanel gp) {
        this.gp = gp;
        
        try {
            // Load the custom font
        	pixelMplusMini = Font.createFont(Font.TRUETYPE_FONT, new File("resources/font/PixelMplus12-Bold.ttf")).deriveFont(15f);
        	pixelMplusSmall = Font.createFont(Font.TRUETYPE_FONT, new File("resources/font/PixelMplus12-Bold.ttf")).deriveFont(30f);
        	pixelMplusBig = Font.createFont(Font.TRUETYPE_FONT, new File("resources/font/PixelMplus12-Bold.ttf")).deriveFont(50f);
        	
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("/font//PixelMplus12-Bold.ttf")));
        } catch (IOException | FontFormatException e) {
            // Handle exception if font loading fails
            e.printStackTrace();
        }
        
        OKey key = new OKey();
        keyImage = key.image;
    }

    public void draw(Graphics2D g2) {
        Font fontToUseSmall = (pixelMplusSmall != null) ? pixelMplusSmall : normalFont;
        Font fontToUseBig = (pixelMplusBig != null) ? pixelMplusBig : normalFont;
        Font fontToUseMini = (pixelMplusMini != null) ? pixelMplusMini : normalFont;
        
        g2.setFont(fontToUseSmall);
        g2.setColor(Color.BLACK);
        g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
        g2.drawString("x " + gp.player.hasKey, 80, 55);
        
        g2.setFont(fontToUseMini);
        g2.setColor(Color.BLACK);
        g2.drawString("FPS: " + FPS, 20, 550);
        
        if (isThereAmessage) {
            long currentTime = System.currentTimeMillis();
            
            if (currentTime - messageStartTime >= 2000) {
                isThereAmessage = false; 
            } else {
                g2.setFont(fontToUseSmall);
                g2.setColor(Color.BLACK);
                g2.drawString(message, (gp.tileSize * gp.maxScreenCol - g2.getFontMetrics().stringWidth(message)) / 2, gp.tileSize * (gp.maxScreenRow - 1));
            }
        } else if(winMessage) {
            g2.setFont(fontToUseBig);
            g2.setColor(Color.BLACK);
            g2.drawString(message, (gp.tileSize * gp.maxScreenCol - g2.getFontMetrics().stringWidth(message)) / 2, gp.tileSize * (gp.maxScreenRow - 1)/2);
            
        }
        
    }
    
    private long messageStartTime = -1;
    
    public void showMessage(String newMessage) {
        message = newMessage;
        isThereAmessage = true;
        messageStartTime = System.currentTimeMillis();
    }
    
    public void showFPS(int drawCount) {
    	this.FPS = drawCount;
    	isThereAFPS = true;
    }
    
    public void win(String newMessage) {
    	 message = newMessage;
         winMessage = true;
    }
}
