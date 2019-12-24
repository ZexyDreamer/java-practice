package Interfaces;

import Database.Database;

import java.io.IOException;

public interface IWeatherHandler {
    String weatherHandle(String message, String app_id, Database database, String chat_id) throws IOException;
}
