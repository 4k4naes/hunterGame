package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dataBase.Counter;
import objects.OKey;

import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 40);
    Font pixelMplusSmall;
    Font pixelMplusBig;
    Font pixelMplusMini;
//    BufferedImage keyImage;
    Boolean isThereAmessage = false;
    Boolean winMessage = false;
    String message = "Go find a chest!";
    Boolean isThereAFPS = false;
    int FPS;

    public void triggerWinScreen() {
        winMessage = true;
    }

    public void displayWinScreen(Graphics2D g2, JPanel parentPanel) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.tileSize * gp.maxScreenCol, gp.tileSize * gp.maxScreenRow);

        Font fontToUseBig = (pixelMplusBig != null) ? pixelMplusBig : normalFont;
        g2.setFont(fontToUseBig);
        g2.setColor(Color.WHITE);

        Counter counter = new Counter();
        String winText = "You won! Your time: " + counter.elapsedTime;
        int textWidth = g2.getFontMetrics().stringWidth(winText);
        int textHeight = g2.getFontMetrics().getHeight();

        int x = (gp.tileSize * gp.maxScreenCol - textWidth) / 2;
        int y = (gp.tileSize * gp.maxScreenRow) / 2 - textHeight;

        g2.drawString(winText, x, y);

        JTextField textField = new JTextField(20);
        JButton submitButton = new JButton("Submit");

        textField.setBounds((gp.tileSize * gp.maxScreenCol) / 2 - 100, y + textHeight + 20, 200, 30);
        submitButton.setBounds((gp.tileSize * gp.maxScreenCol) / 2 - 50, y + textHeight + 60, 100, 30);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = textField.getText();
                System.out.println("Player Name: " + playerName);
            }
        });

        parentPanel.setLayout(null);
        parentPanel.add(textField);
        parentPanel.add(submitButton);

        parentPanel.revalidate();
        parentPanel.repaint();
    }

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            pixelMplusMini = Font.createFont(Font.TRUETYPE_FONT, new File("resources/font/PixelMplus12-Bold.ttf")).deriveFont(15F);
            pixelMplusSmall = Font.createFont(Font.TRUETYPE_FONT, new File("resources/font/PixelMplus12-Bold.ttf")).deriveFont(30F);
            pixelMplusBig = Font.createFont(Font.TRUETYPE_FONT, new File("resources/font/PixelMplus12-Bold.ttf")).deriveFont(50F);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("/font//PixelMplus12-Bold.ttf")));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

//        OKey key = new OKey();
//        keyImage = key.image;
    }

    Font fontToUseSmall = (pixelMplusSmall != null) ? pixelMplusSmall : normalFont;
    Font fontToUseBig = (pixelMplusBig != null) ? pixelMplusBig : normalFont;
    Font fontToUseMini = (pixelMplusMini != null) ? pixelMplusMini : normalFont;
    
    public void draw(Graphics2D g2) {
    	this.g2 = g2;
      
    	 g2.setFont(fontToUseBig);
         g2.setColor(Color.white);
    	
    	if(gp.gameState == gp.playState) {
    		
    	} else if (gp.gameState == gp.pauseState) {
    		drawPauseScreen();
    	}
    }

    public void drawPauseScreen() {
    	String text = "PAUSE";
    	
    	g2.setColor(new Color(0,0,0,150));
    	g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
    	
    	 g2.setFont(fontToUseBig);
    	
    	 int textW = g2.getFontMetrics().stringWidth(text);
    	 int textH = g2.getFontMetrics().getHeight();
         
    	 int x = (gp.screenWidth - textW) / 2;
    	 int y = (gp.screenHeight - textH) / 2;
    	 
    	 g2.setColor(Color.WHITE);
    	 g2.drawString(text, x, y);
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
