package DataBase;

import org.telegram.telegrambots.meta.api.objects.Location;

import java.sql.*;

import Interfaces.IDatabase;

public class Database implements IDatabase {

    private String username = IDatabase.username;
    private String password = IDatabase.password;
    private String connectionURL = IDatabase.connectionURL;

    private Connection conn = DriverManager.getConnection(connectionURL, username, password);

    public Database() throws SQLException {
    }

    public void create() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(connectionURL, username, password);
            Statement statement = conn.createStatement();
            String sq1 ="CREATE TABLE IF NOT EXISTS users(id INT(30) NOT NULL, name CHAR(30) NOT NULL, city CHAR(100), PRIMARY KEY (id));";
            statement.executeUpdate(sq1);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error: " + e);
        }
    }

    public void add(String id, String name, String city) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO bothelper.users(id, name, city) VALUES(?, ?, ?)");
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setString(3, city);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    public void change(Location newLocation, String id) {
        try {
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
