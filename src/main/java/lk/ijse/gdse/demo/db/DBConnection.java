//package lk.ijse.gdse.demo.db;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DBConnection {
//    private static final String URL = "jdbc:mysql://localhost:3306/better_life_pharmacy";
//    private static final String USER = "root";
//    private static final String PASSWORD = "Ijse@1234";
//    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
//
//    private Connection connection;
//    private static DBConnection dbConnection;
//
//    // Private constructor to enforce singleton pattern
//    public DBConnection() throws SQLException {
//        try {
//            Class.forName(DRIVER);
//            connection = DriverManager.getConnection(URL, USER, PASSWORD);
//        } catch (ClassNotFoundException e) {
//            throw new SQLException("MySQL Driver not found.", e);
//        }
//    }
//
//    // Public method to obtain the singleton instance
//    public static synchronized DBConnection getInstance() throws SQLException {
//        if (dbConnection == null || dbConnection.isConnectionClosed()) {
//            dbConnection = new DBConnection();
//        }
//        return dbConnection;
//    }
//
//    // Instance method to get the connection
//    public  Connection getConnection() {
//        return connection;
//    }
//
//    // Method to check if the connection is closed
//    public boolean isConnectionClosed() throws SQLException {
//        return connection == null || connection.isClosed();
//    }
//}

package lk.ijse.gdse.demo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/better_life_pharmacy";
    private static final String USER = "root";
    private static final String PASSWORD = "Ijse@1234";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private Connection connection;
    private static DBConnection dbConnection;

    private DBConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found. Ensure you have added the MySQL Connector JAR to your project.", e);
        }
    }


    public static synchronized DBConnection getInstance() throws SQLException {
        if (dbConnection == null || dbConnection.isConnectionClosed()) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }


    public Connection getConnection() {
        return connection;
    }


    public boolean isConnectionClosed() throws SQLException {
        return connection == null || connection.isClosed();
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Database connection closed successfully.");
        }
    }



}
