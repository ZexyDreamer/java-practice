package Interfaces;

import org.telegram.telegrambots.meta.api.objects.Location;

public interface IDatabase {
    void create();
    void add(String id, String city);
    void change(Location newLocation, String id);
    String get(String id);
}
