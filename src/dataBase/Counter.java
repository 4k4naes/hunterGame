package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.Timestamp;

public class Counter {
    private static long startTime;
    private static long elapsedTime;
    private static boolean isRunning;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "game_results";
    private static final String FULL_DB_URL = DB_URL + DB_NAME;
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public Counter() {
        this.elapsedTime = 0;
        this.isRunning = false;
        initializeDatabase();
    }

    public static void start() {
        if (isRunning) {
            System.out.println("Counter is running");
            return;
        }
        isRunning = true;
        startTime = System.currentTimeMillis();
        System.out.println("Timer starts.");
        initializeDatabase();
    }

    public static void stop() {
        if (!isRunning) {
            System.out.println("Counter wasn't on!");
            return;
        }
        isRunning = false;
        elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Timer stopped. Time: " + elapsedTime + " ms.");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Wprowadź swój nick: ");
        String playerName = scanner.nextLine();

        saveElapsedTimeToDatabase(playerName, elapsedTime);
    }

    private static void saveElapsedTimeToDatabase(String playerName, long elapsedTime) {
        try (Connection connection = DriverManager.getConnection(FULL_DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO counter_results (player_name, elapsed_time, date_time) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, playerName);
                statement.setLong(2, elapsedTime);
                
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                statement.setTimestamp(3, timestamp);

                statement.executeUpdate();
                System.out.println("Czas zapisany do bazy danych dla gracza: " + playerName);
            }
        } catch (SQLException e) {
            System.err.println("Błąd podczas zapisywania do bazy danych: " + e.getMessage());
        }
    }

    private static void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            try (Statement statement = connection.createStatement()) {
                String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
                statement.executeUpdate(createDatabaseQuery);
//                System.out.println("Baza danych została utworzona (jeśli nie istniała).");
            }
        } catch (SQLException e) {
            System.err.println("Błąd podczas tworzenia bazy danych: " + e.getMessage());
            return;
        }

        try (Connection connection = DriverManager.getConnection(FULL_DB_URL, DB_USER, DB_PASSWORD)) {
            try (Statement statement = connection.createStatement()) {
                String createTableQuery = "CREATE TABLE IF NOT EXISTS counter_results (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "player_name VARCHAR(50) NOT NULL, " +
                        "elapsed_time BIGINT NOT NULL, " +
                        "date_time DATETIME NOT NULL)";
                statement.executeUpdate(createTableQuery);
//                System.out.println("Tabela została utworzona (jeśli nie istniała).");
            }
        } catch (SQLException e) {
            System.err.println("Błąd podczas tworzenia tabeli: " + e.getMessage());
        }
    }
}
