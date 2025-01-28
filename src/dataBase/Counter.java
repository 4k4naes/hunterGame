/**
 * The {@code Counter} class provides a simple timer utility with the ability to record elapsed time,
 * stop the timer, and save the result to a MySQL database. The timer supports storing player names
 * and the elapsed time in a database table, including a timestamp of when the time was recorded.
 *
 * <p>The class initializes the database and table structure if they do not exist and allows storing
 * the elapsed time with a player name when the timer is stopped. This implementation connects to a
 * local MySQL database and handles database operations such as table creation and data insertion.
 *
 * <p>Database connection details:
 * <ul>
 *   <li>Database URL: {@code jdbc:mysql://localhost:3306/game_results}</li>
 *   <li>Username: {@code root}</li>
 *   <li>Password: {@code ""} (empty string)</li>
 * </ul>
 *
 * @author [Your Name]
 * @version 1.0
 */
package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Scanner;

public class Counter {

    /**
     * Stores the start time of the timer in milliseconds.
     */
    private static long startTime;

    /**
     * Stores the elapsed time in milliseconds.
     */
    public static long elapsedTime;

    /**
     * Indicates whether the timer is currently running.
     */
    private static boolean isRunning;

    // Database connection constants

    /**
     * Base URL of the MySQL server.
     */
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";

    /**
     * Name of the database where results are stored.
     */
    private static final String DB_NAME = "game_results";

    /**
     * Full URL to connect to the database.
     */
    private static final String FULL_DB_URL = DB_URL + DB_NAME;

    /**
     * Username for the MySQL database connection.
     */
    private static final String DB_USER = "root";

    /**
     * Password for the MySQL database connection (empty by default).
     */
    private static final String DB_PASSWORD = "";

    /**
     * Constructor initializes the database and resets the timer state.
     */
    public Counter() {
        this.elapsedTime = 0;
        this.isRunning = false;
        initializeDatabase();
    }

    /**
     * Starts the timer. If the timer is already running, a message is displayed, and the method exits.
     */
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

    /**
     * Stops the timer, calculates the elapsed time, and displays the result.
     * The elapsed time is also saved to the database with a hardcoded player name.
     */
    public static void stop() {
        if (!isRunning) {
            System.out.println("Counter wasn't on!");
            return;
        }
        isRunning = false;
        elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Timer stopped. Time: " + elapsedTime + " ms.");

        // Example player name for saving to the database
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Please enter your nickname");
//        String playerName = sc.nextLine();
        String playerName = "xxx";
        saveElapsedTimeToDatabase(playerName, elapsedTime);
    }

    /**
     * Saves the elapsed time to the database with the provided player name.
     *
     * @param playerName  The name of the player.
     * @param elapsedTime The elapsed time in milliseconds.
     */
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

    /**
     * Initializes the database by creating the database and table structure if they do not exist.
     */
    private static void initializeDatabase() {
        // Create the database if it does not exist
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            try (Statement statement = connection.createStatement()) {
                String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
                statement.executeUpdate(createDatabaseQuery);
            }
        } catch (SQLException e) {
            System.err.println("Błąd podczas tworzenia bazy danych: " + e.getMessage());
            return;
        }

        // Create the table if it does not exist
        try (Connection connection = DriverManager.getConnection(FULL_DB_URL, DB_USER, DB_PASSWORD)) {
            try (Statement statement = connection.createStatement()) {
                String createTableQuery = "CREATE TABLE IF NOT EXISTS counter_results (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "player_name VARCHAR(50) NOT NULL, " +
                        "elapsed_time BIGINT NOT NULL, " +
                        "date_time DATETIME NOT NULL)";
                statement.executeUpdate(createTableQuery);
            }
        } catch (SQLException e) {
            System.err.println("Błąd podczas tworzenia tabeli: " + e.getMessage());
        }
    }
}
