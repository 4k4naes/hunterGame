package main;

import javax.swing.JFrame;

import dataBase.Counter;

/**
 * The Main class is the entry point of the application. It initializes the game window, 
 * sets up the game panel, and starts the game thread.
 */
public class Main {

    /**
     * The main method is the entry point of the game.
     * It creates the game window, sets up the game panel, and starts the game thread.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        
        // Create a new JFrame window
        JFrame window = new JFrame();
        
        // Set the default close operation for the window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Disable window resizing
        window.setResizable(false);
        
        // Set the window title
        window.setTitle("Hunter");
        
        // Create an instance of GamePanel to handle the game logic and rendering
        GamePanel gamePanel = new GamePanel();
        
        // Add the game panel to the window
        window.add(gamePanel);
        
        // Pack the window to fit the preferred size of the components
        window.pack();
        
        // Center the window on the screen
        window.setLocationRelativeTo(null);
        
        // Make the window visible
        window.setVisible(true);
        
        // Set up the game (initialize game state, assets, etc.)
        gamePanel.setupGame();
        
        // Start the game thread to handle updates and rendering
        gamePanel.startGameThread();
    }
}
