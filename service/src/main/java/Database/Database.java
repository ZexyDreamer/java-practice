package Database;

import Parser.Parser;
import org.telegram.telegrambots.meta.api.objects.Location;

import java.sql.*;
import java.util.Properties;

import Interfaces.IDatabase;

public class Database implements IDatabase {
    final private String username;
    final private String password;
    final private String connectionURL;

    public Database() {
        Properties properties = Parser.parser();
        this.username = properties.getProperty("username");
        this.password = properties.getProperty("password");
        this.connectionURL = properties.getProperty("connectionURL");
    }

    public void create() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(this.connectionURL, this.username, this.password);
            Statement statement = conn.createStatement();
            String sq1 ="CREATE TABLE IF NOT EXISTS users(id INT(30) NOT NULL, city CHAR(100), PRIMARY KEY (id));";
            statement.executeUpdate(sq1);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error: " + e);
        }
    }

    public void add(String id, String city) {
        try {
            Connection conn = DriverManager.getConnection(this.connectionURL, this.username, this.password);
            PreparedStatement statement = conn.prepareStatement("INSERT INTO bothelper.users(id, city) VALUES(?, ?)");
            statement.setString(1, id);
            statement.setString(2, city);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    public void change(Location newLocation, String id) {
        try {
            Connection conn = DriverManager.getConnection(this.connectionURL, this.username, this.password);
            PreparedStatement statement = conn.prepareStatement("UPDATE bothelper.users SET city = ? WHERE id = ?");
            statement.setString(1, newLocation.toString());
            statement.setString(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    public String get(String id) {
        String city = "";
        try {
            Connection conn = DriverManager.getConnection(this.connectionURL, this.username, this.password);
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM bothelper.users WHERE id = ?");
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                city = rs.getString("city");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return city;
    }
}
