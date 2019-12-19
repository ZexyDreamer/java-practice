package DataBase;

import org.telegram.telegrambots.meta.api.objects.Location;

import java.sql.*;

public class Login {

    private String username = "root";
    private String password = "root";
    private String connectionURL = "jdbc:mysql://localhost:3306/users";

    public void create() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(connectionURL, username, password);
            Statement statement = conn.createStatement();
            String sq1 ="CREATE TABLE IF NOT EXISTS Users(id MEDIUMINT NOT NULL, name CHAR(30) NOT NULL, city CHAR(100), PRIMARY KEY (id));";
            statement.executeUpdate(sq1);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error: " + e);
        }

    }

    public void add(String id, String name, String city) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(connectionURL, username, password);
            String sq1 = "INSERT INTO mysql.users(id, name, city) VALUES(?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sq1);
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setString(3, city);
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error: " + e);
        }
    }

    public void change(Location newLocation, String id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(connectionURL, username, password);
            PreparedStatement statement = conn.prepareStatement("UPDATE mysql.users SET city = ? WHERE id = ?");
            statement.setString(1, newLocation.toString());
            statement.setString(2, id);
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public String get(String id) {
        String city = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(connectionURL, username, password);
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM mysql.users WHERE id = ?");
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                city = rs.getString("city");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
        return city;
    }
}
