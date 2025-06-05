package databasecom.parking.db;

import java.sql.*;

public class DBHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/parking_system";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void insertVehicle(double ticket, String number, String size, int bill) {
        String query = "INSERT INTO vehicles (ticket_number, vehicle_number, size, bill) VALUES (?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setDouble(1, ticket);
            stmt.setString(2, number);
            stmt.setString(3, size);
            stmt.setInt(4, bill);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteVehicle(double ticket) {
        String query = "DELETE FROM vehicles WHERE ticket_number = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setDouble(1, ticket);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

