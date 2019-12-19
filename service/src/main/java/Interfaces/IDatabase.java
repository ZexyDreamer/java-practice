package Interfaces;

import org.telegram.telegrambots.meta.api.objects.Location;

public interface IDatabase {
    String username = "root";
    String password = "root";
    String connectionURL = "jdbc:mysql://localhost:3306/users";

    void create();
    void add(String id, String name, String city);
    void change(Location newLocation, String id);
    String get(String id);


}
