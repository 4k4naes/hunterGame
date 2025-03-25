package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Scanner;

public class historyOfMoves {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "game_results";
    private static final String FULL_DB_URL = DB_URL + DB_NAME;
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";


    public static void saveMove(String key) {
        try (Connection connection = DriverManager.getConnection(FULL_DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO moveHistory (time, move) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                statement.setString(2, key);

                statement.executeUpdate();
                System.out.println("dodanorekord:");
            }
        } catch (SQLException e) {
            System.err.println("Błąd podczas zapisywania do bazy danych: " + e.getMessage());
            initializeDatabase();
        }
    }


    public static void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            try (Statement statement = connection.createStatement()) {
                String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
                statement.executeUpdate(createDatabaseQuery);
            }
        } catch (SQLException e) {
            System.err.println("Błąd podczas tworzenia bazy danych: " + e.getMessage());
            return;
        }

        try (Connection connection = DriverManager.getConnection(FULL_DB_URL, DB_USER, DB_PASSWORD)) {
            try (Statement statement = connection.createStatement()) {
                String createTableQuery = "CREATE TABLE IF NOT EXISTS moveHistory (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "time DATETIME NOT NULL, " + 
                        "move VARCHAR(50) NOT NULL "+
                        ")";
                statement.executeUpdate(createTableQuery);
            }
        } catch (SQLException e) {
            System.err.println("Błąd podczas tworzenia tabeli: " + e.getMessage());
        }
    }
    
    public static void seeTheHistory() {
    	try(Connection connection = DriverManager.getConnection(FULL_DB_URL, DB_USER, DB_PASSWORD)){
    		try(Statement statement = connection.createStatement()){
    			ResultSet resultSet = statement.executeQuery("SELECT * FROM moveHistory");
    			System.out.println("Move History:");
    			System.out.println("+------+----------------------------+----------+");
    	        System.out.println("| ID   | Time                       | Move     |");
    	        System.out.println("+------+----------------------------+----------+");
    	        
    	        while (resultSet.next()) {
    	            int id = resultSet.getInt("id");
    	            Timestamp time = resultSet.getTimestamp("time");
    	            String move = resultSet.getString("move");

    	            System.out.printf("| %-2d | %-26s | %-8s |\n", id, time, move);
    	        }

    	        System.out.println("+------+----------------------------+----------+");
    		}
    	} catch(SQLException e){
    		
    	}
    }
}
